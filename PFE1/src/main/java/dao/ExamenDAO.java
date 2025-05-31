package dao;

import model.Examen;
import util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamenDAO {

    public List<Examen> getExamensByType(int typeExamenId) throws SQLException {
        List<Examen> examens = new ArrayList<>();
        String sql = "SELECT id, id_type_examen, nom_examen, duree_preparation_resultats_heures, doit_etre_a_jeun, instructions_preparatoires FROM Examen WHERE id_type_examen = ? ORDER BY nom_examen";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, typeExamenId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Examen examen = new Examen();
                    examen.setId(rs.getInt("id"));
                    examen.setIdTypeExamen(rs.getInt("id_type_examen"));
                    examen.setNomExamen(rs.getString("nom_examen"));
                    // Gérer la valeur NULL pour duree_preparation_resultats_heures
                    if (rs.getObject("duree_preparation_resultats_heures") != null) {
                        examen.setDureePreparationResultatsHeures(rs.getInt("duree_preparation_resultats_heures"));
                    } else {
                        examen.setDureePreparationResultatsHeures(null); // ou une valeur par défaut si vous préférez int au lieu de Integer
                    }
                    examen.setDoitEtreAJeun(rs.getBoolean("doit_etre_a_jeun"));
                    examen.setInstructionsPreparatoires(rs.getString("instructions_preparatoires"));
                    examens.add(examen);
                }
            }
        }
        return examens;
    }

    public Examen getExamenById(int examenId) throws SQLException {
        Examen examen = null;
        String sql = "SELECT id, id_type_examen, nom_examen, duree_preparation_resultats_heures, doit_etre_a_jeun, instructions_preparatoires FROM Examen WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, examenId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    examen = new Examen();
                    examen.setId(rs.getInt("id"));
                    examen.setIdTypeExamen(rs.getInt("id_type_examen"));
                    examen.setNomExamen(rs.getString("nom_examen"));
                    if (rs.getObject("duree_preparation_resultats_heures") != null) {
                         examen.setDureePreparationResultatsHeures(rs.getInt("duree_preparation_resultats_heures"));
                    } else {
                         examen.setDureePreparationResultatsHeures(null);
                    }
                    examen.setDoitEtreAJeun(rs.getBoolean("doit_etre_a_jeun"));
                    examen.setInstructionsPreparatoires(rs.getString("instructions_preparatoires"));
                }
            }
        }
        return examen;
    }
}