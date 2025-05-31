package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Traitement;

import util.Database;

public class TraitementDAO {
    public List<Traitement> getTraitementsByPatientId(int patientId) throws SQLException {
        List<Traitement> traitements = new ArrayList<>();
        String sql = "SELECT id, contenu, duree, date_enregistrement FROM traitement WHERE id_patient = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Traitement traitement = new Traitement();
                traitement.setId(rs.getInt("id"));
                traitement.setContenu(rs.getString("contenu"));
                traitement.setDuree(rs.getString("duree"));
                traitement.setDate_enregistrement(rs.getDate("date_enregistrement"));
                traitements.add(traitement);
            }
        }
        return traitements;
    }
}