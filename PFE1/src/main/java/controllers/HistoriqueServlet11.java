package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/HistoriqueServlete")
public class HistoriqueServlet11 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cin = request.getParameter("cin");
        if (cin != null) {
            cin = cin.trim();  // enlève les espaces avant et après
        }

        System.out.println("Recherche patient avec CIN : '" + cin + "'");

        if (cin == null || cin.isEmpty()) {
            response.sendRedirect("historiqueMedicale.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_hospitaliere", "root", "1234");
            System.out.println("Exécution de la requête SQL avec CIN = " + cin);

            String patientQuery = "SELECT p.*, u.nom, u.prenom, u.cin " +
                                  "FROM patient p " +
                                  "JOIN utilisateur u ON p.id = u.id " +
                                  "WHERE u.cin = ?";
            pst = conn.prepareStatement(patientQuery);
            pst.setString(1, cin);
            rs = pst.executeQuery();

            if (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setCin(rs.getString("cin"));
                p.setAdresse(rs.getString("adresse"));
                p.setDateNaissance(rs.getDate("date_naissance"));
                p.setPoids(rs.getDouble("Poids"));
                p.setGroupeSanguin(rs.getString("Groupe_Sanguin"));
                request.setAttribute("patient", p);

                int idPatient = p.getId();

                // Allergies
                List<Allergie> allergies = new ArrayList<>();
                try (PreparedStatement pstAllergies = conn.prepareStatement("SELECT * FROM allergie WHERE id_patient = ?")) {
                    pstAllergies.setInt(1, idPatient);
                    try (ResultSet rsAllergies = pstAllergies.executeQuery()) {
                        while (rsAllergies.next()) {
                            Allergie a = new Allergie();
                            a.setAllergie(rsAllergies.getString("allergie"));
                            a.setNiveauGravite(rsAllergies.getString("niveau_gravite"));
                            a.setDateDetection(rsAllergies.getDate("date_detection"));
                            allergies.add(a);
                        }
                    }
                }
                request.setAttribute("allergies", allergies);

                // Antécédents
                List<Antecedant> antecedents = new ArrayList<>();
                try (PreparedStatement pstAntecedents = conn.prepareStatement("SELECT * FROM antecedant WHERE id_patient = ?")) {
                    pstAntecedents.setInt(1, idPatient);
                    try (ResultSet rsAntecedents = pstAntecedents.executeQuery()) {
                        while (rsAntecedents.next()) {
                            Antecedant ant = new Antecedant();
                            ant.setType(rsAntecedents.getString("type_antecedant"));
                            ant.setDescription(rsAntecedents.getString("description"));
                            ant.setDate(rsAntecedents.getDate("date_enregistrement"));
                            antecedents.add(ant);
                        }
                    }
                }
                request.setAttribute("antecedents", antecedents);

                // Diagnostics
                List<Diagnostic> diagnostics = new ArrayList<>();
                try (PreparedStatement pstDiagnostics = conn.prepareStatement("SELECT * FROM diagnostic WHERE id_patient = ?")) {
                    pstDiagnostics.setInt(1, idPatient);
                    try (ResultSet rsDiagnostics = pstDiagnostics.executeQuery()) {
                        while (rsDiagnostics.next()) {
                            Diagnostic d = new Diagnostic();
                            d.setContenu(rsDiagnostics.getString("contenu"));
                            d.setDate(rsDiagnostics.getDate("date_diagnostic"));
                            diagnostics.add(d);
                        }
                    }
                }
                request.setAttribute("diagnostics", diagnostics);

                // Traitements
                List<Traitement> traitements = new ArrayList<>();
                try (PreparedStatement pstTraitements = conn.prepareStatement("SELECT * FROM traitement WHERE id_patient = ?")) {
                    pstTraitements.setInt(1, idPatient);
                    try (ResultSet rsTraitements = pstTraitements.executeQuery()) {
                        while (rsTraitements.next()) {
                            Traitement t = new Traitement();
                            t.setContenu(rsTraitements.getString("contenu"));
                            t.setDuree(rsTraitements.getString("duree"));
                            t.setDate_enregistrement(rsTraitements.getDate("date_enregistrement"));
                            traitements.add(t);
                        }
                    }
                }
                request.setAttribute("traitements", traitements);

                // Vaccinations
                List<Vaccination> vaccinations = new ArrayList<>();
                try (PreparedStatement pstVaccinations = conn.prepareStatement("SELECT * FROM vaccinations WHERE ID_Patient = ?")) {
                    pstVaccinations.setInt(1, idPatient);
                    try (ResultSet rsVaccinations = pstVaccinations.executeQuery()) {
                        while (rsVaccinations.next()) {
                            Vaccination v = new Vaccination();
                            v.setNomVaccin(rsVaccinations.getString("nom_vaccin"));
                            v.setDateVaccination(rsVaccinations.getDate("date_vaccination"));
                            v.setReactionsEventuelles(rsVaccinations.getString("reactions_eventuelles"));
                            vaccinations.add(v);
                        }
                    }
                }
                request.setAttribute("vaccinations", vaccinations);

            } else {
                request.setAttribute("message", "Aucun patient trouvé avec le CIN " + cin);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Erreur lors de la recherche du patient.");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (pst != null) pst.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        request.getRequestDispatcher("historiqueMedicale.jsp").forward(request, response);
    }
}
