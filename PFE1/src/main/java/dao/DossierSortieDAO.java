package dao;

import model.DossierSortie;
import util.Database; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Pour date_generation

public class DossierSortieDAO {

    /**
     * Insère un nouvel enregistrement de dossier de sortie dans la base de données.
     * Met à jour l'ID de l'objet DossierSortie avec l'ID auto-généré.
     * @param dossier L'objet DossierSortie à insérer.
     * @return true si l'insertion a réussi, false sinon.
     * @throws SQLException Si une erreur de base de données survient.
     */
    public boolean inserer(DossierSortie dossier) throws SQLException {
        String sql = "INSERT INTO dossier_sortie (ID_Hospitalisation, nom_fichier, chemin_fichier, type_mime, " +
                     "taille_fichier_octets, date_generation, generer_par_utilisateur_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("DossierSortieDAO.inserer - SQL: " + sql + " pour hospID: " + dossier.getIdHospitalisation());

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, dossier.getIdHospitalisation());
            pstmt.setString(2, dossier.getNomFichier());
            pstmt.setString(3, dossier.getCheminFichier());
            pstmt.setString(4, dossier.getTypeMime() != null ? dossier.getTypeMime() : "application/pdf");

            if (dossier.getTailleFichierOctets() != null) {
                pstmt.setInt(5, dossier.getTailleFichierOctets());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }

            // date_generation est DEFAULT CURRENT_TIMESTAMP en BDD, mais on peut le setter explicitement si besoin
            if (dossier.getDateGeneration() != null) {
                pstmt.setTimestamp(6, dossier.getDateGeneration());
            } else {
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Date actuelle si non fournie
            }

            if (dossier.getGenererParUtilisateurId() != null && dossier.getGenererParUtilisateurId() > 0) {
                pstmt.setInt(7, dossier.getGenererParUtilisateurId());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dossier.setIdDossierSortie(generatedKeys.getInt(1)); // Met à jour l'ID sur l'objet
                        System.out.println("DossierSortieDAO.inserer - Dossier inséré avec ID: " + dossier.getIdDossierSortie());
                        return true;
                    }
                }
            }
            System.err.println("DossierSortieDAO.inserer - Échec de l'insertion du dossier pour hospID: " + dossier.getIdHospitalisation());
            return false;
        }
    }

    /**
     * Récupère un dossier de sortie par son ID.
     * @param idDossierSortie L'ID du dossier de sortie.
     * @return L'objet DossierSortie, ou null s'il n'est pas trouvé.
     * @throws SQLException Si une erreur de base de données survient.
     */
    public DossierSortie getById(int idDossierSortie) throws SQLException {
        String sql = "SELECT * FROM dossier_sortie WHERE ID_Dossier_Sortie = ?";
        DossierSortie dossier = null;
        System.out.println("DossierSortieDAO.getById - SQL: " + sql + " pour ID: " + idDossierSortie);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDossierSortie);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dossier = new DossierSortie();
                    dossier.setIdDossierSortie(rs.getInt("ID_Dossier_Sortie"));
                    dossier.setIdHospitalisation(rs.getInt("ID_Hospitalisation"));
                    dossier.setNomFichier(rs.getString("nom_fichier"));
                    dossier.setCheminFichier(rs.getString("chemin_fichier"));
                    dossier.setTypeMime(rs.getString("type_mime"));
                    int taille = rs.getInt("taille_fichier_octets");
                    if (!rs.wasNull()) {
                        dossier.setTailleFichierOctets(taille);
                    }
                    dossier.setDateGeneration(rs.getTimestamp("date_generation"));
                    int userId = rs.getInt("generer_par_utilisateur_id");
                    if (!rs.wasNull()) {
                        dossier.setGenererParUtilisateurId(userId);
                    }
                    System.out.println("DossierSortieDAO.getById - Dossier trouvé: " + dossier.getNomFichier());
                } else {
                     System.out.println("DossierSortieDAO.getById - Aucun dossier trouvé pour ID: " + idDossierSortie);
                }
            }
        }
        return dossier;
    }

    /**
     * Récupère le dossier de sortie associé à une hospitalisation.
     * Puisque ID_Hospitalisation est UNIQUE dans dossier_sortie, cela retourne au plus un dossier.
     * @param idHospitalisation L'ID de l'hospitalisation.
     * @return L'objet DossierSortie, ou null s'il n'existe pas.
     * @throws SQLException Si une erreur de base de données survient.
     */
    public DossierSortie getByHospitalisationId(int idHospitalisation) throws SQLException {
        String sql = "SELECT * FROM dossier_sortie WHERE ID_Hospitalisation = ?";
        DossierSortie dossier = null;
        System.out.println("DossierSortieDAO.getByHospitalisationId - SQL: " + sql + " pour HospID: " + idHospitalisation);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idHospitalisation);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dossier = new DossierSortie();
                    dossier.setIdDossierSortie(rs.getInt("ID_Dossier_Sortie"));
                    dossier.setIdHospitalisation(rs.getInt("ID_Hospitalisation"));
                    dossier.setNomFichier(rs.getString("nom_fichier"));
                    dossier.setCheminFichier(rs.getString("chemin_fichier"));
                    dossier.setTypeMime(rs.getString("type_mime"));
                    int taille = rs.getInt("taille_fichier_octets");
                    if (!rs.wasNull()) {
                        dossier.setTailleFichierOctets(taille);
                    }
                    dossier.setDateGeneration(rs.getTimestamp("date_generation"));
                    int userId = rs.getInt("generer_par_utilisateur_id");
                    if (!rs.wasNull()) {
                        dossier.setGenererParUtilisateurId(userId);
                    }
                     System.out.println("DossierSortieDAO.getByHospitalisationId - Dossier trouvé: " + dossier.getNomFichier());
                } else {
                    System.out.println("DossierSortieDAO.getByHospitalisationId - Aucun dossier trouvé pour HospID: " + idHospitalisation);
                }
            }
        }
        return dossier;
    }

    // Tu pourrais ajouter des méthodes update ou delete si nécessaire plus tard.
}