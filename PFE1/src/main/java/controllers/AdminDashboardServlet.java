package controllers; // Ou un package admin

import dao.StatistiquesDAO; // Un nouveau DAO pour les statistiques

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminDashboard") // URL pour accéder au tableau de bord
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StatistiquesDAO statistiquesDAO;

    @Override
    public void init() throws ServletException {
        statistiquesDAO = new StatistiquesDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification de session/rôle pour l'admin
        HttpSession session = request.getSession(false);
        // if (session == null || !isAdmin(session.getAttribute("utilisateur"))) {
        //     response.sendRedirect(request.getContextPath() + "/login.jsp");
        //     return;
        // }

        try {
        	
            int occupiedLits = statistiquesDAO.getNombreLitsOccupes();
            int totalLits = statistiquesDAO.getNombreTotalLits();
            int freeLits = totalLits - occupiedLits;
            
            System.out.println("AdminDashboardServlet: Lits Occupés = " + occupiedLits);
            System.out.println("AdminDashboardServlet: Lits Libres = " + freeLits);
            System.out.println("AdminDashboardServlet: Total Lits = " + totalLits);

            request.setAttribute("occupiedLits", occupiedLits);
            request.setAttribute("freeLits", freeLits);
            request.setAttribute("totalLits", totalLits); // Utile pour d'autres affichages

            // Tu pourrais aussi récupérer d'autres statistiques ici
            // int nombrePatientsActifs = statistiquesDAO.getNombrePatientsActifs();
            // request.setAttribute("nombrePatientsActifs", nombrePatientsActifs);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des statistiques: " + e.getMessage());
            // Mettre des valeurs par défaut pour éviter les erreurs JS si les attributs manquent
            request.setAttribute("occupiedLits", 0);
            request.setAttribute("freeLits", 0);
            request.setAttribute("totalLits", 0);
        }

        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }
}