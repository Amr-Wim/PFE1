package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Antecedant;

import util.Database;

public class AntecedantDAO {
    public List<Antecedant> getAntecedantsByPatientId(int patientId) throws SQLException {
        List<Antecedant> antecedants = new ArrayList<>();
        String sql = "SELECT id, type_antecedant as type, description, date_enregistrement as date FROM antecedant WHERE id_patient = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Antecedant antecedant = new Antecedant();
                antecedant.setId(rs.getInt("id"));
                antecedant.setType(rs.getString("type"));
                antecedant.setDescription(rs.getString("description"));
                antecedant.setDate(rs.getDate("date"));
                antecedants.add(antecedant);
            }
        }
        return antecedants;
    }
}