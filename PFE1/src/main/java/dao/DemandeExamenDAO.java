package dao;

import model.DemandeExamen;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DemandeExamenDAO {

	public boolean creerDemande(DemandeExamen demande) throws SQLException {
	    String sql = "INSERT INTO DemandeExamen (id_patient, id_medecin_prescripteur, id_examen, date_demande, " +
	                 "statut_demande, notes_medecin, date_estimee_resultats_prets, " +
	                 "date_realisation_effective, resultats_texte, " +
	                 "resultats_fichier_url, date_resultats_disponibles) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // 11 paramètres maintenant
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, demande.getIdPatient());
	        stmt.setInt(2, demande.getIdMedecinPrescripteur());
	        stmt.setInt(3, demande.getIdExamen());
	        stmt.setDate(4, new java.sql.Date(demande.getDateDemande().getTime()));
	        stmt.setString(5, demande.getStatutDemande());

	        // notes_medecin
	        if (demande.getNotesMedecin() != null && !demande.getNotesMedecin().isEmpty()) {
	            stmt.setString(6, demande.getNotesMedecin());
	        } else {
	            stmt.setNull(6, Types.VARCHAR);
	        }

	        // date_estimee_resultats_prets
	        if (demande.getDateEstimeeResultatsPrets() != null) {
	            stmt.setDate(7, new java.sql.Date(demande.getDateEstimeeResultatsPrets().getTime()));
	        } else {
	            stmt.setNull(7, Types.DATE);
	        }

	        // date_realisation_effective
	        if (demande.getDateRealisationEffective() != null) {
	            stmt.setTimestamp(8, new java.sql.Timestamp(demande.getDateRealisationEffective().getTime()));
	        } else {
	            stmt.setNull(8, Types.TIMESTAMP);
	        }

	        // resultats_texte
	        if (demande.getResultatsTexte() != null) {
	            stmt.setString(9, demande.getResultatsTexte()); // Changé de 10 à 9
	        } else {
	            stmt.setNull(9, Types.VARCHAR);
	        }

	        // resultats_fichier_url
	        if (demande.getResultatsFichierUrl() != null) { 
	            stmt.setString(10, demande.getResultatsFichierUrl()); // Changé de 11 à 10
	        } else {
	            stmt.setNull(10, Types.VARCHAR);
	        }

	        // date_resultats_disponibles
	        if (demande.getDateResultatsDisponibles() != null) {
	            stmt.setTimestamp(11, new java.sql.Timestamp(demande.getDateResultatsDisponibles().getTime())); // Changé de 12 à 11
	        } else {
	            stmt.setNull(11, Types.TIMESTAMP);
	        }
	        
	        return stmt.executeUpdate() > 0;
	    }
	    }
}