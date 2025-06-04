package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Lit;
import util.Database; // Assure-toi que c'est ta classe de connexion

public class LitDAO {

    public Lit trouverLitDisponible(int serviceId, String sexePatient, int agePatient) throws SQLException {
        String sql = "SELECT l.id, l.chambre_id, l.sexe_autorise, l.age_min, l.age_max " +
                     "FROM lit l " +
                     "JOIN chambre c ON l.chambre_id = c.id " +
                     "WHERE c.id_service = ? AND l.est_occupe = FALSE " +
                     "AND (l.sexe_autorise = 'Mixte' OR l.sexe_autorise = ?) " +
                     "AND ? BETWEEN l.age_min AND l.age_max " +
                     "ORDER BY l.id ASC " + // Pour la prédictibilité
                     "LIMIT 1";
        System.out.println("LitDAO.trouverLitDisponible - SQL: " + sql + " Params: " + serviceId + ", " + sexePatient + ", " + agePatient);

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.setString(2, sexePatient); // Doit correspondre à l'enum de la BDD: 'Masculin', 'Féminin'
            stmt.setInt(3, agePatient);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Lit lit = new Lit();
                    lit.setId(rs.getInt("id"));
                    lit.setChambreId(rs.getInt("chambre_id"));
                    lit.setSexeAutorise(rs.getString("sexe_autorise"));
                    lit.setAgeMin(rs.getInt("age_min"));
                    lit.setAgeMax(rs.getInt("age_max"));
                    lit.setOccupe(false); // On le sait car on a filtré sur est_occupe = FALSE
                    System.out.println("LitDAO.trouverLitDisponible - Lit trouvé ID: " + lit.getId());
                    return lit;
                }
            }
        }
        System.out.println("LitDAO.trouverLitDisponible - Aucun lit disponible trouvé.");
        return null;
    }

    public boolean attribuerLit(int litId) throws SQLException {
        String sql = "UPDATE lit SET est_occupe = TRUE WHERE id = ? AND est_occupe = FALSE";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, litId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                 System.err.println("LitDAO.attribuerLit: Lit ID " + litId + " non trouvé ou déjà occupé.");
            }
            return affectedRows > 0;
        }
    }

    public boolean libererLit(int litId) throws SQLException {
        String sql = "UPDATE lit SET est_occupe = FALSE WHERE id = ? AND est_occupe = TRUE";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, litId);
             int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                 System.err.println("LitDAO.libererLit: Lit ID " + litId + " non trouvé ou déjà libre.");
            }
            return affectedRows > 0;
        }
    }
    
    // Méthode getLitsByChambre si tu en as besoin ailleurs
    public List<Lit> getLitsByChambre(int chambreId) throws SQLException {
        List<Lit> lits = new ArrayList<>();
        String sql = "SELECT id, chambre_id, sexe_autorise, age_min, age_max, est_occupe FROM lit WHERE chambre_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chambreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lit lit = new Lit();
                    lit.setId(rs.getInt("id"));
                    lit.setChambreId(rs.getInt("chambre_id"));
                    lit.setSexeAutorise(rs.getString("sexe_autorise"));
                    lit.setAgeMin(rs.getInt("age_min"));
                    lit.setAgeMax(rs.getInt("age_max"));
                    lit.setOccupe(rs.getBoolean("est_occupe"));
                    lits.add(lit);
                }
            }
        }
        return lits;
    }
}