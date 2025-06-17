package dao;

import model.Dosage;
import util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DosageDAO {
    public List<Dosage> findAll() throws SQLException {
        List<Dosage> dosages = new ArrayList<>();
        String sql = "SELECT id, valeur FROM dosage ORDER BY id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Dosage d = new Dosage();
                d.setId(rs.getInt("id"));
                d.setValeur(rs.getString("valeur"));
                dosages.add(d);
            }
        }
        return dosages;
    }
}