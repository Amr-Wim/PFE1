package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Hospitalisation;
import model.Medecin;
import util.Database;

public class HospitalisationDAO {
	public Hospitalisation getCurrentByPatientId(int patientId) throws SQLException {
	    String sql = "SELECT h.*, u.nom, u.prenom " +
	                "FROM hospitalisation h " +
	                "LEFT JOIN medecin m ON h.ID_Medecin = m.ID_Medecin " + // Correction ici
	                "LEFT JOIN utilisateur u ON m.ID_Medecin = u.id " + // Correction ici
	                "WHERE h.ID_Patient = ? AND h.etat = 'En cours' " +
	                "ORDER BY h.ID_Hospitalisation DESC LIMIT 1";
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, patientId);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            Hospitalisation h = new Hospitalisation();
	            h.setId(rs.getInt("ID_Hospitalisation"));
	            h.setIdPatient(rs.getInt("ID_Patient"));
	            h.setNomHopital(rs.getString("nom_hopital"));
	            h.setService(rs.getString("service"));
	            h.setDuree(rs.getString("duree"));
	            h.setEtat(rs.getString("etat"));
	            h.setChambre(rs.getString("chambre"));
	            h.setIdMedecin(rs.getInt("ID_Medecin"));
	            h.setMotif(rs.getString("motif"));
	            
	            java.sql.Date dateAdmission = rs.getDate("date_admission");
	            h.setDateAdmission(dateAdmission);
	            
	            if (rs.getInt("ID_Medecin") > 0) {
	                Medecin medecin = new Medecin();
	                medecin.setId(rs.getInt("ID_Medecin"));
	                medecin.setNom(rs.getString("nom"));
	                medecin.setPrenom(rs.getString("prenom"));
	                h.setMedecin(medecin);
	            }
	            
	            return h;
	        }
	    }
	    return null;
	}
}