package dao;

import model.Hospitalisation;
import util.Database; // Assure-toi que c'est ta classe de connexion

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalisationDAO {

    public boolean insertHospitalisation(Hospitalisation hosp) throws SQLException {
        // Supposant que chambre_id a été supprimé et lit_id ajouté à la table hospitalisation
        String sql = "INSERT INTO hospitalisation (ID_Patient, nom_hopital, service, duree, etat, ID_Medecin, motif, Diagnostic_Initial, Date_Sortie_Prevue, lit_id, date_admission) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE())"; // CURDATE() pour date_admission

        try (Connection conn = Database.getConnection(); // Utilise ta classe de connexion
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, hosp.getIdPatient());
            pstmt.setString(2, hosp.getNomHopital());
            pstmt.setString(3, hosp.getService());
            pstmt.setString(4, hosp.getDuree());
            pstmt.setString(5, hosp.getEtat());
            pstmt.setInt(6, hosp.getIdMedecin());
            pstmt.setString(7, hosp.getMotif());
            pstmt.setString(8, hosp.getDiagnosticInitial());

            if (hosp.getDateSortiePrevue() != null) {
                pstmt.setDate(9, hosp.getDateSortiePrevue()); // Doit être java.sql.Date
            } else {
                pstmt.setNull(9, java.sql.Types.DATE);
            }

            if (hosp.getLitId() != null && hosp.getLitId() > 0) {
                pstmt.setInt(10, hosp.getLitId());
            } else {
                // Si lit_id est NOT NULL dans la BDD, ceci causera une erreur, ce qui est bien.
                // Si lit_id peut être NULL, alors on peut permettre pstmt.setNull
                // Mais la logique d'attribution de lit doit s'assurer qu'un lit est trouvé.
                pstmt.setNull(10, java.sql.Types.INTEGER);
                // throw new SQLException("Tentative d'insertion d'hospitalisation sans lit assigné valide.");
            }
            // date_admission est gérée par CURDATE() dans la requête

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hosp.setId(generatedKeys.getInt(1)); // Mettre à jour l'ID de l'objet hosp
                    }
                }
                return true;
            }
            return false;
        }
    }

    public Hospitalisation getById(int hospitalisationId) throws SQLException {
        // Adapte cette requête si tu as enlevé chambre_id et ajouté lit_id
        String sql = "SELECT * FROM hospitalisation WHERE ID_Hospitalisation = ?";
        Hospitalisation hosp = null;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hospitalisationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = new Hospitalisation();
                    hosp.setId(rs.getInt("ID_Hospitalisation"));
                    hosp.setIdPatient(rs.getInt("ID_Patient"));
                    hosp.setNomHopital(rs.getString("nom_hopital"));
                    hosp.setService(rs.getString("service"));
                    hosp.setDuree(rs.getString("duree"));
                    hosp.setEtat(rs.getString("etat"));
                    hosp.setIdMedecin(rs.getInt("ID_Medecin"));
                    hosp.setMotif(rs.getString("motif"));
                    hosp.setDiagnosticInitial(rs.getString("Diagnostic_Initial"));
                    hosp.setDateAdmission(rs.getDate("date_admission"));
                    hosp.setDateSortiePrevue(rs.getDate("Date_Sortie_Prevue"));
                    hosp.setDateSortieReelle(rs.getDate("Date_Sortie_Reelle"));
                    // Gérer lit_id (il peut être null si la colonne est nullable)
                    int litId = rs.getInt("lit_id");
                    if (!rs.wasNull()) {
                        hosp.setLitId(litId);
                    }
                    // Si tu avais chambre_id, il faudrait le supprimer d'ici
                }
            }
        }
        return hosp;
    }

    public boolean updateHospitalisation(Hospitalisation hosp) throws SQLException {
        // Adapte cette requête pour inclure lit_id et enlever chambre_id si besoin
        String sql = "UPDATE hospitalisation SET ID_Patient = ?, nom_hopital = ?, service = ?, duree = ?, " +
                     "etat = ?, ID_Medecin = ?, motif = ?, Diagnostic_Initial = ?, Date_Sortie_Prevue = ?, " +
                     "Date_Sortie_Reelle = ?, lit_id = ? " +
                     "WHERE ID_Hospitalisation = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hosp.getIdPatient());
            pstmt.setString(2, hosp.getNomHopital());
            pstmt.setString(3, hosp.getService());
            pstmt.setString(4, hosp.getDuree());
            pstmt.setString(5, hosp.getEtat());
            pstmt.setInt(6, hosp.getIdMedecin());
            pstmt.setString(7, hosp.getMotif());
            pstmt.setString(8, hosp.getDiagnosticInitial());
            pstmt.setDate(9, hosp.getDateSortiePrevue());
            pstmt.setDate(10, hosp.getDateSortieReelle());
            if (hosp.getLitId() != null) {
                pstmt.setInt(11, hosp.getLitId());
            } else {
                pstmt.setNull(11, java.sql.Types.INTEGER);
            }
            pstmt.setInt(12, hosp.getId());

            return pstmt.executeUpdate() > 0;
        }
    }
    
 // Dans dao.HospitalisationDAO.java

    public Hospitalisation getCurrentByPatientId(int patientId) throws SQLException {
        // On cherche une hospitalisation avec etat = 'En cours' pour ce patient
        // S'il peut y en avoir plusieurs (ce qui serait étrange), on prend la plus récente par date_admission
        String sql = "SELECT * FROM hospitalisation " +
                     "WHERE ID_Patient = ? AND etat = 'En cours' " +
                     "ORDER BY date_admission DESC LIMIT 1"; // Pour prendre la plus récente si plusieurs "En cours"

        Hospitalisation hosp = null;
        System.out.println("HospitalisationDAO.getCurrentByPatientId - SQL: " + sql + " avec ID_Patient: " + patientId);

        try (Connection conn = Database.getConnection(); // Ta classe de connexion
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = new Hospitalisation();
                    // Assure-toi de mapper TOUS les champs nécessaires de la table hospitalisation
                    // vers l'objet hosp, comme tu l'as fait pour la méthode getById
                    hosp.setId(rs.getInt("ID_Hospitalisation"));
                    hosp.setIdPatient(rs.getInt("ID_Patient"));
                    hosp.setNomHopital(rs.getString("nom_hopital"));
                    hosp.setService(rs.getString("service"));
                    hosp.setDuree(rs.getString("duree"));
                    hosp.setEtat(rs.getString("etat"));
                    hosp.setIdMedecin(rs.getInt("ID_Medecin"));
                    hosp.setMotif(rs.getString("motif"));
                    hosp.setDiagnosticInitial(rs.getString("Diagnostic_Initial"));
                    hosp.setDateAdmission(rs.getDate("date_admission"));
                    hosp.setDateSortiePrevue(rs.getDate("Date_Sortie_Prevue"));
                    hosp.setDateSortieReelle(rs.getDate("Date_Sortie_Reelle"));
                    int litId = rs.getInt("lit_id");
                    if (!rs.wasNull()) {
                        hosp.setLitId(litId);
                    }
                    // Retire chambre_id si tu l'as supprimé de la table
                    System.out.println("HospitalisationDAO.getCurrentByPatientId - Hospitalisation en cours trouvée, ID: " + hosp.getId());
                } else {
                    System.out.println("HospitalisationDAO.getCurrentByPatientId - Aucune hospitalisation en cours trouvée pour patient ID: " + patientId);
                }
            }
        }
        return hosp; // Retourne null si aucune hospitalisation en cours n'est trouvée
    }
    // ... autres méthodes si besoin ...
}