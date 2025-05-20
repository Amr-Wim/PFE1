

package controllers;

import java.io.IOException;
import java.sql.SQLException;

import dao.HospitalisationDAO;
import dao.PatientDAO;
import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Patient;
import model.Utilisateur;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String login = request.getParameter("login");
        String motDePasse = request.getParameter("mot_de_passe");

        try {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            Utilisateur utilisateur = utilisateurDAO.authentifier(login, motDePasse);

            if (utilisateur != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);

                if ("patient".equals(utilisateur.getRole())) {
                    // Récupérer les infos patient + hospitalisation
                    PatientDAO patientDAO = new PatientDAO();
                    HospitalisationDAO hospDAO = new HospitalisationDAO();
                    
                    try {
                        Patient patient = patientDAO.getPatientById(utilisateur.getId());
                        session.setAttribute("patient", patient);
                        
                        Hospitalisation hospitalisation = hospDAO.getCurrentByPatientId(patient.getId());
                        session.setAttribute("hospitalisation", hospitalisation);
                    } catch (SQLException e) {
                        // Log l'erreur mais continue sans les infos patient/hospitalisation
                        System.err.println("Erreur lors de la récupération des données patient: " + e.getMessage());
                        request.setAttribute("warningMessage", "Certaines informations ne sont pas disponibles");
                    }
                }

                switch (utilisateur.getRole()) {
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
            } else {
                request.setAttribute("errorMessage", "Login ou mot de passe incorrect !");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {  // Changé de SQLException vers Exception pour capturer toutes les erreurs
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
            request.setAttribute("errorMessage", "Une erreur technique est survenue. Veuillez réessayer.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}