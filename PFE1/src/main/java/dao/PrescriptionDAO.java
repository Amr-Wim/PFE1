package dao;

import model.Prescription;
import util.Database;
import java.sql.*;

public class PrescriptionDAO {
    // Cette méthode insère une prescription et retourne l'ID auto-généré. C'est crucial.
    public int ajouterEtRetournerId(Prescription prescription) throws SQLException {
        // La colonne 'contenu' a été retirée de la requête INSERT
        String sql = "INSERT INTO prescription (id_medecin, id_patient, date_prescription) VALUES (?, ?, ?)";
        
        // L'option Statement.RETURN_GENERATED_KEYS est la clé du succès ici
        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setInt(1, prescription.getIdMedecin());
            statement.setInt(2, prescription.getIdPatient());
            statement.setDate(3, prescription.getDatePrescription());
            
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La création de la prescription a échoué, aucune ligne ajoutée.");
            }

            // Récupérer la clé générée (l'ID)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retourne le premier ID généré
                } else {
                    throw new SQLException("La création de la prescription a échoué, aucun ID obtenu.");
                }
            }
        }
    }
}