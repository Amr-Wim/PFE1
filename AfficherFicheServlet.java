package controllers;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AfficherFicheServlet1")
public class AfficherFicheServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println(">>>> Servlet appelée !");
        String id = request.getParameter("idPatient");
        System.out.println("Paramètre reçu : idPatient = " + id);

        boolean rechercheEffectuee = false;

        if (id != null && !id.trim().isEmpty()) {
            rechercheEffectuee = true;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_hospitaliere", "root", "");

                String sql = "SELECT u.nom, u.prenom, p.date_naissance, d.contenu AS diagnostic " +
                             "FROM utilisateur u " +
                             "JOIN patient p ON u.id = p.id " +
                             "LEFT JOIN diagnostic d ON p.id = d.id_patient " +
                             "WHERE u.id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(id));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println(">> Données trouvées !");
                    request.setAttribute("idPatient", id);
                    request.setAttribute("nom", rs.getString("nom"));
                    request.setAttribute("prenom", rs.getString("prenom"));
                    request.setAttribute("dateNaissance", rs.getDate("date_naissance").toString());
                    request.setAttribute("diagnostic", rs.getString("diagnostic"));
                } else {
                    System.out.println(">> Aucun patient trouvé avec l’ID " + id);
                }

                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("rechercheEffectuee", rechercheEffectuee);
        request.getRequestDispatcher("FichePatient.jsp").forward(request, response);
    }
}
