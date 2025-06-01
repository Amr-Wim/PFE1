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
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

@WebServlet("/PlanifierExamen1")
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

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("Action reçue: " + action); // Debug

        try {
            if ("getExamsByType".equals(action)) {
                String typeIdStr = request.getParameter("typeId");
                System.out.println("Type ID reçu: " + typeIdStr); // Debug
                
                if (typeIdStr == null || typeIdStr.isEmpty()) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("[]");
                    return;
                }
                
                int typeId = Integer.parseInt(typeIdStr);
                System.out.println("Recherche des examens pour typeId: " + typeId); // Debug
                
                List<Examen> examens = examenDAO.getExamensByType(typeId);
                System.out.println("Nombre d'examens trouvés: " + examens.size()); // Debug
                
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < examens.size(); i++) {
                    Examen examen = examens.get(i);
                    System.out.println("Examen " + i + ": " + examen.getNomExamen()); // Debug
                    
                    json.append("{")
                       .append("\"id\":").append(examen.getId()).append(",")
                       .append("\"nomExamen\":\"").append(escapeJson(examen.getNomExamen())).append("\",")
                       .append("\"doitEtreAJeun\":").append(examen.isDoitEtreAJeun()).append(",")
                       .append("\"dureePreparationResultatsHeures\":");
                    
                    if (examen.getDureePreparationResultatsHeures() != null) {
                        json.append(examen.getDureePreparationResultatsHeures());
                    } else {
                        json.append("null");
                    }
                    
                    json.append("}");
                    
                    if (i < examens.size() - 1) json.append(",");
                }
                json.append("]");
                
                System.out.println("JSON généré: " + json.toString()); // Debug important
                
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json.toString());
                return;
            }
            
            // Chargement initial de la page
            List<Patient> patients = patientDAO.getAllPatients();
            List<TypeExamen> typesExamens = typeExamenDAO.getAllTypesExamens();
            
            System.out.println("Nombre de patients chargés: " + patients.size()); // Debug
            System.out.println("Nombre de types d'examens chargés: " + typesExamens.size()); // Debug
            
            request.setAttribute("patients", patients);
            request.setAttribute("typesExamens", typesExamens);
            
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Erreur dans doGet: " + e.getMessage());
            e.printStackTrace();
            
            if ("getExamsByType".equals(action)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Erreur serveur: " + escapeJson(e.getMessage()) + "\"}");
            } else {
                request.setAttribute("error", "Erreur lors du chargement: " + e.getMessage());
                request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
            }
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer idMedecin = (Integer) session.getAttribute("userId");
        System.out.println("Tentative de création de demande par médecin ID: " + idMedecin); // Debug

        if (idMedecin == null) {
            request.setAttribute("error", "Session invalide. Veuillez vous reconnecter.");
            loadInitialData(request);
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
            return;
        }
        
        try {
            String idPatientStr = request.getParameter("idPatient");
            String notesMedecin = request.getParameter("notesMedecin");
            String selectedExamsStr = request.getParameter("selectedExams");

            System.out.println("Paramètres reçus - Patient: " + idPatientStr + 
                             ", Notes: " + notesMedecin + 
                             ", Examens: " + selectedExamsStr); // Debug
            
            if (idPatientStr == null || idPatientStr.isEmpty()) {
                throw new Exception("Patient non sélectionné");
            }
            int idPatient = Integer.parseInt(idPatientStr);

            if (selectedExamsStr == null || selectedExamsStr.isEmpty()) {
                throw new Exception("Aucun examen sélectionné");
            }
            
            String[] selectedExamsIds = selectedExamsStr.split(",");
            System.out.println("Examens à créer: " + selectedExamsIds.length); // Debug
            
            int demandesCrees = 0;
            List<String> successfullyAddedExams = new ArrayList<>();

            for (String examIdStr : selectedExamsIds) {
                if (!examIdStr.trim().isEmpty()) {
                    int idExamen = Integer.parseInt(examIdStr.trim());
                    System.out.println("Création demande pour examen ID: " + idExamen); // Debug
                    
                    DemandeExamen demande = new DemandeExamen();
                    demande.setIdPatient(idPatient);
                    demande.setIdMedecinPrescripteur(idMedecin);
                    demande.setIdExamen(idExamen);
                    demande.setDateDemande(new Date());
                    demande.setStatutDemande("Demandé");
                    demande.setNotesMedecin(notesMedecin);
                    
                    Examen examenDetails = examenDAO.getExamenById(idExamen);
                    if (examenDetails != null && examenDetails.getDureePreparationResultatsHeures() != null) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(demande.getDateDemande());
                        cal.add(Calendar.HOUR_OF_DAY, examenDetails.getDureePreparationResultatsHeures());
                        demande.setDateEstimeeResultatsPrets(cal.getTime());
                    }
                    
                    if (demandeExamenDAO.creerDemande(demande)) {
                        demandesCrees++;
                        String nomExamen = examenDetails != null ? examenDetails.getNomExamen() : "ID: " + idExamen;
                        successfullyAddedExams.add(nomExamen);
                        System.out.println("Demande créée pour: " + nomExamen); // Debug
                    }
                }
            }
            
            if (demandesCrees > 0) {
                request.setAttribute("successMessage", "Succès: " + demandesCrees + " demande(s) créée(s)");
                System.out.println("Demandes créées avec succès: " + successfullyAddedExams); // Debug
            } else {
                request.setAttribute("error", "Aucune demande n'a pu être créée");
            }

            doGet(request, response);
            
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format: " + e.getMessage());
            request.setAttribute("error", "ID invalide");
            loadInitialData(request);
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Erreur dans doPost: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Erreur: " + e.getMessage());
            loadInitialData(request);
            request.getRequestDispatcher("/planifierExamen.jsp").forward(request, response);
        }
    }

    private void loadInitialData(HttpServletRequest request) {
        try {
            List<Patient> patients = patientDAO.getAllPatients();
            List<TypeExamen> typesExamens = typeExamenDAO.getAllTypesExamens();
            request.setAttribute("patients", patients);
            request.setAttribute("typesExamens", typesExamens);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des données: " + e.getMessage());
            request.setAttribute("errorLoadingData", "Erreur de chargement des données");
        }
    }
}