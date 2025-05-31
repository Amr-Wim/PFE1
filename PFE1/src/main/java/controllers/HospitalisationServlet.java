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
	    // Récupérer directement depuis la session plutôt que de re-quérir la BD
	    Hospitalisation hosp = (Hospitalisation) session.getAttribute("hospitalisation");
	    
	    if (hosp != null && hosp.getIdMedecin() > 0) {
	        try {
	            MedecinDAO medecinDAO = new MedecinDAO();
	            Medecin medecin = medecinDAO.getById(hosp.getIdMedecin());
	            hosp.setMedecin(medecin);
	        } catch (SQLException e) {
	            // Logger l'erreur mais continuer sans les infos du médecin
	            System.err.println("Erreur récupération médecin: " + e.getMessage());
	        }
	    }
	    
	    // Transférer vers la JSP
	    request.getRequestDispatcher("/hospitalisation.jsp").forward(request, response);
	}
}