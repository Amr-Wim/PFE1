package controllers; // Ou controllers.medecin

import dao.HospitalisationDAO;
import dao.PatientDAO;
import model.Hospitalisation;
import model.Medecin;
import model.Patient;

import service.DossierSortieService; // AJOUT
import model.DossierSortie;          // AJOUT
import model.Utilisateur;          // AJOUT


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/preparerSortie")
public class PreparerSortieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HospitalisationDAO hospitalisationDAO;
    private PatientDAO patientDAO;
    private DossierSortieService dossierSortieService;

    public void init() throws ServletException {
        hospitalisationDAO = new HospitalisationDAO();
        patientDAO = new PatientDAO();
        dossierSortieService = new DossierSortieService(); // AJOUT
    }

    // Afficher le formulaire de préparation de sortie
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String hospitalisationIdStr = request.getParameter("hospitalisationId");
        if (hospitalisationIdStr == null) {
            request.setAttribute("error", "ID d'hospitalisation manquant.");
            // Rediriger vers la liste des hospitalisations avec un message
            response.sendRedirect(request.getContextPath() + "/mesHospitalisations?error=IDHospManquant");
            return;
        }

        try {
            int hospitalisationId = Integer.parseInt(hospitalisationIdStr);
            // Utiliser la méthode DAO qui charge toutes les infos, y compris les champs texte pour la sortie
            Hospitalisation hosp = hospitalisationDAO.getById(hospitalisationId); // Tu devras créer/adapter cette méthode

            if (hosp == null) {
                request.setAttribute("error", "Hospitalisation non trouvée.");
                 response.sendRedirect(request.getContextPath() + "/mesHospitalisations?error=HospNonTrouvee");
                return;
            }
            
            // Vérifier si le médecin connecté est bien le médecin de cette hospitalisation (sécurité)
            Medecin medecinConnecte = (Medecin) session.getAttribute("medecin");
            if (hosp.getIdMedecin() != medecinConnecte.getId()) {
                 request.setAttribute("error", "Vous n'êtes pas autorisé à préparer la sortie pour cette hospitalisation.");
                 response.sendRedirect(request.getContextPath() + "/mesHospitalisations?error=NonAutorise");
                return;
            }

            Patient patient = patientDAO.getPatientById(hosp.getIdPatient()); // Pour afficher le nom etc.

            request.setAttribute("hospitalisation", hosp);
            request.setAttribute("patient", patient);
            request.getRequestDispatcher("/finaliser_sortie_form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID d'hospitalisation invalide.");
            response.sendRedirect(request.getContextPath() + "mesHospitalisations?error=IDHospInvalide");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur base de données: " + e.getMessage());
            // Peut-être forward vers une page d'erreur générale ou retour à la liste
            response.sendRedirect(request.getContextPath() + "mesHospitalisations?error=ErreurDB");
        }
    }

    // Traiter la soumission du formulaire de finalisation de sortie
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Medecin medecinConnecte = (Medecin) session.getAttribute("medecin");
        

        String hospitalisationIdStr = request.getParameter("hospitalisationId");
        // Récupérer les données du formulaire (dateSortieReelle, diagnostics_sortie, compte_rendu, instructions, rdv_suivi)
        String dateSortieReelleStr = request.getParameter("dateSortieReelle");
        String diagnosticsSortie = request.getParameter("diagnosticsSortie");
        String compteRendu = request.getParameter("compteRendu");
        String instructionsSortie = request.getParameter("instructionsSortie");
        String infoRdvSuivi = request.getParameter("infoRdvSuivi");
        // ... et l'état (devrait être "Sortie")

        // Logique de validation et de mise à jour...
        try {
            int hospitalisationId = Integer.parseInt(hospitalisationIdStr);
            Hospitalisation hosp = hospitalisationDAO.getById(hospitalisationId); // Récupérer l'existant

            if (hosp == null || hosp.getIdMedecin() != medecinConnecte.getId()) {
                 request.setAttribute("error", "Hospitalisation non trouvée ou accès non autorisé.");
                 // Rediriger ou forwarder avec erreur
                 response.sendRedirect(request.getContextPath() + "/mesHospitalisations?error=PostNonAutorise");
                 return;
            }
            
            // Mettre à jour l'objet hosp
            if(dateSortieReelleStr != null && !dateSortieReelleStr.isEmpty()){
                hosp.setDateSortieReelle(java.sql.Timestamp.valueOf(java.time.LocalDateTime.parse(dateSortieReelleStr))); // Ou juste Date si c'est une date
                hosp.setDateSortieReelle(new java.sql.Timestamp(System.currentTimeMillis()));
            }
            hosp.setDiagnosticsSortie(diagnosticsSortie);
            hosp.setCompteRenduHospitalisation(compteRendu);
            hosp.setInstructionsSortie(instructionsSortie);
            hosp.setInformationsRendezVousSuivi(infoRdvSuivi);
            hosp.setEtat("Sortie"); // Marquer comme sortie

            boolean updated = hospitalisationDAO.updateInfosSortieHospitalisation(hosp); // Tu as déjà cette méthode

            if (updated) {
                System.out.println("PreparerSortieServlet: Hospitalisation ID " + hospitalisationId + " mise à jour.");
                try {
                    // Récupérer l'ID de l'utilisateur qui génère (médecin ou admin connecté)
                    Utilisateur utilisateurPourAudit = (Utilisateur) session.getAttribute("medecin"); // Ou "utilisateur"
                    int idUtilisateurActuel = (utilisateurPourAudit != null) ? utilisateurPourAudit.getId() : 0; // 0 ou un ID système si non loggué

                    DossierSortie dossierGenere = dossierSortieService.genererEtEnregistrerDossierPDF(hospitalisationId, idUtilisateurActuel);

                    if (dossierGenere != null && dossierGenere.getIdDossierSortie() > 0) {
                        session.setAttribute("successMessage", "Sortie du patient finalisée et dossier PDF (ID: " + dossierGenere.getIdDossierSortie() + ") généré.");
                    } else {
                        session.setAttribute("warningMessage", "Sortie finalisée, mais problème lors de la génération/enregistrement du dossier PDF.");
                    }
                } catch (Exception eGenPdf) {
                    eGenPdf.printStackTrace();
                    session.setAttribute("errorMessage", "Sortie finalisée, mais ERREUR critique lors de la génération du dossier PDF: " + eGenPdf.getMessage());
                }
            } else {
                session.setAttribute("errorMessage", "Échec de la mise à jour des informations de sortie de l'hospitalisation.");
            }
            response.sendRedirect(request.getContextPath() + "/medecin/mesHospitalisations"); // Toujours rediriger

       } catch (NumberFormatException e) {
           session.setAttribute("errorMessage", "ID d'hospitalisation invalide.");
           response.sendRedirect(request.getContextPath() + "/medecin/mesHospitalisations");
       } catch (SQLException e) {
           e.printStackTrace();
           session.setAttribute("errorMessage", "Erreur base de données lors de la finalisation: " + e.getMessage());
           response.sendRedirect(request.getContextPath() + "/medecin/mesHospitalisations");
       }
   }
}