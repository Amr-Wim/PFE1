package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.PatientDossierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Patient;
import model.Utilisateur;

@WebServlet("/dossierPatient1")
public class AfficherDossierMedicalServlet extends HttpServlet {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        Patient patient = (Patient) session.getAttribute("patient");

        // Vérification que l'utilisateur est bien un patient connecté
        if (utilisateur == null || !"patient".equals(utilisateur.getRole()) || patient == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_hospitaliere", "root", "1234")) {
            PatientDossierDAO dao = new PatientDossierDAO(con);
            request.setAttribute("allergies", dao.getAllergies(patient.getId()));
            request.setAttribute("antecedants", dao.getAntecedants(patient.getId()));
            request.setAttribute("diagnostics", dao.getDiagnostics(patient.getId()));
            request.setAttribute("traitements", dao.getTraitements(patient.getId()));
            request.getRequestDispatcher("dossier_medical.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}