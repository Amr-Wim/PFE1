package service;

import dao.HospitalisationDAO;
import dao.LitDAO;
import java.sql.Connection;
import java.sql.SQLException;
import util.Database; // Votre classe de connexion

public class GestionLitService {
    private LitDAO litDAO;
    private HospitalisationDAO hospitalisationDAO;

    public GestionLitService() {
        this.litDAO = new LitDAO();
        this.hospitalisationDAO = new HospitalisationDAO();
    }

    public boolean changerLitPatient(int hospitalisationId, Integer ancienLitId, int nouveauLitId, String raison)
            throws SQLException {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false); // Début de la transaction

            // 1. Libérer l'ancien lit, s'il y en a un et s'il est différent du nouveau
            if (ancienLitId != null && ancienLitId > 0 && !ancienLitId.equals(nouveauLitId)) {
                boolean ancienLibere = litDAO.libererLit(ancienLitId); // Utilise la connexion implicite du DAO
                if (!ancienLibere) {
                    // Peut-être que le lit était déjà libre, ou n'existait pas.
                    // Loguer mais continuer si ce n'est pas critique pour l'attribution du nouveau.
                    System.err.println("GestionLitService: L'ancien lit ID " + ancienLitId + " n'a pas pu être marqué comme libéré (peut-être déjà libre).");
                } else {
                     System.out.println("GestionLitService: Ancien lit ID " + ancienLitId + " libéré.");
                }
            }

            // 2. Attribuer le nouveau lit
            boolean nouveauAttribue = litDAO.attribuerLit(nouveauLitId); // Utilise la connexion implicite du DAO
            if (!nouveauAttribue) {
                conn.rollback();
                System.err.println("GestionLitService: Le nouveau lit ID " + nouveauLitId + " n'a pas pu être attribué (peut-être déjà occupé ou inexistant).");
                throw new SQLException("Impossible d'attribuer le nouveau lit ID: " + nouveauLitId + ". Il est peut-être déjà occupé ou n'existe pas.");
            }
             System.out.println("GestionLitService: Nouveau lit ID " + nouveauLitId + " attribué.");


            // 3. Mettre à jour l'hospitalisation avec le nouvel ID de lit
            boolean hospMaj = hospitalisationDAO.updateLitIdForHospitalisation(hospitalisationId, nouveauLitId);
            if (!hospMaj) {
                conn.rollback();
                System.err.println("GestionLitService: Échec de la mise à jour de l'hospitalisation ID " + hospitalisationId + " avec le nouveau lit ID " + nouveauLitId);
                throw new SQLException("Impossible de mettre à jour l'hospitalisation avec le nouveau lit.");
            }
             System.out.println("GestionLitService: Hospitalisation ID " + hospitalisationId + " mise à jour avec lit ID " + nouveauLitId);


            // Optionnel: Enregistrer la raison du changement (nécessiterait une table d'historique des lits/changements)
            // logChangementDeLit(hospitalisationId, ancienLitId, nouveauLitId, raison, conn);

            conn.commit(); // Fin de la transaction, tout s'est bien passé
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("GestionLitService: Erreur lors du rollback de la transaction de changement de lit: " + ex.getMessage());
                }
            }
            System.err.println("GestionLitService: Erreur SQL lors du changement de lit: " + e.getMessage());
            throw e; // Relancer pour que le servlet puisse l'attraper
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Rétablir l'autocommit
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("GestionLitService: Erreur lors de la fermeture de la connexion: " + e.getMessage());
                }
            }
        }
    }
}