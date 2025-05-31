package dao;

import model.TypeExamen;
import util.Database; // Assurez-vous que le chemin est correct

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeExamenDAO {

    public List<TypeExamen> getAllTypesExamens() throws SQLException {
        List<TypeExamen> types = new ArrayList<>();
        String sql = "SELECT id, nom_type FROM TypeExamen ORDER BY nom_type";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                TypeExamen type = new TypeExamen();
                type.setId(rs.getInt("id"));
                type.setNomType(rs.getString("nom_type"));
                types.add(type);
            }
        }
        return types;
    }
}