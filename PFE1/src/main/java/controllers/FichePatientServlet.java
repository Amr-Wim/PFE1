package controllers;

import dao.PatientDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import model.Patient;

@WebServlet("/patient/fiche")
public class FichePatientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Les infos sont déjà en session, on forward vers la JSP
        request.getRequestDispatcher("/WEB-INF/views/fiche_patient.jsp").forward(request, response);
    }
}