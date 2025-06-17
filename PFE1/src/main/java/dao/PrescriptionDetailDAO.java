package dao;

import model.PrescriptionDetail;
import util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PrescriptionDetailDAO {
    // Méthode pour ajouter UN seul détail de prescription
    public void ajouter(PrescriptionDetail detail) throws SQLException {
        String sql = "INSERT INTO prescription_detail (id_prescription, id_medicament, id_dosage, id_posologie, id_duree_traitement) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, detail.getIdPrescription());
            statement.setInt(2, detail.getIdMedicament());
            statement.setInt(3, detail.getIdDosage());
            statement.setInt(4, detail.getIdPosologie());
            statement.setInt(5, detail.getIdDureeTraitement());
            statement.executeUpdate();
        }
    }
    
    // Méthode optimisée pour ajouter une liste de détails en une seule transaction (batch update)
    public void ajouterEnLot(List<PrescriptionDetail> details) throws SQLException {
        String sql = "INSERT INTO prescription_detail (id_prescription, id_medicament, id_dosage, id_posologie, id_duree_traitement) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            for (PrescriptionDetail detail : details) {
                statement.setInt(1, detail.getIdPrescription());
                statement.setInt(2, detail.getIdMedicament());
                statement.setInt(3, detail.getIdDosage());
                statement.setInt(4, detail.getIdPosologie());
                statement.setInt(5, detail.getIdDureeTraitement());
                statement.addBatch(); // Ajoute la requête au lot
            }
            statement.executeBatch(); // Exécute toutes les requêtes d'un coup
        }
    }
}