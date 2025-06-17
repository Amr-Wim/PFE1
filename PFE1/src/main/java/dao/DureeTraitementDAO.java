package dao;

import model.DureeTraitement;
import util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DureeTraitementDAO {
    public List<DureeTraitement> findAll() throws SQLException {
        List<DureeTraitement> durees = new ArrayList<>();
        String sql = "SELECT id, libelle FROM duree_traitement ORDER BY id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DureeTraitement d = new DureeTraitement();
                d.setId(rs.getInt("id"));
                d.setLibelle(rs.getString("libelle"));
                durees.add(d);
            }
        }
        return durees;
    }
}