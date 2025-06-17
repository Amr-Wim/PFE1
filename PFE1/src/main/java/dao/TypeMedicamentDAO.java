package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TypeMedicament;
import util.Database;

public class TypeMedicamentDAO {
    // PAS DE CONSTRUCTEUR ICI, CE N'EST PAS NÉCESSAIRE

    public List<TypeMedicament> findAll() throws SQLException {
        List<TypeMedicament> types = new ArrayList<>();
        String sql = "SELECT id, nom FROM type_medicament ORDER BY nom ASC";

        try (Connection conn = Database.getConnection(); // La connexion est obtenue ici
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                TypeMedicament type = new TypeMedicament();
                type.setId(rs.getInt("id"));
                type.setNom(rs.getString("nom"));
                types.add(type);
            }
        }
        return types;
    }
}