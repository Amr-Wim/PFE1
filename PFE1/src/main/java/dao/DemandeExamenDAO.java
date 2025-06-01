package dao;

import model.DemandeExamen;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types; // Pour Types.VARCHAR, Types.DATE, etc.

public class DemandeExamenDAO {

	public boolean creerDemande(DemandeExamen demande) throws SQLException {
	    // Assurez-vous que les noms de colonnes correspondent à votre table DemandeExamen
	    String sql = "INSERT INTO DemandeExamen (id_patient, id_medecin_prescripteur, id_examen, date_demande, " +
	                 "statut_demande, notes_medecin, date_estimee_resultats_prets, " +
	                 "date_realisation_effective, resultats_texte, " +
	                 "resultats_fichier_url, date_resultats_disponibles) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, demande.getIdPatient());
	        stmt.setInt(2, demande.getIdMedecinPrescripteur());
	        stmt.setInt(3, demande.getIdExamen());
            
            // date_demande (java.util.Date to java.sql.Date)
            if (demande.getDateDemande() != null) {
	            stmt.setDate(4, new java.sql.Date(demande.getDateDemande().getTime()));
            } else {
                stmt.setNull(4, Types.DATE); // Ou définissez une date par défaut si non nullable
            }

	        stmt.setString(5, demande.getStatutDemande());

	        if (demande.getNotesMedecin() != null && !demande.getNotesMedecin().isEmpty()) {
	            stmt.setString(6, demande.getNotesMedecin());
	        } else {
	            stmt.setNull(6, Types.VARCHAR);
	        }

	        if (demande.getDateEstimeeResultatsPrets() != null) {
	            stmt.setDate(7, new java.sql.Date(demande.getDateEstimeeResultatsPrets().getTime()));
	        } else {
	            stmt.setNull(7, Types.DATE);
	        }

	        if (demande.getDateRealisationEffective() != null) {
	            stmt.setTimestamp(8, new java.sql.Timestamp(demande.getDateRealisationEffective().getTime()));
	        } else {
	            stmt.setNull(8, Types.TIMESTAMP);
	        }

	        if (demande.getResultatsTexte() != null) {
	            stmt.setString(9, demande.getResultatsTexte()); 
	        } else {
	            stmt.setNull(9, Types.VARCHAR);
	        }

	        if (demande.getResultatsFichierUrl() != null) { 
	            stmt.setString(10, demande.getResultatsFichierUrl()); 
	        } else {
	            stmt.setNull(10, Types.VARCHAR);
	        }

	        if (demande.getDateResultatsDisponibles() != null) {
	            stmt.setTimestamp(11, new java.sql.Timestamp(demande.getDateResultatsDisponibles().getTime())); 
	        } else {
	            stmt.setNull(11, Types.TIMESTAMP);
	        }
	        
	        return stmt.executeUpdate() > 0;
	    }
	}
}