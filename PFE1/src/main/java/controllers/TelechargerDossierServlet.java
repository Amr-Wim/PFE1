package controllers; // Ou controllers.patient

import dao.DossierSortieDAO;
import dao.HospitalisationDAO;
import model.DossierSortie;
import model.Hospitalisation;
import model.Patient; // Ou Utilisateur si l'objet en session est Utilisateur

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/patient/telechargerDossier")
public class TelechargerDossierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DossierSortieDAO dossierSortieDAO;
    private HospitalisationDAO hospitalisationDAO;

    @Override
    public void init() throws ServletException {
        dossierSortieDAO = new DossierSortieDAO();
        hospitalisationDAO = new HospitalisationDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("TelechargerDossierServlet: Requête reçue.");
        HttpSession session = request.getSession(false);

        // 1. Vérifier si le patient est connecté
        if (session == null || session.getAttribute("patient") == null) {
            System.err.println("TelechargerDossierServlet: Session invalide ou patient non connecté.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Veuillez vous connecter pour accéder à cette ressource.");
            return;
        }
        Patient patientConnecte = (Patient) session.getAttribute("patient");

        // 2. Récupérer l'ID de l'hospitalisation (ou du dossier) depuis la requête
        String hospitalisationIdStr = request.getParameter("hospitalisationId");
        if (hospitalisationIdStr == null || hospitalisationIdStr.trim().isEmpty()) {
            System.err.println("TelechargerDossierServlet: Paramètre hospitalisationId manquant.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID d'hospitalisation requis.");
            return;
        }

        try {
            int hospitalisationId = Integer.parseInt(hospitalisationIdStr);
            System.out.println("TelechargerDossierServlet: Tentative de téléchargement pour hospitalisation ID: " + hospitalisationId + " par Patient ID: " + patientConnecte.getId());

            // 3. Récupérer les métadonnées du dossier de sortie
            DossierSortie dossier = dossierSortieDAO.getByHospitalisationId(hospitalisationId);

            if (dossier == null) {
                System.err.println("TelechargerDossierServlet: Aucun dossier de sortie trouvé en BDD pour hospitalisation ID: " + hospitalisationId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Dossier de sortie non trouvé pour cette hospitalisation.");
                return;
            }

            // 4. **VÉRIFICATION DE SÉCURITÉ CRUCIALE**
            Hospitalisation hospAssociee = hospitalisationDAO.getById(dossier.getIdHospitalisation());
            if (hospAssociee == null || hospAssociee.getIdPatient() != patientConnecte.getId()) {
                System.err.println("TelechargerDossierServlet: ACCÈS NON AUTORISÉ. Patient ID " + patientConnecte.getId() +
                                   " essaie de télécharger un dossier pour hospitalisation ID " + dossier.getIdHospitalisation() +
                                   " appartenant à patient ID " + (hospAssociee != null ? hospAssociee.getIdPatient() : "INCONNU"));
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé à ce dossier.");
                return;
            }
            System.out.println("TelechargerDossierServlet: Vérification de sécurité OK.");

            // 5. Vérifier l'existence du fichier physique
            File pdfFile = new File(dossier.getCheminFichier());
            if (!pdfFile.exists() || !pdfFile.isFile()) {
                System.err.println("TelechargerDossierServlet: Fichier PDF introuvable sur le serveur: " + dossier.getCheminFichier());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Le fichier du dossier de sortie est introuvable sur le serveur. Veuillez contacter l'administration.");
                return;
            }
            System.out.println("TelechargerDossierServlet: Fichier PDF trouvé: " + dossier.getCheminFichier());

            // 6. Configurer la réponse HTTP pour le téléchargement ou l'affichage inline
            response.setContentType(dossier.getTypeMime() != null ? dossier.getTypeMime() : "application/pdf");
            response.setContentLengthLong(pdfFile.length());
            // Pour afficher dans le navigateur (permet ensuite d'imprimer/sauvegarder)
            response.setHeader("Content-Disposition", "inline; filename=\"" + dossier.getNomFichier() + "\"");
            // Pour forcer le téléchargement direct :
            // response.setHeader("Content-Disposition", "attachment; filename=\"" + dossier.getNomFichier() + "\"");

            // 7. Envoyer le contenu du fichier dans le flux de réponse
            try (FileInputStream fis = new FileInputStream(pdfFile);
                 OutputStream os = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush(); // S'assurer que tout est écrit
                System.out.println("TelechargerDossierServlet: Fichier " + dossier.getNomFichier() + " envoyé au client.");
            } catch (IOException e) {
                System.err.println("TelechargerDossierServlet: IOException lors de l'envoi du fichier: " + e.getMessage());
                // Il est possible que la connexion client ait été fermée, difficile d'envoyer une erreur HTTP ici.
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            System.err.println("TelechargerDossierServlet: ID d'hospitalisation invalide: " + hospitalisationIdStr);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID d'hospitalisation au format invalide.");
        } catch (SQLException e) {
            System.err.println("TelechargerDossierServlet: SQLException: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur de base de données lors de la récupération du dossier.");
        }
    }
}