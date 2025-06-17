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
        System.out.println(">> Paramètre idPatient brut : " + id);

        int idInt = -1;
        try {
            idInt = Integer.parseInt(id.trim());
            System.out.println(">> idPatient converti en int : " + idInt);
        } catch (NumberFormatException e) {
            System.out.println(">> ERREUR de conversion de idPatient !");
        }

        boolean rechercheEffectuee = false;

        if (id != null && !id.trim().isEmpty()) {
            rechercheEffectuee = true;
            System.out.println(">> Début de la requête SQL...");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/gestion_hospitaliere", "root", "1234");
                System.out.println(">> Connexion à la base réussie.");

                String sql = "SELECT u.nom, u.prenom, p.date_naissance, d.contenu AS diagnostic " +
                             "FROM utilisateur u " +
                             "JOIN patient p ON u.id = p.id " +
                             "LEFT JOIN diagnostic d ON p.id = d.id_patient " +
                             "WHERE u.id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idInt);
                ResultSet rs = ps.executeQuery();

                System.out.println(">> Requête SQL exécutée.");
                if (rs.next()) {
                    System.out.println(">> Données patient trouvées !");
                    System.out.println(">> nom = " + rs.getString("nom"));
                    System.out.println(">> prénom = " + rs.getString("prenom"));
                    System.out.println(">> date_naissance = " + rs.getDate("date_naissance"));
                    System.out.println(">> diagnostic = " + rs.getString("diagnostic"));

                    request.setAttribute("idPatient", id);
                    request.setAttribute("nom", rs.getString("nom"));
                    request.setAttribute("prenom", rs.getString("prenom"));
                    request.setAttribute("dateNaissance", rs.getDate("date_naissance").toString());
                    request.setAttribute("diagnostic", rs.getString("diagnostic"));
                } else {
                    System.out.println(">> Aucun patient trouvé avec l’ID " + id);
                }
                conn.close();
                System.out.println(">> Connexion fermée.");

            } catch (Exception e) {
                System.out.println(">> Exception attrapée : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println(">> Aucune recherche effectuée : idPatient est vide ou null.");
        }

        request.setAttribute("rechercheEffectuee", rechercheEffectuee);
        System.out.println(">> Redirection vers FichePatient.jsp...");
        request.getRequestDispatcher("FichePatient.jsp").forward(request, response);
    }
}
