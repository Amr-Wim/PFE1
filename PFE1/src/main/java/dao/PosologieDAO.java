package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Posologie;
import util.Database; // Votre classe de connexion

public class PosologieDAO {
    public List<Posologie> findAll() throws SQLException {
        List<Posologie> liste = new ArrayList<>();
        String sql = "SELECT * FROM posologie ORDER BY id";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Posologie p = new Posologie();
                p.setId(rs.getInt("id"));
                p.setLibelle(rs.getString("libelle"));
                liste.add(p);
            }
        }
        return liste;
    }
}