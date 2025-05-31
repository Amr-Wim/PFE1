package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Allergie;

import util.Database;

public class AllergieDAO {
    public List<Allergie> getAllergiesByPatientId(int patientId) throws SQLException {
        List<Allergie> allergies = new ArrayList<>();
        String sql = "SELECT id, allergie, niveau_gravite, date_detection FROM allergie WHERE id_patient = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Allergie allergie = new Allergie();
                allergie.setId(rs.getInt("id"));
                allergie.setAllergie(rs.getString("allergie"));
                allergie.setNiveauGravite(rs.getString("niveau_gravite"));
                allergie.setDateDetection(rs.getDate("date_detection"));
                allergies.add(allergie);
            }
        }
        return allergies;
    }
}