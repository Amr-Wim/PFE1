package controllers;

import dao.ObservationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Pas besoin de HttpSession pour ce test
// import jakarta.servlet.http.HttpSession; 
import model.Infirmier;
import model.Observation;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/AjouterObservationServlet")
public class AjouterObservationServlet extends HttpServlet {
    private ObservationDao observationDao;

    @Override
    public void init() {
        this.observationDao = new ObservationDao();
    }

    /**
     * Gère l'affichage initial du formulaire.
     * ON SUPPRIME LA PROTECTION POUR LE TEST.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- doGet: Affichage du formulaire (SANS VÉRIFICATION DE SESSION) ---");
        request.getRequestDispatcher("/ajouterobservation.jsp").forward(request, response);
    }

    /**
     * Gère la soumission du formulaire.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // HttpSession session = request.getSession(false);
        
        // --- ON MET LA VÉRIFICATION DE SESSION EN COMMENTAIRE POUR LE TEST ---
        /*
        if (session == null || session.getAttribute("infirmier") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=accesRefuse");
            return;
        }
        Infirmier infirmierConnecte = (Infirmier) session.getAttribute("infirmier");
        */

        // --- ON "TRICHE" EN CRÉANT UN FAUX OBJET INFIRMIER ---
        // On suppose que l'infirmier connecté est celui avec l'ID 13.
        // Changez cet ID si vous voulez tester avec un autre.
        Infirmier infirmierConnecte = new Infirmier();
        infirmierConnecte.setId(13); 
        System.out.println("--- doPost: Utilisation d'un infirmier 'test' avec ID=" + infirmierConnecte.getId() + " (SANS VÉRIFICATION DE SESSION) ---");
        // --------------------------------------------------------

        String idPatientParam = request.getParameter("idPatient");
        
        try {
            Observation nouvelleObs = new Observation();
            nouvelleObs.setIdPatient(Integer.parseInt(idPatientParam));
            nouvelleObs.setTemperature(Float.parseFloat(request.getParameter("temperature")));
            nouvelleObs.setTension(request.getParameter("tension"));
            nouvelleObs.setFrequence(Integer.parseInt(request.getParameter("frequence")));
            nouvelleObs.setTexte(request.getParameter("observation"));
            nouvelleObs.setDate(Date.valueOf(request.getParameter("dateObservation")));
            nouvelleObs.setIdInfirmier(infirmierConnecte.getId()); // On utilise l'ID de notre faux infirmier

            observationDao.ajouterObservation(nouvelleObs);
            
            // On ne peut plus utiliser la session pour les messages, on met un message simple en request
            request.setAttribute("successMessage", "L'observation a été ajoutée avec succès ! (Mode Test)");
            // On redirige vers la page de recherche
            response.sendRedirect(request.getContextPath() + "/RechercherObservationServlet?idPatient=" + idPatientParam);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'ajout : " + e.getMessage());
            // En cas d'erreur, on ré-affiche le formulaire
            request.getRequestDispatcher("/WEB-INF/views/ajouterobservation.jsp").forward(request, response);
        }
    }
}