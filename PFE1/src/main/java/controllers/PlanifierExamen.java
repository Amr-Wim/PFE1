package controllers;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import java.io.IOException;
import java.util.List;


@WebServlet("/PlanifierExamen")
public class PlanifierExamen extends HttpServlet {
    
    private PatientDAO patientDAO;
    private TypeExamenDAO typeExamenDAO;
    private ExamenDAO examenDAO;
    private DemandeExamenDAO demandeExamenDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        patientDAO = new PatientDAO();
        typeExamenDAO = new TypeExamenDAO();
        examenDAO = new ExamenDAO();
        demandeExamenDAO = new DemandeExamenDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
        	
        	System.out.println("PlanifierExamen doGet() appelé");
        	List<Patient> patients = patientDAO.getAllPatients();
        	System.out.println("Nombre de patients récupérés: " + patients.size());
        	List<TypeExamen> typesExamens = typeExamenDAO.getAllTypesExamens();
        	System.out.println("Nombre de types d'examens récupérés: " + typesExamens.size());
        	
        	
            /* Récupérer la liste des patients et types d'examens
            List<Patient> patients = patientDAO.getAllPatients();
            List<TypeExamen> typesExamens = typeExamenDAO.getAllTypesExamens();*/
            
            request.setAttribute("patients", patients);
            request.setAttribute("typesExamens", typesExamens);
            
            // Gérer la requête AJAX pour les examens par type
            if ("getExamsByType".equals(request.getParameter("action"))) {
                int typeId = Integer.parseInt(request.getParameter("typeId"));
                List<Examen> examens = examenDAO.getExamensByType(typeId);
                
                // Convertir en JSON
                StringBuilder json = new StringBuilder("[");
                for (Examen examen : examens) {
                    if (json.length() > 1) json.append(",");
                    json.append(String.format(
                        "{\"id\":%d,\"nomExamen\":\"%s\",\"dureeEstimeeMinutes\":%d}",
                        examen.getId(),
                        examen.getNomExamen().replace("\"", "\\\""),
                        examen.getDureePreparationResultatsHeures() != null ? 
                            examen.getDureePreparationResultatsHeures() * 60 : 0
                    ));
                }
                json.append("]");
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json.toString());
                return;
            }
            
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des données: " + e.getMessage());
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
        }
    }

    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Récupérer les paramètres
            int idPatient = Integer.parseInt(request.getParameter("idPatient"));
            String notes = request.getParameter("notes");
            
            // Récupérer les examens sélectionnés (séparés par des virgules)
            String[] selectedExams = request.getParameter("selectedExams").split(",");
            
            // Récupérer l'ID du médecin
            HttpSession session = request.getSession();
            Integer idMedecin = (Integer) session.getAttribute("userId");
            
            if (idMedecin == null) {
                throw new Exception("Médecin non connecté");
            }

            // Créer une demande pour chaque examen
            for (String examId : selectedExams) {
                if (!examId.isEmpty()) {
                    DemandeExamen demande = new DemandeExamen();
                    demande.setIdPatient(idPatient);
                    demande.setIdMedecinPrescripteur(idMedecin);
                    demande.setIdExamen(Integer.parseInt(examId));
                    demande.setDateDemande(new java.util.Date());
                    demande.setStatutDemande("Demandé");
                    demande.setNotesMedecin(notes);
                    
                    // Calcul date estimée résultats
                    Examen examen = examenDAO.getExamenById(demande.getIdExamen());
                    if (examen.getDureePreparationResultatsHeures() != null) {
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.add(java.util.Calendar.HOUR, examen.getDureePreparationResultatsHeures());
                        demande.setDateEstimeeResultatsPrets(cal.getTime());
                    }
                    
                    demandeExamenDAO.creerDemande(demande);
                }
            }
            
            request.setAttribute("successMessage", selectedExams.length + " examens planifiés avec succès");
            doGet(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la planification: " + e.getMessage());
            doGet(request, response);
        }
    }
}