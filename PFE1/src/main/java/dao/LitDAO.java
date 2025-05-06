package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Lit;
import util.Database;

public class LitDAO {
	public List<Lit> getLitsByChambre(int chambreId) throws SQLException {
	    List<Lit> lits = new ArrayList<>();
	    String sql = "SELECT id, numero_lit, id_chambre, est_disponible FROM lit WHERE id_chambre = ?";
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, chambreId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Lit lit = new Lit();
	                lit.setId(rs.getInt("id"));
	                lit.setNumeroLit(rs.getString("numero_lit"));
	                lit.setIdChambre(rs.getInt("id_chambre"));
	                lit.setEstDisponible(rs.getBoolean("est_disponible")); // Conversion automatique
	                lits.add(lit);
	            }
	        }
	    }
	    
	    return lits;
	    }
	public boolean affecterLitAPatient(int idLit, int idPatient) throws SQLException {
	    String sql = "UPDATE lit SET est_disponible = 0, patient_id = ? WHERE id = ? AND est_disponible = 1";
	    
	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, idPatient);
	        stmt.setInt(2, idLit);
	        return stmt.executeUpdate() > 0;
	    }
	}

}