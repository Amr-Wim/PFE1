package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Medecin;
import util.Database;

public class MedecinDAO {

    // Récupérer un médecin par son ID
    public Medecin getById(int id) throws SQLException {
        String sql = "SELECT u.*, m.specialite FROM utilisateur u JOIN medecin m ON u.id = m.id WHERE u.id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapMedecin(rs);
            }
        }
        return null;
    }

    // Récupérer tous les médecins
    public List<Medecin> getAll() throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT u.*, m.specialite FROM utilisateur u JOIN medecin m ON u.id = m.id";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }
        }
        return medecins;
    }

    // Récupérer les médecins par spécialité
    public List<Medecin> getBySpecialite(String specialite) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT u.*, m.specialite FROM utilisateur u JOIN medecin m ON u.id = m.id WHERE m.specialite = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, specialite);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }
        }
        return medecins;
    }

    // Ajouter un nouveau médecin
    public boolean add(Medecin medecin) throws SQLException {
        String sql = "INSERT INTO medecin (id, specialite) VALUES (?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, medecin.getId());
            stmt.setString(2, medecin.getSpecialite());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Mettre à jour un médecin
    public boolean update(Medecin medecin) throws SQLException {
        String sql = "UPDATE medecin SET specialite = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, medecin.getSpecialite());
            stmt.setInt(2, medecin.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Supprimer un médecin
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM medecin WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Méthode utilitaire pour mapper un ResultSet à un objet Medecin
    private Medecin mapMedecin(ResultSet rs) throws SQLException {
        Medecin medecin = new Medecin();
        medecin.setId(rs.getInt("id"));
        medecin.setNom(rs.getString("nom"));
        medecin.setPrenom(rs.getString("prenom"));
        medecin.setEmail(rs.getString("email"));
        medecin.setLogin(rs.getString("login"));
        medecin.setMotDePasse(rs.getString("mot_de_passe"));
        medecin.setRole(rs.getString("role"));
        medecin.setSpecialite(rs.getString("specialite"));
        return medecin;
    }
}