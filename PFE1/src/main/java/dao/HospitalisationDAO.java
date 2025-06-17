package dao;

import model.Hospitalisation;
import model.Medecin;
import model.Patient; // Ajouté pour la méthode getHospitalisationsEnCoursParMedecin
import util.Database; // Ta classe de connexion

import java.sql.*; // Import général pour les types SQL
import java.util.ArrayList;
import java.util.List;

public class HospitalisationDAO {

    public boolean insertHospitalisation(Hospitalisation hosp) throws SQLException {
        String sql = "INSERT INTO hospitalisation (ID_Patient, nom_hopital, service, duree, etat, ID_Medecin, motif, " +
                     "Diagnostic_Initial, Date_Sortie_Prevue, lit_id, date_admission, " +
                     "compte_rendu_hospitalisation, instructions_sortie, diagnostics_de_sortie, informations_rendez_vous_suivi, Date_Sortie_Reelle) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, hosp.getIdPatient());
            pstmt.setString(2, hosp.getNomHopital());
            pstmt.setString(3, hosp.getService());
            pstmt.setString(4, hosp.getDuree());
            pstmt.setString(5, hosp.getEtat());
            pstmt.setInt(6, hosp.getIdMedecin());
            pstmt.setString(7, hosp.getMotif());
            pstmt.setString(8, hosp.getDiagnosticInitial());
            pstmt.setDate(9, hosp.getDateSortiePrevue()); // java.sql.Date

            if (hosp.getLitId() != null && hosp.getLitId() > 0) {
                pstmt.setInt(10, hosp.getLitId());
            } else {
                pstmt.setNull(10, java.sql.Types.INTEGER);
            }
            // Les nouveaux champs texte pour la sortie, initialement null ou vides
            pstmt.setString(11, hosp.getCompteRenduHospitalisation());
            pstmt.setString(12, hosp.getInstructionsSortie());
            pstmt.setString(13, hosp.getDiagnosticsSortie());
            pstmt.setString(14, hosp.getInformationsRendezVousSuivi());
            pstmt.setTimestamp(15, hosp.getDateSortieReelle()); // java.sql.Timestamp

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
        // Jointure pour récupérer toutes les infos, y compris les détails du lit/chambre et médecin
        // La requête SQL dans ton code était déjà bien pour cela.
         String sql = "SELECT h.*, " +
                     "l.id as id_du_lit, " +
                     "ch.numero as numero_chambre, " +
                     "s_ch.Nom_Service_FR as nom_service_chambre, " +
                     "u_med.nom as nom_medecin, u_med.prenom as prenom_medecin " +
                     "FROM hospitalisation h " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre ch ON l.chambre_id = ch.id " +
                     "LEFT JOIN service s_ch ON ch.id_service = s_ch.ID_Service " +
                     "LEFT JOIN medecin med ON h.ID_Medecin = med.ID_Medecin " +
                     "LEFT JOIN utilisateur u_med ON med.ID_Medecin = u_med.id " +
                     "WHERE h.ID_Hospitalisation = ?";
        Hospitalisation hosp = null;
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hospitalisationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = mapRowToHospitalisation(rs);
                }
            }
        }
        return hosp;
    }
    
    // Tu avais une méthode updateHospitalisation et une méthode updateInfosSortieHospitalisation.
    // Il est préférable d'avoir une seule méthode updateHospitalisation complète si l'objet hosp contient toujours toutes les infos à jour.
    // Ou une méthode très ciblée si tu ne mets à jour que certains champs.
    // Je vais modifier updateHospitalisation pour être plus complète.

    public boolean updateHospitalisation(Hospitalisation hosp) throws SQLException {
        String sql = "UPDATE hospitalisation SET " +
                     "ID_Patient = ?, nom_hopital = ?, service = ?, duree = ?, etat = ?, ID_Medecin = ?, " +
                     "motif = ?, Diagnostic_Initial = ?, Date_Sortie_Prevue = ?, lit_id = ?, " +
                     "compte_rendu_hospitalisation = ?, instructions_sortie = ?, diagnostics_de_sortie = ?, " +
                     "informations_rendez_vous_suivi = ?, Date_Sortie_Reelle = ? " + // Date_Sortie_Reelle est la dernière
                     "WHERE ID_Hospitalisation = ?";
        System.out.println("HospitalisationDAO.updateHospitalisation - SQL: " + sql + " pour ID: " + hosp.getId());

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
            pstmt.setDate(9, hosp.getDateSortiePrevue()); // java.sql.Date

            if (hosp.getLitId() != null && hosp.getLitId() > 0) {
                pstmt.setInt(10, hosp.getLitId());
            } else {
                pstmt.setNull(10, java.sql.Types.INTEGER);
            }

            pstmt.setString(11, hosp.getCompteRenduHospitalisation());
            pstmt.setString(12, hosp.getInstructionsSortie());
            pstmt.setString(13, hosp.getDiagnosticsSortie());
            pstmt.setString(14, hosp.getInformationsRendezVousSuivi());
            pstmt.setTimestamp(15, hosp.getDateSortieReelle()); // *** CORRECTION ICI: utiliser setTimestamp ***

            pstmt.setInt(16, hosp.getId()); // Pour la clause WHERE

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    public boolean updateLitIdForHospitalisation(int hospitalisationId, Integer nouveauLitId) throws SQLException {
        String sql = "UPDATE hospitalisation SET lit_id = ? WHERE ID_Hospitalisation = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (nouveauLitId != null && nouveauLitId > 0) {
                pstmt.setInt(1, nouveauLitId);
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setInt(2, hospitalisationId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Hospitalisation getCurrentByPatientId(int patientId) throws SQLException {
        // La requête que tu avais est bonne, on s'assure juste que mapRowToHospitalisation est correcte.
        String sql = "SELECT h.*, " +
                     "l.id as id_du_lit, " +
                     "ch.numero as numero_chambre, " +
                     "s_ch.Nom_Service_FR as nom_service_chambre, " +
                     "u_med.nom as nom_medecin, u_med.prenom as prenom_medecin " +
                     "FROM hospitalisation h " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre ch ON l.chambre_id = ch.id " +
                     "LEFT JOIN service s_ch ON ch.id_service = s_ch.ID_Service " +
                     "LEFT JOIN medecin med ON h.ID_Medecin = med.ID_Medecin " +
                     "LEFT JOIN utilisateur u_med ON med.ID_Medecin = u_med.id " +
                     "WHERE h.ID_Patient = ? AND h.etat = 'En cours' " +
                     "ORDER BY h.date_admission DESC LIMIT 1";
        Hospitalisation hosp = null;
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = mapRowToHospitalisation(rs);
                }
            }
        }
        return hosp;
    }

    private Hospitalisation mapRowToHospitalisation(ResultSet rs) throws SQLException {
        Hospitalisation hosp = new Hospitalisation();
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
        hosp.setDateSortieReelle(rs.getTimestamp("Date_Sortie_Reelle")); // *** CORRECTION ICI: utiliser getTimestamp ***

        int litIdFromDB = rs.getInt("lit_id");
        if (!rs.wasNull()) {
            hosp.setLitId(litIdFromDB);
            hosp.setNumeroLit("Lit N°" + rs.getInt("id_du_lit")); // Assumant que 'id_du_lit' est l'alias pour l.id
            hosp.setNumeroChambre(rs.getString("numero_chambre"));
            hosp.setNomServiceChambre(rs.getString("nom_service_chambre"));
        }

        // Champs de sortie ajoutés à la table hospitalisation
        hosp.setCompteRenduHospitalisation(rs.getString("compte_rendu_hospitalisation"));
        hosp.setInstructionsSortie(rs.getString("instructions_sortie"));
        hosp.setDiagnosticsSortie(rs.getString("diagnostics_de_sortie"));
        hosp.setInformationsRendezVousSuivi(rs.getString("informations_rendez_vous_suivi"));

        if (hosp.getIdMedecin() > 0) {
            String nomMedecin = rs.getString("nom_medecin");
            String prenomMedecin = rs.getString("prenom_medecin");
            if (nomMedecin != null && prenomMedecin != null) {
                Medecin medecin = new Medecin();
                medecin.setId(hosp.getIdMedecin());
                medecin.setNom(nomMedecin);
                medecin.setPrenom(prenomMedecin);
                // Tu pourrais charger d'autres infos du médecin ici si nécessaire (ex: spécialité)
                // si elles sont sélectionnées par la requête SQL.
                hosp.setMedecin(medecin);
            }
            
            System.out.println("DEBUG DAO mapRow: État lu depuis BDD: [" + rs.getString("etat") + "]"); 
        }
        return hosp;
    }
    
    public List<Hospitalisation> getHospitalisationsEnCoursParMedecin(int idMedecin) throws SQLException {
        List<Hospitalisation> hospitalisations = new ArrayList<>();
        String sql = "SELECT " +
                     "h.ID_Hospitalisation, h.ID_Patient, h.date_admission, h.motif, h.duree, h.service, " +
                     "u.nom as patient_nom, u.prenom as patient_prenom, u.cin as patient_cin, " +
                     "l.id as lit_numero_id, " +
                     "c.numero as chambre_numero " +
                     "FROM hospitalisation h " +
                     "JOIN utilisateur u ON h.ID_Patient = u.id " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre c ON l.chambre_id = c.id " + // jointure sur lit.chambre_id
                     "WHERE h.ID_Medecin = ? AND h.etat = 'En cours' " +
                     "ORDER BY u.nom ASC, u.prenom ASC, h.date_admission DESC";
    
        System.out.println("HospitalisationDAO.getHospitalisationsEnCoursParMedecin - SQL: " + sql + " pour Medecin ID: " + idMedecin);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMedecin);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Hospitalisation hosp = new Hospitalisation();
                    hosp.setId(rs.getInt("ID_Hospitalisation"));
                    hosp.setIdPatient(rs.getInt("ID_Patient"));
                    hosp.setDateAdmission(rs.getDate("date_admission"));
                    hosp.setMotif(rs.getString("motif"));
                    hosp.setDuree(rs.getString("duree"));
                    hosp.setService(rs.getString("service"));

                    hosp.setPatientNom(rs.getString("patient_nom"));
                    hosp.setPatientPrenom(rs.getString("patient_prenom"));
                    hosp.setPatientCin(rs.getString("patient_cin"));
                 
                    int litId = rs.getInt("lit_numero_id");
                    if (!rs.wasNull()) {
                        hosp.setLitId(litId);
                        hosp.setNumeroLit("Lit N°" + litId);
                        hosp.setNumeroChambre(rs.getString("chambre_numero"));
                    }
                    hospitalisations.add(hosp);
                }
            }
        }
        System.out.println("HospitalisationDAO.getHospitalisationsEnCoursParMedecin - " + hospitalisations.size() + " hospitalisations trouvées.");
        return hospitalisations;
    }
 
    // Cette méthode est celle appelée par PreparerSortieServlet.doPost
    // Elle doit mettre à jour les champs spécifiques à la sortie et l'état.
    public boolean updateInfosSortieHospitalisation(Hospitalisation hosp) throws SQLException {
        String sql = "UPDATE hospitalisation SET " +
                     "Date_Sortie_Reelle = ?, " +
                     "etat = ?, " +
                     "compte_rendu_hospitalisation = ?, " +
                     "instructions_sortie = ?, " +
                     "diagnostics_de_sortie = ?, " +
                     "informations_rendez_vous_suivi = ? " +
                     "WHERE ID_Hospitalisation = ?";
        System.out.println("HospitalisationDAO.updateInfosSortieHospitalisation pour ID: " + hosp.getId());
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, hosp.getDateSortieReelle()); // *** UTILISER setTimestamp ***
            pstmt.setString(2, hosp.getEtat()); // Doit être "Sortie"
            pstmt.setString(3, hosp.getCompteRenduHospitalisation());
            pstmt.setString(4, hosp.getInstructionsSortie());
            pstmt.setString(5, hosp.getDiagnosticsSortie());
            pstmt.setString(6, hosp.getInformationsRendezVousSuivi());
            pstmt.setInt(7, hosp.getId());

            return pstmt.executeUpdate() > 0;
        }
    }
    
 // Dans dao.HospitalisationDAO.java
    public Hospitalisation getLatestHospitalisationByPatientIdWithDetails(int patientId) throws SQLException {
        String sql = "SELECT h.*, " +
                     "l.id as id_du_lit, ch.numero as numero_chambre, s_ch.Nom_Service_FR as nom_service_chambre, " +
                     "u_med.nom as nom_medecin, u_med.prenom as prenom_medecin " +
                     "FROM hospitalisation h " +
                     "LEFT JOIN lit l ON h.lit_id = l.id " +
                     "LEFT JOIN chambre ch ON l.chambre_id = ch.id " +
                     "LEFT JOIN service s_ch ON ch.id_service = s_ch.ID_Service " +
                     "LEFT JOIN medecin med ON h.ID_Medecin = med.ID_Medecin " +
                     "LEFT JOIN utilisateur u_med ON med.ID_Medecin = u_med.id " +
                     "WHERE h.ID_Patient = ? " + // PAS DE FILTRE SUR h.etat
                     "ORDER BY h.date_admission DESC, h.ID_Hospitalisation DESC LIMIT 1"; // Prend la plus récente
        Hospitalisation hosp = null;
        System.out.println("HospitalisationDAO.getLatestHospitalisationByPatientIdWithDetails - SQL: " + sql + " pour Patient ID: " + patientId);
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hosp = mapRowToHospitalisation(rs); // Utilise ta méthode de mapping existante
                    System.out.println("HospitalisationDAO.getLatestHospitalisationByPatientIdWithDetails - Hospitalisation trouvée ID: " + hosp.getId() + ", État: " + hosp.getEtat());
                } else {
                     System.out.println("HospitalisationDAO.getLatestHospitalisationByPatientIdWithDetails - Aucune hospitalisation trouvée pour Patient ID: " + patientId);
                }
            }
        }
        return hosp;
    }
    
    public List<Hospitalisation> getPatientsActifsParMedecin(int idMedecin) {
        List<Hospitalisation> hospitalisations = new ArrayList<>();
        // Cette requête récupère les infos de l'hospitalisation ET du patient associé
        String sql = "SELECT h.*, u.nom AS patient_nom, u.prenom AS patient_prenom " +
                     "FROM hospitalisation h " +
                     "JOIN patient p ON h.ID_Patient = p.id " +
                     "JOIN utilisateur u ON p.id = u.id " +
                     "WHERE h.ID_Medecin = ? AND h.etat = 'En cours'";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMedecin);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Hospitalisation hosp = new Hospitalisation();
                hosp.setId(rs.getInt("ID_Hospitalisation"));
                hosp.setIdPatient(rs.getInt("ID_Patient"));
                // ... peupler les autres champs de l'hospitalisation si besoin
                
                // Peupler les champs "transitoires" pour l'affichage dans la liste
                hosp.setPatientNom(rs.getString("patient_nom"));
                hosp.setPatientPrenom(rs.getString("patient_prenom"));
                
                hospitalisations.add(hosp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hospitalisations;
    }
}