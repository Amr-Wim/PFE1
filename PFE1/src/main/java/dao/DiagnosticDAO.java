package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Diagnostic;
import util.Database;

public class DiagnosticDAO {

    // Méthode existante (ajustée légèrement pour le nom de la colonne)
    public List<Diagnostic> getDiagnosticsByPatientId(int patientId) throws SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        // Ajout des nouveaux champs à la requête
        String sql = "SELECT id, contenu, date_diagnostic, id_medecin, id_hospitalisation, id_patient FROM diagnostic WHERE id_patient = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Diagnostic diagnostic = new Diagnostic();
                diagnostic.setId(rs.getInt("id"));
                diagnostic.setContenu(rs.getString("contenu"));
                diagnostic.setDate(rs.getDate("date_diagnostic")); // Utiliser le nom de la colonne DB
                diagnostic.setIdPatient(rs.getInt("id_patient"));
                diagnostic.setIdMedecin(rs.getInt("id_medecin"));
                diagnostic.setIdHospitalisation(rs.getInt("id_hospitalisation")); // Peut renvoyer 0 si NULL en DB
                diagnostics.add(diagnostic);
            }
        }
        return diagnostics;
    }

    // NOUVELLE MÉTHODE : Ajouter un diagnostic
    public boolean ajouterDiagnostic(Diagnostic diagnostic) {
        String sql = "INSERT INTO diagnostic (id_patient, id_medecin, id_hospitalisation, contenu, date_diagnostic) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 1. id_patient
            stmt.setInt(1, diagnostic.getIdPatient());
            
            // 2. id_medecin
            stmt.setInt(2, diagnostic.getIdMedecin());

            // 3. id_hospitalisation (peut être null)
            if (diagnostic.getIdHospitalisation() != null) {
                stmt.setInt(3, diagnostic.getIdHospitalisation());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            
            // 4. contenu
            stmt.setString(4, diagnostic.getContenu());
            
            // 5. date_diagnostic (Utilise java.sql.Date comme dans votre modèle)
            stmt.setDate(5, diagnostic.getDate());

            // Exécution de la requête
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'insertion a réussi

        } catch (SQLException e) {
            e.printStackTrace(); // Pour le débogage. En production, utilisez un système de logs.
            return false; // Retourne false en cas d'erreur
        }
    }
}