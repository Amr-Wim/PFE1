package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.AllergieDAO;
import dao.AntecedantDAO;
import dao.DemandeExamenDAO;
import dao.DiagnosticDAO;
import dao.HospitalisationDAO;
import dao.InfirmierDao; // Assurez-vous que cet import est présent
import dao.MedecinDAO;
import dao.PatientDAO;
import dao.TraitementDAO;
import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DemandeExamen;
import model.Hospitalisation;
import model.Infirmier; // Assurez-vous que cet import est présent
import model.Medecin;
import model.Patient;
import model.Utilisateur;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // La méthode doPost principale
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String login = request.getParameter("login");
        String motDePasse = request.getParameter("mot_de_passe");

        try {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            Utilisateur utilisateur = utilisateurDAO.authentifier(login, motDePasse);

            if (utilisateur == null) {
                request.setAttribute("errorMessage", "Login ou mot de passe incorrect !");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            System.out.println("Utilisateur ID: " + utilisateur.getId());
            System.out.println("Rôle: " + utilisateur.getRole());

            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", utilisateur);
            // session.setAttribute("userId", utilisateur.getId()); // Redondant car déjà dans l'objet utilisateur

            // --- GESTION SPÉCIFIQUE PAR RÔLE ---
            
            if ("medecin".equals(utilisateur.getRole())) {
                try {
                    MedecinDAO medecinDAO = new MedecinDAO();
                    Medecin medecin = medecinDAO.getMedecinWithServiceAndHopital(utilisateur.getId());
                    
                    if (medecin != null) {
                        medecin.setNom(utilisateur.getNom());
                        medecin.setPrenom(utilisateur.getPrenom());
                        medecin.setEmail(utilisateur.getEmail());
                        session.setAttribute("medecin", medecin);
                    } else {
                        System.err.println("Aucune donnée trouvée pour le médecin ID: " + utilisateur.getId());
                    }
                } catch (SQLException e) {
                    System.err.println("Erreur DB lors du chargement du médecin: " + e.getMessage());
                }
            } else if ("patient".equals(utilisateur.getRole())) {
                handlePatientSession(utilisateur, session, request);
            } 
            // --- LA LIGNE AJOUTÉE EST ICI ---
            else if ("infirmier".equals(utilisateur.getRole())) {
                // On appelle la méthode que vous aviez déjà écrite
                handleInfirmierSession(utilisateur, session);
            }
            
            // Redirection selon le rôle
            redirectByRole(utilisateur.getRole(), response);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
            request.setAttribute("errorMessage", "Une erreur technique est survenue.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handlePatientSession(Utilisateur utilisateur, HttpSession session, HttpServletRequest request) {
        try {
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = patientDAO.getPatientById(utilisateur.getId());
            
            if (patient != null) {
                session.setAttribute("patient", patient);
                System.out.println("Patient stocké en session - ID: " + patient.getId());
                
                // Chargement des données médicales
                loadMedicalData(patient.getId(), session);
                
                // Chargement des demandes d'examens
                DemandeExamenDAO demandeExamenDAO = new DemandeExamenDAO();
                List<DemandeExamen> demandesExamens = demandeExamenDAO.getDemandesByPatientId(patient.getId());
                session.setAttribute("demandesExamens", demandesExamens);
                System.out.println("Demandes d'examens chargées: " + demandesExamens.size());
                
                // Hospitalisation
                HospitalisationDAO hospDAO = new HospitalisationDAO();
                Hospitalisation hospitalisation = hospDAO.getCurrentByPatientId(patient.getId());
                if (hospitalisation != null) {
                    session.setAttribute("hospitalisation", hospitalisation);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur chargement données patient: " + e.getMessage());
            session.setAttribute("warningMessage", "Certaines informations médicales ne sont pas disponibles");
        }
    }

    private void loadMedicalData(int patientId, HttpSession session) throws SQLException {
        AllergieDAO allergieDAO = new AllergieDAO();
        session.setAttribute("allergies", allergieDAO.getAllergiesByPatientId(patientId));
        
        TraitementDAO traitementDAO = new TraitementDAO();
        session.setAttribute("traitements", traitementDAO.getTraitementsByPatientId(patientId));
        
        AntecedantDAO antecedantDAO = new AntecedantDAO();
        session.setAttribute("antecedants", antecedantDAO.getAntecedantsByPatientId(patientId));
        
        DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
        session.setAttribute("diagnostics", diagnosticDAO.getDiagnosticsByPatientId(patientId));
    }
    
    private void handleInfirmierSession(Utilisateur utilisateur, HttpSession session) {
        try {
            InfirmierDao dao = new InfirmierDao(); 
            Infirmier infirmier = dao.findById(utilisateur.getId()); 
            
            if (infirmier != null) {
                infirmier.setNom(utilisateur.getNom());
                infirmier.setPrenom(utilisateur.getPrenom());
                infirmier.setEmail(utilisateur.getEmail());
                infirmier.setRole(utilisateur.getRole());
                
                session.setAttribute("infirmier", infirmier);
                System.out.println("LOGIN_SERVLET: L'attribut 'infirmier' a été ajouté à la session. ID de session: " + session.getId());
            } else {
                System.err.println("LOGIN_SERVLET (handleInfirmierSession): L'objet infirmier est NULL car non trouvé en BDD pour ID: " + utilisateur.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur DB lors du chargement de l'infirmier: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    private void redirectByRole(String role, HttpServletResponse response) throws IOException {
        switch (role) {
            case "patient":
                response.sendRedirect("patient_dashboard.jsp");
                break;
            case "medecin":
                response.sendRedirect("medecin_dashboard.jsp");
                break;
            case "infirmier":
                response.sendRedirect("infirmier_dashboard.jsp");
                break;
            case "admin":
                response.sendRedirect("adminDashboard");
                break;
            default:
                response.sendRedirect("login.jsp");
        }
    }
}