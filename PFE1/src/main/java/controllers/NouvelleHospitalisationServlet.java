package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.PatientDAO; // Assure-toi d'avoir un PatientDAO
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Medecin;
import model.Patient; // Ton modèle Patient

@WebServlet("/nouvelleHospitalisationForm") // Choisis une URL claire pour afficher le formulaire
public class NouvelleHospitalisationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Ne pas créer de session si elle n'existe pas

        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Rediriger vers login si pas connecté
            return;
        }

        Medecin medecin = (Medecin) session.getAttribute("medecin");
        // Si medecin.getNomService() ou getNomHopital() sont null ici,
        // c'est que MedecinDAO ne les a pas peuplés correctement.

        PatientDAO patientDAO = new PatientDAO(); // Ton DAO pour les patients
        List<Patient> patientsList = null;
        try {
            patientsList = patientDAO.getAllActivePatients();
            System.out.println("NouvelleHospitalisationServlet: Nombre de patients récupérés: " + (patientsList != null ? patientsList.size() : "null")); // DEBUG
        } catch (SQLException e) {
        	e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération de la liste des patients.");
            // Gérer l'erreur, peut-être rediriger vers une page d'erreur ou afficher un message
        }

        request.setAttribute("patientsList", patientsList);
        // L'objet medecin est déjà en session, donc accessible via ${sessionScope.medecin} dans la JSP
        request.getRequestDispatcher("/nouvelleHospitalisation.jsp").forward(request, response); // Utilise un chemin sous WEB-INF pour la JSP
    }
}