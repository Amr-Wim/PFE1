package controllers;

import dao.HospitalisationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Medecin;
import java.io.IOException;
import java.util.List;

@WebServlet("/PrepareDiagnosticServlet")
public class PrepareDiagnosticServlet extends HttpServlet {
    private HospitalisationDAO hospitalisationDAO;

    @Override
    public void init() {
        this.hospitalisationDAO = new HospitalisationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // On ne crée pas de nouvelle session

        // Sécurité : Vérifier que l'utilisateur est un médecin connecté
        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect("login.jsp?error=accesRefuse");
            return;
        }
        
        Medecin medecin = (Medecin) session.getAttribute("medecin");
        int idMedecin = medecin.getId();
        
        try {
            // ACTION CLÉ : Récupérer la liste des patients hospitalisés "En cours" par CE médecin
            List<Hospitalisation> patientsHospitalises = hospitalisationDAO.getPatientsActifsParMedecin(idMedecin);
            
            // On passe cette liste à la requête pour que la JSP puisse l'utiliser
            request.setAttribute("patientsHospitalises", patientsHospitalises);
            
            // On transfère vers la page JSP qui affichera le formulaire
            request.getRequestDispatcher("ajouterDiagnostic.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'erreur, on peut rediriger vers le tableau de bord avec un message
            request.setAttribute("errorMessage", "Erreur lors du chargement de la liste des patients.");
            request.getRequestDispatcher("medecin_dashboard.jsp").forward(request, response);
        }
    }
}