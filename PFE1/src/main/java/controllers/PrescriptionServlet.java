package controllers;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// L'annotation est suffisante, pas besoin de web.xml pour cette servlet
@WebServlet("/PrescriptionServlet")
public class PrescriptionServlet extends HttpServlet {

    // --- Déclaration des DAOs. On ne les initialise PAS ici. ---
    private PatientDAO patientDAO;
    private TypeMedicamentDAO typeMedicamentDAO;
    private MedicamentDAO medicamentDAO;
    private DosageDAO dosageDAO;
    private PosologieDAO posologieDAO;
    private DureeTraitementDAO dureeTraitementDAO;
    private PrescriptionDAO prescriptionDAO;
    private PrescriptionDetailDAO detailDAO;

    /**
     * La méthode init() est appelée par Tomcat une seule fois, lorsque la servlet est chargée.
     * C'est le meilleur endroit pour initialiser les objets qui seront utilisés
     * pendant toute la durée de vie de la servlet.
     */
    @Override
    public void init() throws ServletException {
        // Initialisation de tous les objets DAO.
        // Cela suppose que vos classes DAO ont un constructeur par défaut (sans arguments).
        this.patientDAO = new PatientDAO();
        this.typeMedicamentDAO = new TypeMedicamentDAO();
        this.medicamentDAO = new MedicamentDAO();
        this.dosageDAO = new DosageDAO();
        this.posologieDAO = new PosologieDAO();
        this.dureeTraitementDAO = new DureeTraitementDAO();
        this.prescriptionDAO = new PrescriptionDAO();
        this.detailDAO = new PrescriptionDetailDAO();
    }

    /**
     * Gère les requêtes GET. Soit pour afficher la page, soit pour la requête AJAX.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getMedicaments".equals(action)) {
            handleAjaxGetMedicaments(request, response);
        } else {
            preparePage(request, response);
        }
    }

    /**
     * Prépare les données nécessaires et affiche la page JSP du formulaire.
     */
    private void preparePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("medecin") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp"); // Utiliser getContextPath pour des URLs robustes
                return;
            }
            // On utilise les DAOs initialisés dans init()
            request.setAttribute("patients", patientDAO.findAll());
            request.setAttribute("typesMedicaments", typeMedicamentDAO.findAll());
            request.setAttribute("dosages", dosageDAO.findAll());
            request.setAttribute("posologies", posologieDAO.findAll());
            request.setAttribute("durees", dureeTraitementDAO.findAll());

            // Redirection vers la JSP qui se trouve dans WEB-INF pour la sécurité
            request.getRequestDispatcher("/PrescriptionMedicale.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); // Important pour le débogage
            throw new ServletException("Erreur de base de données lors de la préparation de la page", e);
        }
    }

    /**
     * Traite la soumission du formulaire de prescription.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession(false);
            Medecin medecin = (Medecin) session.getAttribute("medecin");
            if (medecin == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // --- 1. Créer la prescription principale ---
            Prescription prescription = new Prescription();
            prescription.setIdMedecin(medecin.getId());
            prescription.setIdPatient(Integer.parseInt(request.getParameter("idPatient")));
            prescription.setDatePrescription(java.sql.Date.valueOf(request.getParameter("datePrescription")));
            
            int newPrescriptionId = prescriptionDAO.ajouterEtRetournerId(prescription);

            // --- 2. Récupérer et traiter les détails des médicaments ---
            String[] medicamentIds = request.getParameterValues("medicamentId");
            String[] dosageIds = request.getParameterValues("dosageId");
            String[] posologieIds = request.getParameterValues("posologieId");
            String[] dureeIds = request.getParameterValues("dureeId");

            if (medicamentIds != null && medicamentIds.length > 0) {
                List<PrescriptionDetail> details = new ArrayList<>();
             // Dans le doPost, à l'intérieur de la boucle for

                for (int i = 0; i < medicamentIds.length; i++) {
                    // AJOUTER DES VÉRIFICATIONS ICI
                    if (medicamentIds[i] == null || medicamentIds[i].isEmpty() ||
                        dosageIds[i] == null || dosageIds[i].isEmpty() ||
                        posologieIds[i] == null || posologieIds[i].isEmpty() ||
                        dureeIds[i] == null || dureeIds[i].isEmpty()) {
                        
                        // Si une des valeurs est vide, on ignore cette ligne ou on lève une erreur
                        System.err.println("Donnée de prescription invalide reçue à l'index " + i + ". Ligne ignorée.");
                        continue; // Passe à l'itération suivante de la boucle
                    }

                    PrescriptionDetail detail = new PrescriptionDetail();
                    detail.setIdPrescription(newPrescriptionId);
                    detail.setIdMedicament(Integer.parseInt(medicamentIds[i]));
                    detail.setIdDosage(Integer.parseInt(dosageIds[i]));
                    detail.setIdPosologie(Integer.parseInt(posologieIds[i]));
                    detail.setIdDureeTraitement(Integer.parseInt(dureeIds[i]));
                    details.add(detail);
                }
                // --- 3. Insérer tous les détails en une seule fois (plus performant) ---
                detailDAO.ajouterEnLot(details);
            }

            response.sendRedirect(request.getContextPath() + "/medecin_dashboard.jsp?status=prescriptionSuccess");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'enregistrement de la prescription. " + e.getMessage());
            // En cas d'erreur, on ré-affiche la page du formulaire avec un message d'erreur
            preparePage(request, response);
        }
    }
    
    /**
     * Gère la requête AJAX pour obtenir les médicaments filtrés par type.
     */
    private void handleAjaxGetMedicaments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String typeIdParam = request.getParameter("typeId");
            if (typeIdParam == null || typeIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"ID de type manquant.\"}");
                return;
            }

            int typeId = Integer.parseInt(typeIdParam);
            List<Medicament> medicaments = medicamentDAO.findByTypeId(typeId);
            
            // Construction manuelle du JSON (pas besoin de Gson)
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < medicaments.size(); i++) {
                Medicament m = medicaments.get(i);
                if (i > 0) json.append(",");
                json.append("{\"id\":").append(m.getId())
                    .append(",\"nom\":\"").append(m.getNom().replace("\"", "\\\"")).append("\"}");
            }
            json.append("]");
            
            out.print(json.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\":\"ID de type invalide.\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"Erreur de base de données.\"}");
            e.printStackTrace();
        }
    }
}