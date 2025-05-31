package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Patient;
import model.Utilisateur;
import util.Database;

public class PatientDAO {
	
	    
	    public List<Patient> getAllPatients() throws SQLException {
	        List<Patient> patients = new ArrayList<>();
	        String sql = "SELECT u.*, p.date_naissance, p.adresse, p.taille, p.poids, " +
	                     "p.groupe_sanguin, p.assurance_medicale, p.numero_assurance " +
	                     "FROM utilisateur u " +
	                     "JOIN patient p ON u.id = p.id " +
	                     "WHERE u.role = 'patient' " +
	                     "ORDER BY u.nom, u.prenom";
	        
	        try (Connection conn = Database.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
	            
	            while (rs.next()) {
	                Patient patient = new Patient();
	                // Champs de Utilisateur
	                patient.setId(rs.getInt("id"));
	                patient.setNom(rs.getString("nom"));
	                patient.setPrenom(rs.getString("prenom"));
	                patient.setCin(rs.getString("cin"));
	                patient.setEmail(rs.getString("email"));
	                // Champs spécifiques à Patient
	                patient.setDateNaissance(rs.getDate("date_naissance"));
	                patient.setAdresse(rs.getString("adresse"));
	                patient.setTaille(rs.getInt("taille"));
	                patient.setPoids(rs.getDouble("poids"));
	                patient.setGroupeSanguin(rs.getString("groupe_sanguin"));
	                patient.setAssuranceMedicale(rs.getString("assurance_medicale"));
	                patient.setNumeroAssurance(rs.getString("numero_assurance"));
	                
	                patients.add(patient);
	            }
	        }
	        return patients;
	    }
	
	
	    // Récupère un patient par son ID
	    public Patient getPatientById(int id) throws SQLException {
	        String sql = "SELECT u.id, u.nom, u.prenom, u.email, p.date_naissance, p.adresse " +
	                     "FROM utilisateur u JOIN patient p ON u.id = p.id " +
	                     "WHERE u.id = ? AND u.role = 'patient'";
	        
	        try (Connection conn = Database.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            stmt.setInt(1, id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return mapPatient(rs);
	                }
	            }
	        }
	        return null;
	    }
	    
	    // Méthode utilitaire pour mapper un ResultSet à un objet Patient
	    private Patient mapPatient(ResultSet rs) throws SQLException {
	        Patient patient = new Patient();
	        patient.setId(rs.getInt("id"));
	        patient.setNom(rs.getString("nom"));
	        patient.setPrenom(rs.getString("prenom"));
	        patient.setEmail(rs.getString("email"));
	        patient.setDateNaissance(rs.getDate("date_naissance"));
	        patient.setAdresse(rs.getString("adresse"));
	        return patient;
	    }
	    
	    public Patient getPatientByUtilisateur(Utilisateur user) throws SQLException {
	        if (user == null || !"patient".equals(user.getRole())) {
	            return null;
	        }
	        return getPatientById(user.getId());
	    }
	}
