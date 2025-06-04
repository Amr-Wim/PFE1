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
	    
	 // Dans PatientDAO.java
	 // Dans PatientDAO.java
	    public List<Patient> getAllActivePatients() throws SQLException {
	        List<Patient> patients = new ArrayList<>();
	        // CORRECTION: Joindre utilisateur (alias u) avec patient (alias p)
	        // et sélectionner p.date_naissance
	        String sql = "SELECT u.id, u.nom, u.prenom, u.cin, " + // Champs de la table utilisateur
	                     "p.date_naissance, p.adresse, p.Taille, p.Poids, p.Groupe_Sanguin, p.Assurance_Medicale, p.Numero_Assurance " + // Champs de la table patient
	                     "FROM utilisateur u " +
	                     "JOIN patient p ON u.id = p.id " + // La condition de jointure est cruciale: u.id (PK de utilisateur) = p.id (PK de patient ET FK vers utilisateur.id)
	                     "WHERE u.role = 'patient' AND u.Statut = 'Actif' " +
	                     "ORDER BY u.nom, u.prenom";

	        System.out.println("Exécution de la requête pour getAllActivePatients: " + sql); // Pour vérifier la requête

	        try (Connection conn = Database.getConnection(); // Ta classe de connexion
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            int count = 0;
	            while (rs.next()) {
	                count++;
	                Patient patient = new Patient(); // Assumant que ton modèle Patient a les setters correspondants

	                // Champs de l'utilisateur
	                patient.setId(rs.getInt("id")); // L'ID vient de u.id (qui est le même que p.id)
	                patient.setNom(rs.getString("nom"));
	                patient.setPrenom(rs.getString("prenom"));
	                patient.setCin(rs.getString("cin"));
	                // Note: la table utilisateur a aussi un Date_Naissance. Décide lequel tu veux utiliser.
	                // Si celui de la table 'patient' est plus spécifique/correct, utilise p.date_naissance.
	                // Si tu n'as pas mis la date de naissance de l'utilisateur dans la table 'patient',
	                // tu peux aussi la prendre de u.Date_Naissance (de la table utilisateur).
	                // Pour l'instant, on prend celui de la table patient:
	                patient.setDateNaissance(rs.getDate("date_naissance")); // Vient de p.date_naissance

	                // Champs spécifiques de la table patient (si ton modèle Patient les a)
	                patient.setAdresse(rs.getString("adresse"));
	                // Vérifie que ton modèle Patient a les setters pour ces champs:
	                // patient.setTaille(rs.getInt("Taille"));
	                // patient.setPoids(rs.getBigDecimal("Poids")); // Pour decimal(5,2)
	                // patient.setGroupeSanguin(rs.getString("Groupe_Sanguin"));
	                // patient.setAssuranceMedicale(rs.getString("Assurance_Medicale"));
	                // patient.setNumeroAssurance(rs.getString("Numero_Assurance"));

	                patients.add(patient);
	            }
	            System.out.println("Nombre de patients trouvés par DAO: " + count);
	        }
	        return patients;
	    }
	    
	 // Dans PatientDAO.java

	 // Méthode utilitaire pour mapper un ResultSet à un objet Patient
	 // Assure-toi que cette méthode (ou une similaire) est utilisée par getPatientById
	 // et qu'elle sette bien tous les champs, y compris le sexe.
	 private Patient mapFullPatient(ResultSet rs) throws SQLException {
	     Patient patient = new Patient(); // Supposant que Patient hérite d'Utilisateur

	     // Champs de Utilisateur (super-classe)
	     patient.setId(rs.getInt("u_id")); // Utiliser les alias pour éviter ambiguïté
	     patient.setNom(rs.getString("u_nom"));
	     patient.setPrenom(rs.getString("u_prenom"));
	     patient.setEmail(rs.getString("u_email"));
	     patient.setSexe(rs.getString("u_sexe"));       // <--- SETTER LE SEXE
	     patient.setLogin(rs.getString("u_login"));     // Optionnel, si besoin
	     patient.setRole(rs.getString("u_role"));       // Optionnel, si besoin
	     // Tu peux aussi récupérer u.Date_Naissance, u.Adresse, u.cin si ce sont les sources primaires
	     // et que la table 'patient' ne fait que les compléter ou les spécialiser.

	     // Champs spécifiques de la table Patient
	     patient.setDateNaissance(rs.getDate("p_date_naissance")); // Vient de la table patient
	     patient.setAdresse(rs.getString("p_adresse"));             // Vient de la table patient
	     // Si Patient n'hérite pas d'Utilisateur, et que tu veux quand même CIN etc.
	     // if (patient.getCin() == null) patient.setCin(rs.getString("u_cin"));

	     // Ajouter les autres champs de la table 'patient' si ton modèle Patient les a
	     // et s'ils sont sélectionnés dans la requête
	     if (rs.getObject("p_taille") != null) patient.setTaille(rs.getInt("p_taille"));
	     if (rs.getObject("p_poids") != null) patient.setPoids(rs.getDouble("p_poids")); // Ou rs.getBigDecimal si le modèle utilise BigDecimal
	     patient.setGroupeSanguin(rs.getString("p_groupe_sanguin"));
	     patient.setAssuranceMedicale(rs.getString("p_assurance_medicale"));
	     patient.setNumeroAssurance(rs.getString("p_numero_assurance"));

	     return patient;
	 }


	 public Patient getPatientById(int id) throws SQLException {
	     // CORRECTION: Sélectionner u.sexe et les autres champs nécessaires de 'utilisateur' et 'patient'
	     // Utiliser des alias pour chaque colonne pour éviter les ambiguïtés si les noms sont les mêmes
	     String sql = "SELECT u.id as u_id, u.nom as u_nom, u.prenom as u_prenom, u.email as u_email, u.sexe as u_sexe, " +
	                  "u.login as u_login, u.role as u_role, u.cin as u_cin, u.Date_Naissance as u_date_naissance_utilisateur, u.Adresse as u_adresse_utilisateur, " + // Champs de utilisateur
	                  "p.date_naissance as p_date_naissance, p.adresse as p_adresse, p.Taille as p_taille, p.Poids as p_poids, " +
	                  "p.Groupe_Sanguin as p_groupe_sanguin, p.Assurance_Medicale as p_assurance_medicale, p.Numero_Assurance as p_numero_assurance " + // Champs de patient
	                  "FROM utilisateur u " +
	                  "JOIN patient p ON u.id = p.id " + // Jointure sur l'ID commun
	                  "WHERE u.id = ? AND u.role = 'patient'"; // Filtrer par ID utilisateur et rôle

	     Patient patient = null;
	     System.out.println("PatientDAO.getPatientById - Exécution SQL: " + sql + " avec ID: " + id); // Log

	     try (Connection conn = Database.getConnection(); // Ta classe de connexion
	          PreparedStatement stmt = conn.prepareStatement(sql)) {

	         stmt.setInt(1, id);
	         try (ResultSet rs = stmt.executeQuery()) {
	             if (rs.next()) {
	                 // Utiliser la méthode de mapping pour peupler l'objet
	                 patient = mapFullPatient(rs);
	                 System.out.println("PatientDAO.getPatientById - Patient trouvé: " + patient.getNom() + ", Sexe: " + patient.getSexe()); // Log
	             } else {
	                 System.out.println("PatientDAO.getPatientById - Aucun patient trouvé pour ID: " + id); // Log
	             }
	         }
	     }
	     return patient;
	 }
	}
