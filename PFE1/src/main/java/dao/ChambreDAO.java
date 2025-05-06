package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Chambre;
import util.Database;

public class ChambreDAO {
	public List<Chambre> getAllChambres() throws SQLException {
	    System.out.println("Exécution de getAllChambres()");
	    List<Chambre> chambres = new ArrayList<>();
	    String sql = "SELECT id, numero, service FROM chambre";
	    System.out.println("SQL : " + sql);

	    try (Connection conn = Database.getConnection()) {
	        System.out.println("Connexion obtenue : " + conn);
	        try (Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {
	            
	            System.out.println("Exécution de la requête...");
	            while (rs.next()) {
	                System.out.println("Chambre trouvée - ID: " + rs.getInt("id"));
	                Chambre chambre = new Chambre(
	                    rs.getInt("id"),
	                    rs.getString("numero"),
	                    rs.getString("service")
	                );
	                chambres.add(chambre);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("ERREUR SQL dans getAllChambres:");
	        e.printStackTrace(System.err);
	        throw e;
	    }
	    return chambres;
	}

}