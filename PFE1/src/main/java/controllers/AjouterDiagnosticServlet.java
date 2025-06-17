package controllers;

import java.io.IOException;

import dao.DiagnosticDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Diagnostic;
import model.Medecin;

@WebServlet("/AjouterDiagnostic")
public class AjouterDiagnosticServlet extends HttpServlet {
    // ... init() et le DAO ...

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        // Sécurité
        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect("login.jsp?error=sessionExpiree");
            return;
        }

        try {
            // Récupérer les données du formulaire
            String patientHospi = request.getParameter("patientHospi");
            String contenu = request.getParameter("contenu");
            String dateString = request.getParameter("dateDiagnostic");
            
            // On sépare la valeur de l'option en deux IDs
            String[] ids = patientHospi.split(";");
            int idHospitalisation = Integer.parseInt(ids[0]);
            int idPatient = Integer.parseInt(ids[1]);
            
            // Récupérer l'ID du médecin depuis la session
            Medecin medecin = (Medecin) session.getAttribute("medecin");
            int idMedecin = medecin.getId();
            
            // Créer l'objet Diagnostic
            Diagnostic nouveauDiagnostic = new Diagnostic();
            nouveauDiagnostic.setIdPatient(idPatient);
            nouveauDiagnostic.setIdMedecin(idMedecin);
            nouveauDiagnostic.setIdHospitalisation(idHospitalisation);
            nouveauDiagnostic.setContenu(contenu);
            nouveauDiagnostic.setDate(java.sql.Date.valueOf(dateString));

            // Enregistrer via le DAO
            DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
            boolean success = diagnosticDAO.ajouterDiagnostic(nouveauDiagnostic);

            if (success) {
                // Rediriger vers le tableau de bord avec un message de succès
                response.sendRedirect("medecin_dashboard.jsp?status=diagSuccess");
            } else {
                // Gérer l'échec
                response.sendRedirect("PrepareDiagnosticServlet?status=diagError");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PrepareDiagnosticServlet?status=diagError");
        }
    }
}