package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Medecin;
import model.Patient;

import java.io.IOException;
import java.sql.SQLException;

import dao.HospitalisationDAO;
import dao.MedecinDAO;

@WebServlet("/patient/hospitalisation")
public class HospitalisationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	
        HttpSession session = request.getSession();
        Patient patient = (Patient) session.getAttribute("patient");
        
        
        if (patient == null) {
            System.out.println("DEBUG - Aucun patient en session, redirection vers login");
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("DEBUG - Recherche hospitalisation pour patient ID: " + patient.getId());
        
        try {
            HospitalisationDAO hospDAO = new HospitalisationDAO();
            Hospitalisation hosp = hospDAO.getCurrentByPatientId(patient.getId());
            
            
            System.out.println("DEBUG - Résultat hospitalisation: " + (hosp != null ? "trouvé" : "non trouvé"));
            
            if (hosp != null) {
                System.out.println("DEBUG - Hospitalisation trouvée: " + hosp.getId());
                
                if (hosp.getMedecin() == null && hosp.getIdMedecin() > 0) {
                    System.out.println("DEBUG - Chargement des infos médecin pour ID: " + hosp.getIdMedecin());
                    MedecinDAO medecinDAO = new MedecinDAO();
                    Medecin medecin = medecinDAO.getById(hosp.getIdMedecin());
                    hosp.setMedecin(medecin);
                }
                session.setAttribute("hospitalisation", hosp);
            }
            
            request.getRequestDispatcher("/hospitalisation.jsp").forward(request, response);
            
        } catch (SQLException e) {
            System.err.println("ERREUR SQL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des données d'hospitalisation");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}