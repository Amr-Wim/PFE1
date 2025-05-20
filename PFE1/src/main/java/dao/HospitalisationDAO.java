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
	                "LEFT JOIN medecin m ON h.id_medecin = m.id " +
	                "LEFT JOIN utilisateur u ON m.id = u.id " +
	                "WHERE h.id_patient = ? " +
	                "ORDER BY h.id_hospitalisation DESC LIMIT 1";
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, patientId);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            Hospitalisation h = new Hospitalisation();
	            h.setId(rs.getInt("id_hospitalisation")); // Correction ici
	            h.setIdPatient(rs.getInt("id_patient"));
	            h.setNomHopital(rs.getString("nom_hopital"));
	            h.setService(rs.getString("service"));
	            h.setDuree(rs.getString("duree"));
	            h.setEtat(rs.getString("etat"));
	            h.setChambre(rs.getString("chambre"));
	            h.setIdMedecin(rs.getInt("id_medecin"));
	            h.setMotif(rs.getString("motif"));
	            
	            // Gestion de la date (même si null)
	            java.sql.Date dateAdmission = rs.getDate("date_admission");
	            h.setDateAdmission(dateAdmission);
	            
	            // Ajout des infos médecin
	            Medecin medecin = new Medecin();
	            medecin.setId(rs.getInt("id_medecin"));
	            medecin.setNom(rs.getString("nom"));
	            medecin.setPrenom(rs.getString("prenom"));
	            h.setMedecin(medecin);
	            
	            return h;
	        }
	    }
	    return null;
	}
}