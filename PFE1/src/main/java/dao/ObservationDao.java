package dao;

import model.Observation;
import util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservationDao {

    public boolean ajouterObservation(Observation observation) throws SQLException {
        // La requête insère le nom de l'infirmier, pas l'ID
        String sql = "INSERT INTO observation (id_patient, temperature, tension, frequence_cardiaque, observation, date_mesure) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, observation.getIdPatient());
            ps.setFloat(2, observation.getTemperature());
            ps.setString(3, observation.getTension());
            ps.setInt(4, observation.getFrequence());
            ps.setString(5, observation.getTexte());
            ps.setDate(6, observation.getDate());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Observation> findByPatientId(int idPatient) throws SQLException {
        List<Observation> observations = new ArrayList<>();
        // Pas de jointure, on lit directement la colonne nom_infirmier
        String sql = "SELECT * FROM observation WHERE id_patient = ? ORDER BY date_mesure DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Observation obs = new Observation();
                    obs.setId(rs.getInt("id"));
                    obs.setTemperature(rs.getFloat("temperature"));
                    obs.setTension(rs.getString("tension"));
                    obs.setFrequence(rs.getInt("frequence_cardiaque"));
                    obs.setDate(rs.getDate("date_mesure"));
                    obs.setTexte(rs.getString("observation"));
                   
                    observations.add(obs);
                }
            }
        }
        return observations;
    }
}