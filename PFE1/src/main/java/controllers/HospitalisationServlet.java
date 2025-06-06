package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Patient;
import model.DossierSortie;
import dao.HospitalisationDAO;
import dao.DossierSortieDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/patient/hospitalisation")
public class HospitalisationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private HospitalisationDAO hospitalisationDAO;
    private DossierSortieDAO dossierSortieDAO;

    @Override
    public void init() throws ServletException {
        super.init(); 
        hospitalisationDAO = new HospitalisationDAO();
        dossierSortieDAO = new DossierSortieDAO();
        System.out.println("DEBUG: HospitalisationServlet initialisé.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("DEBUG: HospitalisationServlet (patient) - Entrée dans doGet.");
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            System.out.println("DEBUG: HospitalisationServlet (patient) - Session est NULL. Redirection vers login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        Patient patient = (Patient) session.getAttribute("patient");
        if (patient == null) {
            System.out.println("DEBUG: HospitalisationServlet (patient) - Attribut 'patient' est NULL dans la session. Redirection vers login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        System.out.println("DEBUG: HospitalisationServlet (patient) - Patient en session: ID " + patient.getId() + ", Nom: " + patient.getNom());

        Hospitalisation hosp = null; // Déclarer en dehors du try pour le logging final
        boolean dossierDispo = false; // Initialiser

        try {
            String hospitalisationIdParam = request.getParameter("hospitalisationId");
            
            if (hospitalisationIdParam != null && !hospitalisationIdParam.isEmpty()) {
                try {
                    int hospId = Integer.parseInt(hospitalisationIdParam);
                    System.out.println("DEBUG: HospitalisationServlet (patient) - Tentative de chargement de l'hospitalisation par ID explicite: " + hospId);
                    hosp = hospitalisationDAO.getById(hospId); // Assure-toi que getById charge tous les détails
                } catch (NumberFormatException e) {
                    System.err.println("DEBUG: HospitalisationServlet (patient) - hospitalisationIdParam n'est pas un nombre valide: " + hospitalisationIdParam);
                }
            } else {
                System.out.println("DEBUG: HospitalisationServlet (patient) - Chargement de la dernière hospitalisation pour patient ID: " + patient.getId());
                // Assure-toi que cette méthode existe et est correcte dans HospitalisationDAO
                hosp = hospitalisationDAO.getLatestHospitalisationByPatientIdWithDetails(patient.getId());
            }
            
            if (hosp != null) {
                // Sécurité : Vérifier que l'hospitalisation appartient bien au patient connecté
                if (hosp.getIdPatient() != patient.getId()) {
                    System.err.println("DEBUG: HospitalisationServlet (patient) - ACCÈS NON AUTORISÉ à l'hospitalisation ID " + hosp.getId() + " par patient ID " + patient.getId());
                    session.removeAttribute("hospitalisation");
                    request.setAttribute("errorMessage", "Accès non autorisé à cette hospitalisation.");
                    // Mettre dossierDispo à false explicitement
                    request.setAttribute("dossierSortieDisponible", false);
                    dossierDispo = false; 
                } else {
                    System.out.println("DEBUG: HospitalisationServlet (patient) - Hospitalisation chargée: ID " + hosp.getId() + ", État: [" + hosp.getEtat() + "], Lit ID: " + hosp.getLitId());
                    session.setAttribute("hospitalisation", hosp); // Mis en session pour la JSP

                    String etatHospitalisation = hosp.getEtat();
                    if (etatHospitalisation != null && "Sortie".equalsIgnoreCase(etatHospitalisation.trim())) {
                        System.out.println("DEBUG: HospitalisationServlet (patient) - État est 'Sortie', recherche du dossier pour HospID: " + hosp.getId());
                        DossierSortie dossier = dossierSortieDAO.getByHospitalisationId(hosp.getId());
                        if (dossier != null) {
                            request.setAttribute("dossierSortieDisponible", true);
                            dossierDispo = true;
                            System.out.println("DEBUG: HospitalisationServlet (patient) - Dossier de sortie DISPONIBLE. Fichier: " + dossier.getNomFichier());
                        } else {
                            request.setAttribute("dossierSortieDisponible", false);
                            dossierDispo = false;
                            System.out.println("DEBUG: HospitalisationServlet (patient) - Dossier de sortie NON TROUVÉ en BDD pour hosp ID " + hosp.getId());
                        }
                    } else {
                        request.setAttribute("dossierSortieDisponible", false);
                        dossierDispo = false;
                        System.out.println("DEBUG: HospitalisationServlet (patient) - Hospitalisation ID " + hosp.getId() + " n'est pas à l'état 'Sortie'. État actuel: [" + etatHospitalisation + "]");
                    }
                }
            } else {
                 System.out.println("DEBUG: HospitalisationServlet (patient): Aucune hospitalisation trouvée pour patient ID " + patient.getId());
                 session.removeAttribute("hospitalisation"); 
                 request.setAttribute("dossierSortieDisponible", false);
                 dossierDispo = false;
            }
            
            System.out.println("DEBUG: HospitalisationServlet (patient) - AVANT FORWARD: hosp est " + (hosp == null ? "null" : "non null (ID: " + hosp.getId() + ")") + ", dossierSortieDisponible=" + dossierDispo);
            request.getRequestDispatcher("/hospitalisation.jsp").forward(request, response); 
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des données d'hospitalisation: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}