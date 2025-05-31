package controllers;

import java.io.IOException;
import java.sql.SQLException;

import dao.AllergieDAO;
import dao.AntecedantDAO;
import dao.DiagnosticDAO;
import dao.HospitalisationDAO;
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
import model.Hospitalisation;
import model.Medecin;
import model.Patient;
import model.Utilisateur;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
            session.setAttribute("userId", utilisateur.getId());
            
            // Gestion spécifique pour les médecins
            if ("medecin".equals(utilisateur.getRole())) {
                try {
                    MedecinDAO medecinDAO = new MedecinDAO();
                    Medecin medecin = medecinDAO.getById(utilisateur.getId());
                    
                    if (medecin == null) {
                        System.err.println("Aucun médecin trouvé pour l'ID: " + utilisateur.getId());
                        throw new Exception("Profil médecin incomplet");
                    }
                    
                    
                    session.setAttribute("idMedecin", medecin.getId());
                    System.out.println("Médecin connecté: " + medecin.getId());
                    
                } catch (SQLException e) {
                    System.err.println("Erreur DB médecin: " + e.getMessage());
                    throw new ServletException("Erreur profil médecin", e);
                }
            }
            
            // Gestion spécifique pour les patients
            if ("patient".equals(utilisateur.getRole())) {
                handlePatientSession(utilisateur, session, request);
            }
            
            // Redirection selon le rôle
            redirectByRole(utilisateur.getRole(), response);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
            request.setAttribute("errorMessage", "Une erreur technique est survenue. Veuillez réessayer.");
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
                response.sendRedirect("admin_dashboard.jsp");
                break;
            default:
                response.sendRedirect("login.jsp");
        }
    }
}