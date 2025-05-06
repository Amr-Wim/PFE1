package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.LitDAO;
import dao.PatientDAO;
import dao.ChambreDAO;
import model.Chambre;
import model.Lit;
import model.Patient;

@WebServlet("/Affectation")
public class AffectationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LitDAO litDAO;
    private ChambreDAO chambreDAO;
    private PatientDAO patientDAO;

    public void init() {
        litDAO = new LitDAO();
        chambreDAO = new ChambreDAO();
        patientDAO = new PatientDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	    try {
    	    	
    	        List<Chambre> chambres = chambreDAO.getAllChambres();
    	        List<Patient> patients = patientDAO.getAllPatients(); // Nouveau
    	        
    	        for (Chambre chambre : chambres) {
    	            List<Lit> lits = litDAO.getLitsByChambre(chambre.getId());
    	            chambre.setLits(lits);
    	            
    	            long disponibles = lits.stream().filter(Lit::isEstDisponible).count();
    	            chambre.setNombreLitsDisponibles((int)disponibles);
    	        }
    	        
    	        request.setAttribute("chambres", chambres);
    	        request.setAttribute("patients", patients); // Nouveau
    	        request.getRequestDispatcher("affectation_chambre.jsp").forward(request, response);
    	        
    	        
    	        
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des chambres");
            request.getRequestDispatcher("affectation_chambre.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idLit = Integer.parseInt(request.getParameter("idLit"));
            int idPatient = Integer.parseInt(request.getParameter("idPatient"));

            // Affecter le lit au patient
            boolean success = litDAO.affecterLitAPatient(idLit, idPatient);

            if (success) {
                request.setAttribute("success", "Lit affecté avec succès au patient");
            } else {
                request.setAttribute("error", "Échec de l'affectation du lit");
            }

            // Recharger la liste des chambres
            List<Chambre> chambres = chambreDAO.getAllChambres();
          
            for (Chambre chambre : chambres) {
                List<Lit> lits = litDAO.getLitsByChambre(chambre.getId());
                chambre.setLits(lits);
                
                long disponibles = lits.stream().filter(Lit::isEstDisponible).count();
                chambre.setNombreLitsDisponibles((int)disponibles);
            }
            
            request.setAttribute("chambres", chambres);
            
            List<Patient> patients = patientDAO.getAllPatients();
            request.setAttribute("patients", patients);

            
            request.getRequestDispatcher("affectation_chambre.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur dans AffectationServlet: " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la récupération des chambres: " + e.getMessage());
            request.getRequestDispatcher("affectation_chambre.jsp").forward(request, response);
        }request.getRequestDispatcher("affectation_chambre.jsp").forward(request, response);
        }


}