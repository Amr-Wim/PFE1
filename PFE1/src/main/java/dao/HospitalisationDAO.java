package dao;

import model.Hospitalisation; // Assure-toi que le modèle Hospitalisation est bien celui avec les nouveaux champs transitoires
import model.Medecin; // Si tu charges l'objet médecin ici
import util.Database; // Ta classe de connexion

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalisationDAO {

    // insertHospitalisation, getById, updateHospitalisation restent comme tu les as fournies
    // (en s'assurant qu'elles gèrent lit_id et non chambre_id)

    public boolean insertHospitalisation(Hospitalisation hosp) throws SQLException {
        String sql = "INSERT INTO hospitalisation (ID_Patient, nom_hopital, service, duree, etat, ID_Medecin, motif, Diagnostic_Initial, Date_Sortie_Prevue, lit_id, date_admission) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // ... (ton code de mapping des paramètres est correct) ...
            pstmt.setInt(1, hosp.getIdPatient());
            pstmt.setString(2, hosp.getNomHopital());
            pstmt.setString(3, hosp.getService());
            pstmt.setString(4, hosp.getDuree());
            pstmt.setString(5, hosp.getEtat());
            pstmt.setInt(6, hosp.getIdMedecin());
            pstmt.setString(7, hosp.getMotif());
            pstmt.setString(8, hosp.getDiagnosticInitial());
            pstmt.setDate(9, hosp.getDateSortiePrevue()); // Doit être java.sql.Date
            if (hosp.getLitId() != null && hosp.getLitId() > 0) {
                pstmt.setInt(10, hosp.getLitId());
            } else {
                pstmt.setNull(10, java.sql.Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hosp.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public Hospitalisation getById(int hospitalisationId) throws SQLException {
        String sql = "SELECT h.*, " +
                     "l.id as id_du_lit, " +
                     "ch.numero as numero_chambre, " +
                     "s_ch.Nom_Service_FR as nom_service_chambre, " +
                     "u_med.nom as nom_medecin, u_med.prenom as prenom_medecin " +
                     "FROM hospitalisation h " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre ch ON l.chambre_id = ch.id " + // l.chambre_id au lieu de h.chambre_id
                     "LEFT JOIN service s_ch ON ch.id_service = s_ch.ID_Service " +
                     "LEFT JOIN medecin med ON h.ID_Medecin = med.ID_Medecin " + // Pour récupérer le nom du médecin
                     "LEFT JOIN utilisateur u_med ON med.ID_Medecin = u_med.id " +
                     "WHERE h.ID_Hospitalisation = ?";
        Hospitalisation hosp = null;
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hospitalisationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = mapRowToHospitalisation(rs); // Utiliser une méthode de mapping
                }
            }
        }
        return hosp;
    }
    
    public boolean updateHospitalisation(Hospitalisation hosp) throws SQLException {
        String sql = "UPDATE hospitalisation SET ID_Patient = ?, nom_hopital = ?, service = ?, duree = ?, " +
                     "etat = ?, ID_Medecin = ?, motif = ?, Diagnostic_Initial = ?, Date_Sortie_Prevue = ?, " +
                     "Date_Sortie_Reelle = ?, lit_id = ? " +
                     "WHERE ID_Hospitalisation = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // ... (ton code de mapping des paramètres est correct) ...
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

    public Hospitalisation getCurrentByPatientId(int patientId) throws SQLException {
        String sql = "SELECT h.*, " + // Tous les champs de hospitalisation
                     "l.id as id_du_lit, " + // Pour le numeroLit, on peut utiliser l'ID du lit
                     "ch.numero as numero_chambre, " +
                     "s_ch.Nom_Service_FR as nom_service_chambre, " + // s_ch est l'alias pour service de la chambre
                     "u_med.nom as nom_medecin, u_med.prenom as prenom_medecin " + // Pour l'objet Medecin
                     "FROM hospitalisation h " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre ch ON l.chambre_id = ch.id " + // Correction: l.chambre_id
                     "LEFT JOIN service s_ch ON ch.id_service = s_ch.ID_Service " +
                     "LEFT JOIN medecin med ON h.ID_Medecin = med.ID_Medecin " + // Jointure pour l'objet Medecin
                     "LEFT JOIN utilisateur u_med ON med.ID_Medecin = u_med.id " + // Jointure pour l'objet Medecin
                     "WHERE h.ID_Patient = ? AND h.etat = 'En cours' " +
                     "ORDER BY h.date_admission DESC LIMIT 1";

        Hospitalisation hosp = null;
        System.out.println("HospitalisationDAO.getCurrentByPatientId - SQL: " + sql + " avec ID_Patient: " + patientId);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = mapRowToHospitalisation(rs); // Utiliser la méthode de mapping
                } else {
                    System.out.println("HospitalisationDAO.getCurrentByPatientId - Aucune hospitalisation en cours trouvée pour patient ID: " + patientId);
                }
            }
        }
        return hosp;
    }

    // Méthode utilitaire de mapping
    private Hospitalisation mapRowToHospitalisation(ResultSet rs) throws SQLException {
        Hospitalisation hosp = new Hospitalisation();
        hosp.setId(rs.getInt("ID_Hospitalisation"));
        hosp.setIdPatient(rs.getInt("ID_Patient"));
        hosp.setNomHopital(rs.getString("nom_hopital"));
        hosp.setService(rs.getString("service")); // Service de l'hospitalisation
        hosp.setDuree(rs.getString("duree"));
        hosp.setEtat(rs.getString("etat"));
        hosp.setIdMedecin(rs.getInt("ID_Medecin"));
        hosp.setMotif(rs.getString("motif"));
        hosp.setDiagnosticInitial(rs.getString("Diagnostic_Initial"));
        hosp.setDateAdmission(rs.getDate("date_admission"));
        hosp.setDateSortiePrevue(rs.getDate("Date_Sortie_Prevue"));
        hosp.setDateSortieReelle(rs.getDate("Date_Sortie_Reelle"));

        int litIdFromDB = rs.getInt("lit_id");
        if (!rs.wasNull()) {
            hosp.setLitId(litIdFromDB);
            // Si tu as une colonne 'numero' dans la table 'lit', tu la récupérerais ici.
            // Sinon, on peut juste utiliser l'ID du lit comme "numéro".
            hosp.setNumeroLit("Lit N°" + rs.getInt("id_du_lit")); // Ou rs.getString("colonne_numero_lit")
            hosp.setNumeroChambre(rs.getString("numero_chambre"));
            hosp.setNomServiceChambre(rs.getString("nom_service_chambre"));
            System.out.println("HospitalisationDAO (mapRow): Lit ID " + hosp.getLitId() + ", Chambre " + hosp.getNumeroChambre() + " dans service " + hosp.getNomServiceChambre());
        } else {
            System.out.println("HospitalisationDAO (mapRow): Aucune information de lit/chambre car lit_id est null pour hosp ID " + hosp.getId());
        }

        // Charger l'objet Medecin si ID_Medecin est présent
        if (hosp.getIdMedecin() > 0) {
            Medecin medecin = new Medecin();
            medecin.setId(hosp.getIdMedecin());
            // Les noms du médecin sont récupérés par la jointure et peuvent être directement settés ici
            // si l'objet Medecin a des setters pour nom et prenom.
            // Ou tu peux juste stocker nom/prenom dans des champs transitoires de Hospitalisation.
            // Pour l'instant, on va supposer que Medecin a setNom/setPrenom.
            // Ces champs viennent de u_med.nom et u_med.prenom dans la requête
            String nomMedecin = rs.getString("nom_medecin");
            String prenomMedecin = rs.getString("prenom_medecin");
            if(nomMedecin != null && prenomMedecin != null) {
                medecin.setNom(nomMedecin);
                medecin.setPrenom(prenomMedecin);
                // Tu pourrais aussi setter d'autres infos du médecin si tu les as jointes
                hosp.setMedecin(medecin);
            }
        }
        return hosp;
    }
}