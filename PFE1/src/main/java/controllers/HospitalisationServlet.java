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
        
        try {
            HttpSession session = request.getSession();
            Patient patient = (Patient) session.getAttribute("patient");
            
            if (patient != null) {
                HospitalisationDAO hospDAO = new HospitalisationDAO();
                Hospitalisation hosp = hospDAO.getCurrentByPatientId(patient.getId());
                
                if (hosp != null) {
                    MedecinDAO medecinDAO = new MedecinDAO();
                    Medecin medecin = medecinDAO.getById(hosp.getIdMedecin());
                    hosp.setMedecin(medecin);
                }
                
                request.setAttribute("hospitalisation", hosp);
            }
            
            request.getRequestDispatcher("/hospitalisation.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Erreur de base de données", e);
        }
    }
}