package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Hospitalisation;
import util.Database;

public class HospitalisationDAO {
	public Hospitalisation getCurrentByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM hospitalisation WHERE id_patient = ? ORDER BY date_admission DESC LIMIT 1";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Hospitalisation h = new Hospitalisation();
                h.setId(rs.getInt("id"));
                h.setIdPatient(rs.getInt("id_patient"));
                h.setNomHopital(rs.getString("nom_hopital"));
                h.setService(rs.getString("service"));
                h.setDuree(rs.getString("duree"));
                h.setEtat(rs.getString("etat"));
                h.setChambre(rs.getString("chambre"));
                h.setIdMedecin(rs.getInt("id_medecin"));
                h.setMotif(rs.getString("motif"));
                h.setDateAdmission(rs.getDate("date_admission"));
                return h;
            }
        }
        return null;
    }

}
