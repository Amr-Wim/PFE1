package controllers;

import dao.HospitalisationDAO;
import dao.LitDAO;
import dao.PatientDAO;
import dao.ServiceDAO;
import model.Hospitalisation;
import model.Lit;
import model.Patient;
import model.Service;
import model.Utilisateur; // Pour vérifier le rôle
import service.GestionLitService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/changerLit")
public class ChangerLitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HospitalisationDAO hospitalisationDAO;
    private PatientDAO patientDAO;
    private LitDAO litDAO;
    private ServiceDAO serviceDAO;
    private GestionLitService gestionLitService;

    @Override
    public void init() throws ServletException {
        hospitalisationDAO = new HospitalisationDAO();
        patientDAO = new PatientDAO();
        litDAO = new LitDAO();
        serviceDAO = new ServiceDAO();
        gestionLitService = new GestionLitService();
    }

    private boolean isAdmin(HttpSession session) {
        if (session == null) return false;
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur"); // Ou "admin" si c'est l'attribut
        return utilisateur != null && "admin".equals(utilisateur.getRole()); // ou autre rôle autorisé
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) { // Utilisation de la méthode isAdmin
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String hospitalisationIdStr = request.getParameter("hospitalisationId");
        String patientIdPourRechercheLitsStr = request.getParameter("patientIdPourRechercheLits");


        try {
             // Toujours charger la liste des hospitalisations actives pour la sélection initiale
             // ou une méthode pour rechercher une hospitalisation par ID patient/nom
             // Pour l'instant, on assume qu'on arrive ici avec un hospitalisationId ou patientId

            if ("rechercherLits".equals(action) && hospitalisationIdStr != null) {
                int hospitalisationId = Integer.parseInt(hospitalisationIdStr);
                Hospitalisation hosp = hospitalisationDAO.getById(hospitalisationId);

                if (hosp == null) {
                    request.setAttribute("errorMessage", "Hospitalisation non trouvée pour ID: " + hospitalisationId);
                } else {
                    Patient patient = patientDAO.getPatientById(hosp.getIdPatient());
                    if (patient == null) {
                        request.setAttribute("errorMessage", "Patient non trouvé pour l'hospitalisation ID: " + hospitalisationId);
                    } else {
                        request.setAttribute("hospitalisation", hosp);
                        request.setAttribute("patient", patient);

                        // Déterminer le service pour la recherche de lits.
                        // Priorité : service de la chambre actuelle si le lit est déjà attribué et a une chambre dans un service.
                        // Sinon : service de l'hospitalisation (service du médecin).
                        int serviceIdPourRecherche = 0;
                        if (hosp.getNomServiceChambre() != null && !hosp.getNomServiceChambre().isEmpty()) {
                            serviceIdPourRecherche = serviceDAO.getServiceIdByName(hosp.getNomServiceChambre());
                        }
                        if (serviceIdPourRecherche == 0 && hosp.getService() != null) { // Fallback sur le service de l'hospitalisation
                            serviceIdPourRecherche = serviceDAO.getServiceIdByName(hosp.getService());
                        }

                        if (serviceIdPourRecherche > 0) {
                            List<Lit> litsDisponibles = litDAO.trouverLitDisponiblePourService(serviceIdPourRecherche, patient.getSexe(), patient.getAge());
                            request.setAttribute("litsDisponibles", litsDisponibles);
                            System.out.println("ChangerLitServlet(doGet): " + (litsDisponibles != null ? litsDisponibles.size() : "0") +
                                               " lits disponibles trouvés pour service ID " + serviceIdPourRecherche +
                                               ", patient sexe: " + patient.getSexe() + ", age: " + patient.getAge());
                        } else {
                            System.err.println("ChangerLitServlet(doGet): Impossible de déterminer un service ID valide pour la recherche de lits. Hosp.Service: " + hosp.getService() + ", Hosp.ServiceChambre: " + hosp.getNomServiceChambre());
                            request.setAttribute("warningMessage", "Impossible de déterminer le service pour la recherche de lits.");
                        }
                    }
                }
            } else if (patientIdPourRechercheLitsStr != null) {
                 // Si on arrive par recherche patient pour trouver son hospitalisation et ensuite les lits
                 int patientId = Integer.parseInt(patientIdPourRechercheLitsStr);
                 Hospitalisation hosp = hospitalisationDAO.getCurrentByPatientId(patientId); // Ou une autre méthode pour trouver l'hosp active
                 if (hosp != null) {
                     // Rediriger vers l'action rechercherLits avec l'ID de l'hospitalisation trouvée
                     response.sendRedirect(request.getContextPath() + "/changerLit?action=rechercherLits&hospitalisationId=" + hosp.getId());
                     return; // Important pour éviter le forward en bas
                 } else {
                     request.setAttribute("errorMessage", "Aucune hospitalisation active trouvée pour le patient ID: " + patientId);
                 }
            }
            // Optionnel: Charger la liste de tous les patients hospitalisés ou tous les services pour les filtres
            // List<Patient> patientsHospitalises = patientDAO.getAllPatientsActuellementHospitalises();
            // request.setAttribute("patientsHospitalisesList", patientsHospitalises);
            // List<Service> services = serviceDAO.getAllServices();
            // request.setAttribute("servicesList", services);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erreur base de données: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID invalide fourni.");
            e.printStackTrace();
        }
        request.getRequestDispatcher("changer_lit_form.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!isAdmin(session)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String hospitalisationIdStr = request.getParameter("hospitalisationId");
        String ancienLitIdStr = request.getParameter("ancienLitId");
        String nouveauLitIdStr = request.getParameter("nouveauLitId");
        String raison = request.getParameter("raisonChangement"); // Optionnel, pour logs/historique

        String redirectURL = request.getContextPath() + "/changerLit"; // URL de base pour la redirection

        try {
            if (hospitalisationIdStr == null || hospitalisationIdStr.trim().isEmpty() ||
                nouveauLitIdStr == null || nouveauLitIdStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "ID d'hospitalisation et ID du nouveau lit sont requis.");
                response.sendRedirect(redirectURL + (hospitalisationIdStr != null ? "?action=rechercherLits&hospitalisationId=" + hospitalisationIdStr : ""));
                return;
            }

            int hospitalisationId = Integer.parseInt(hospitalisationIdStr);
            int nouveauLitId = Integer.parseInt(nouveauLitIdStr);
            Integer ancienLitId = null;

            if (ancienLitIdStr != null && !ancienLitIdStr.trim().isEmpty() && !"0".equals(ancienLitIdStr.trim()) && !"null".equalsIgnoreCase(ancienLitIdStr.trim())) {
                try {
                    ancienLitId = Integer.parseInt(ancienLitIdStr.trim());
                } catch (NumberFormatException e) {
                    System.err.println("ChangerLitServlet(doPost): Ancien Lit ID invalide '" + ancienLitIdStr + "', traité comme null.");
                    // ancienLitId restera null, ce qui est ok
                }
            }
            
            redirectURL += "?action=rechercherLits&hospitalisationId=" + hospitalisationId; // Pour la redirection en cas de succès ou d'erreur


            boolean success = gestionLitService.changerLitPatient(hospitalisationId, ancienLitId, nouveauLitId, raison);

            if (success) {
                request.getSession().setAttribute("successMessage", "Le lit du patient a été changé avec succès.");
            } else {
                // Le service devrait lever une exception en cas d'échec critique,
                // mais on met un message d'erreur générique au cas où.
                request.getSession().setAttribute("errorMessage", "Échec du changement de lit. Vérifiez la disponibilité du lit.");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "ID d'hospitalisation ou de lit invalide.");
            e.printStackTrace();
            redirectURL = request.getContextPath() + "/changerLit" + (hospitalisationIdStr != null && !hospitalisationIdStr.isEmpty() ? "?action=rechercherLits&hospitalisationId=" + hospitalisationIdStr : "");
        } catch (SQLException | IllegalArgumentException e) {
            request.getSession().setAttribute("errorMessage", "Erreur lors du changement de lit: " + e.getMessage());
            e.printStackTrace();
            redirectURL = request.getContextPath() + "/changerLit" + (hospitalisationIdStr != null && !hospitalisationIdStr.isEmpty() ? "?action=rechercherLits&hospitalisationId=" + hospitalisationIdStr : "");
        }
        response.sendRedirect(redirectURL);
    }
}