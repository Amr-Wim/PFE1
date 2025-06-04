package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Patient;
import java.io.IOException;
import java.sql.SQLException;
import dao.HospitalisationDAO;
// Retirer ChambreDAO et MedecinDAO si plus utilisés directement ici

@WebServlet("/patient/hospitalisation") // Assure-toi que cette URL est bien celle appelée
public class HospitalisationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Bonne pratique

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("HospitalisationServlet (patient): Entrée dans doGet.");
        HttpSession session = request.getSession(false); // Ne pas créer de nouvelle session
        
        if (session == null || session.getAttribute("patient") == null) {
            System.out.println("HospitalisationServlet (patient): Session invalide ou patient non trouvé. Redirection vers login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Rediriger vers la page de login de l'application
            return;
        }
        Patient patient = (Patient) session.getAttribute("patient");
        System.out.println("HospitalisationServlet (patient): Patient en session: ID " + patient.getId());

        try {
            HospitalisationDAO hospDAO = new HospitalisationDAO();
            // getCurrentByPatientId est maintenant enrichi avec les infos de lit/chambre/service de chambre/médecin
            Hospitalisation hosp = hospDAO.getCurrentByPatientId(patient.getId());
            
            if (hosp != null) {
                System.out.println("HospitalisationServlet (patient): Hospitalisation trouvée: ID " + hosp.getId() + ", Lit ID: " + hosp.getLitId());
                if(hosp.getLitId() != null) {
                    System.out.println("Details: Lit " + hosp.getNumeroLit() + ", Chambre " + hosp.getNumeroChambre() + ", Service Chambre " + hosp.getNomServiceChambre());
                }
                if(hosp.getMedecin() != null) {
                    System.out.println("Médecin: " + hosp.getMedecin().getNom());
                }
                session.setAttribute("hospitalisation", hosp);
            } else {
                 System.out.println("HospitalisationServlet (patient): Aucune hospitalisation active trouvée pour patient ID " + patient.getId());
                 session.removeAttribute("hospitalisation"); // Assurer qu'il n'y a pas d'ancienne hospitalisation en session
            }
            
            // S'assurer que le chemin vers la JSP est correct. Si elle est à la racine: "/hospitalisation.jsp"
            // Si elle est dans WEB-INF/jsp: "/WEB-INF/jsp/hospitalisation.jsp"
            request.getRequestDispatcher("/hospitalisation.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des données d'hospitalisation: " + e.getMessage());
            // S'assurer que le chemin vers error.jsp est correct
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}