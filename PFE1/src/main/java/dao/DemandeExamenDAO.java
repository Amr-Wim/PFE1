package dao;

import model.DemandeExamen;
import model.Examen;
import model.TypeExamen;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; // Pour Types.VARCHAR, Types.DATE, etc.
import java.util.ArrayList;
import java.util.List;

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
	
	
	
	public List<DemandeExamen> getDemandesParHospitalisation(int hospitalisationId) throws SQLException {
        List<DemandeExamen> demandes = new ArrayList<>();
        // Jointure avec la table examen pour obtenir nom_examen
        // Assure-toi que les noms de table et de colonnes sont corrects (demandeexamen, examen)
        String sql = "SELECT de.*, e.nom_examen " +
                     "FROM demandeexamen de " +
                     "JOIN examen e ON de.id_examen = e.id " +
                     "WHERE de.ID_Hospitalisation = ? ORDER BY de.date_demande ASC";

        System.out.println("DemandeExamenDAO.getDemandesParHospitalisation - SQL: " + sql + " pour HospID: " + hospitalisationId);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hospitalisationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DemandeExamen de = new DemandeExamen();
                    de.setId(rs.getInt("id"));
                    de.setIdPatient(rs.getInt("id_patient"));
                    de.setIdMedecinPrescripteur(rs.getInt("id_medecin_prescripteur"));
                    de.setIdExamen(rs.getInt("id_examen"));
                    de.setDateDemande(rs.getDate("date_demande"));
                    de.setStatutDemande(rs.getString("statut_demande"));
                    de.setNotesMedecin(rs.getString("notes_medecin"));
                    // ... mapper les autres champs de la table demandeexamen ...
                    de.setIdHospitalisation(rs.getInt("ID_Hospitalisation")); // Champ que tu as ajouté

                    // Créer et setter l'objet Examen associé
                    Examen ex = new Examen(); // Tu dois avoir un modèle model.Examen.java
                    ex.setId(rs.getInt("id_examen"));
                    ex.setNomExamen(rs.getString("nom_examen"));
                    de.setExamen(ex); // Tu dois avoir un setExamen(Examen ex) dans model.DemandeExamen.java

                    demandes.add(de);
                }
            }
        }
        System.out.println("DemandeExamenDAO.getDemandesParHospitalisation - " + demandes.size() + " demandes trouvées.");
        return demandes;
    }
     // Méthode pour lister les demandes par patient (utilisée dans LoginServlet)
 public List<DemandeExamen> getDemandesByPatientId(int patientId) throws SQLException {
     List<DemandeExamen> demandes = new ArrayList<>();
     String sql = "SELECT de.*, e.nom_examen " +
                  "FROM demandeexamen de " +
                  "JOIN examen e ON de.id_examen = e.id " +
                  "WHERE de.id_patient = ? ORDER BY de.date_demande DESC";
     try (Connection conn = Database.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setInt(1, patientId);
         try (ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                 DemandeExamen de = new DemandeExamen();
                 de.setId(rs.getInt("id"));
                 de.setIdPatient(rs.getInt("id_patient"));
                 de.setIdMedecinPrescripteur(rs.getInt("id_medecin_prescripteur"));
                 de.setIdExamen(rs.getInt("id_examen"));
                 de.setDateDemande(rs.getDate("date_demande"));
                 de.setStatutDemande(rs.getString("statut_demande"));
                 de.setNotesMedecin(rs.getString("notes_medecin"));
                 // ... autres champs ...
                 int idHosp = rs.getInt("ID_Hospitalisation");
                 if(!rs.wasNull()){
                     de.setIdHospitalisation(idHosp);
                 }

                 Examen ex = new Examen();
                 ex.setId(rs.getInt("id_examen"));
                 ex.setNomExamen(rs.getString("nom_examen"));
                 de.setExamen(ex);
                 demandes.add(de);
             }
         }
     }
     return demandes;
 }

}

