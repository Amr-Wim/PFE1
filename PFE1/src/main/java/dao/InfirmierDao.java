package dao;

import model.Infirmier;
import util.Database; // Votre classe de connexion
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfirmierDao {

    /**
     * Recherche les détails d'un infirmier par son ID (qui est le même que son ID utilisateur).
     * La méthode n'est PAS statique.
     * @param idUtilisateur L'ID de l'infirmier à rechercher.
     * @return Un objet Infirmier s'il est trouvé, sinon null.
     * @throws SQLException Si une erreur de base de données se produit.
     */
	public Infirmier findById(int idUtilisateur) throws SQLException {
        Infirmier infirmier = null;
        String sql = "SELECT * FROM infirmier WHERE id = ?";
        
        // Log pour voir si la méthode est appelée
        System.out.println("INFIRMIER_DAO: Recherche de l'infirmier avec ID = " + idUtilisateur);
        
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUtilisateur);
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    // Si on entre ici, c'est qu'une ligne a été trouvée !
                    System.out.println("INFIRMIER_DAO: Ligne trouvée pour ID = " + idUtilisateur);
                    
                    infirmier = new Infirmier();
                    infirmier.setId(rs.getInt("id"));
                    
                    // VÉRIFIEZ QUE CES NOMS DE COLONNES SONT EXACTEMENT LES MÊMES QUE DANS VOTRE TABLE
                    infirmier.setIdService(rs.getInt("ID_Service")); 
                    infirmier.setIdHopital(rs.getInt("ID_Hopital"));
                    
                } else {
                    // Si on entre ici, c'est que la requête n'a rien retourné
                    System.err.println("INFIRMIER_DAO: AUCUNE ligne trouvée dans la table 'infirmier' pour ID = " + idUtilisateur);
                }
            }
        } catch (SQLException e) {
            System.err.println("INFIRMIER_DAO: Erreur SQL pendant la recherche.");
            e.printStackTrace();
            throw e; // Important de relancer l'exception
        }
        
        return infirmier;
    }

}