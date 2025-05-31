package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import dao.AllergieDAO;
import dao.AntecedantDAO;
import dao.DiagnosticDAO;
import dao.TraitementDAO;
import model.Allergie;
import model.Antecedant;
import model.Diagnostic;
import model.Traitement;
import model.Utilisateur;
import model.Patient;

@WebServlet("/DossierMedical")
public class DossierMedicalServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Patient patient = (Patient) session.getAttribute("patient");

        // Vérifier que l'utilisateur est connecté et est un patient
        if (utilisateur == null || !"patient".equals(utilisateur.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Récupérer l'ID du patient
            int patientId = patient.getId();

            // Récupérer les données médicales
            AllergieDAO allergieDAO = new AllergieDAO();
            List<Allergie> allergies = allergieDAO.getAllergiesByPatientId(patientId);

            AntecedantDAO antecedantDAO = new AntecedantDAO();
            List<Antecedant> antecedants = antecedantDAO.getAntecedantsByPatientId(patientId);

            DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
            List<Diagnostic> diagnostics = diagnosticDAO.getDiagnosticsByPatientId(patientId);

            TraitementDAO traitementDAO = new TraitementDAO();
            List<Traitement> traitements = traitementDAO.getTraitementsByPatientId(patientId);

            // Stocker les données dans la requête
            request.setAttribute("allergies", allergies);
            request.setAttribute("antecedants", antecedants);
            request.setAttribute("diagnostics", diagnostics);
            request.setAttribute("traitements", traitements);

            // Forward vers la JSP
            request.getRequestDispatcher("dossier_medical.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des données médicales");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}