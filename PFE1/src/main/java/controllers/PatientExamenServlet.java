package controllers;

import dao.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/PatientExamens")
public class PatientExamenServlet extends HttpServlet {
    
    private DemandeExamenDAO demandeExamenDAO;
    private ExamenDAO examenDAO;
    private TypeExamenDAO typeExamenDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        demandeExamenDAO = new DemandeExamenDAO();
        examenDAO = new ExamenDAO();
        typeExamenDAO = new TypeExamenDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer patientId = (Integer) session.getAttribute("userId");
        System.out.println("DEBUG - Patient ID from session: " + patientId);
        
        try {
            List<DemandeExamen> demandes = demandeExamenDAO.getDemandesByPatientId(patientId);
            System.out.println("DEBUG - Number of exams found: " + demandes.size());
            
            // Debug: Afficher le contenu de chaque demande
            for (DemandeExamen demande : demandes) {
                System.out.println("DEBUG - Exam: " + demande.getExamen().getNomExamen() 
                    + ", Date: " + demande.getDateDemande());
            }
            
            request.setAttribute("demandesExamens", demandes);
            request.getRequestDispatcher("/analyses.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("ERROR in PatientExamenServlet: ");
            e.printStackTrace();
            request.setAttribute("error", "Erreur technique: " + e.getMessage());
            request.getRequestDispatcher("/patientDashboard.jsp").forward(request, response);
        }
    }
    private void debugDemande(DemandeExamen demande) {
        System.out.println("=== DEBUG DEMANDE ===");
        System.out.println("ID: " + demande.getId());
        System.out.println("Statut: " + demande.getStatutDemande());
        System.out.println("Notes médecin: " + demande.getNotesMedecin());
        
        if(demande.getExamen() != null) {
            System.out.println("Examen: " + demande.getExamen().getNomExamen());
            System.out.println("À jeun: " + demande.getExamen().isDoitEtreAJeun());
            
            if(demande.getExamen().getTypeExamen() != null) {
                System.out.println("Type: " + demande.getExamen().getTypeExamen().getNomType());
            }
        }
    }}