package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Chambre;
import util.Database;

public class ChambreDAO {
	public List<Chambre> getChambresByService(String nomService) throws SQLException {
	    List<Chambre> chambres = new ArrayList<>();
	    String sql = "SELECT c.id, c.numero, c.id_service " +
	                 "FROM chambre c " +
	                 "JOIN service s ON c.id_service = s.id " +
	                 "WHERE s.nom = ?";
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, nomService);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Chambre chambre = new Chambre();
	                chambre.setId(rs.getInt("id"));
	                chambre.setNumero(rs.getString("numero"));
	                chambre.setIdService(rs.getInt("id_service"));
	                chambres.add(chambre);
	            }
	        }
	    }
	    return chambres;
	}
    
    public Chambre getChambreById(int id) throws SQLException {
        String sql = "SELECT id, numero, id_service FROM chambre WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Chambre chambre = new Chambre();
                    chambre.setId(rs.getInt("id"));
                    chambre.setNumero(rs.getString("numero"));
                    chambre.setIdService(rs.getInt("id_service"));
                    return chambre;
                }
            }
        }
        return null;
    }
}