package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Medecin;
import util.Database;

public class MedecinDAO {
	public Medecin getById(int id) throws SQLException {
	    // Requête simplifiée utilisant uniquement les colonnes existantes
	    String query = "SELECT m.ID_Medecin, m.specialite, m.Grade, " +
	                  "m.ID_Service, m.Statut, m.Numero_Ordre " +
	                  "FROM medecin m WHERE m.ID_Medecin = ?";
	    
	    try (Connection con = Database.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            Medecin medecin = new Medecin();
	            medecin.setId(rs.getInt("ID_Medecin"));
	            medecin.setSpecialite(rs.getString("specialite"));
	            medecin.setGrade(rs.getString("Grade"));
	            medecin.setIdService(rs.getInt("ID_Service"));
	            medecin.setStatut(rs.getString("Statut"));
	            medecin.setNumeroOrdre(rs.getString("Numero_Ordre"));
	            return medecin;
	        }
	    }
	    return null;
	}

    // Récupérer tous les médecins
    public List<Medecin> getAll() throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT u.*, m.specialite FROM utilisateur u JOIN medecin m ON u.id = m.id";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }
        }
        return medecins;
    }

    // Récupérer les médecins par spécialité
    public List<Medecin> getBySpecialite(String specialite) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT u.*, m.specialite FROM utilisateur u JOIN medecin m ON u.id = m.id WHERE m.specialite = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, specialite);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }
        }
        return medecins;
    }

    // Ajouter un nouveau médecin
    public boolean add(Medecin medecin) throws SQLException {
        String sql = "INSERT INTO medecin (id, specialite) VALUES (?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, medecin.getId());
            stmt.setString(2, medecin.getSpecialite());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Mettre à jour un médecin
    public boolean update(Medecin medecin) throws SQLException {
        String sql = "UPDATE medecin SET specialite = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, medecin.getSpecialite());
            stmt.setInt(2, medecin.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Supprimer un médecin
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM medecin WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Méthode utilitaire pour mapper un ResultSet à un objet Medecin
    private Medecin mapMedecin(ResultSet rs) throws SQLException {
        Medecin medecin = new Medecin();
        medecin.setId(rs.getInt("id"));
        medecin.setNom(rs.getString("nom"));
        medecin.setPrenom(rs.getString("prenom"));
        medecin.setEmail(rs.getString("email"));
        medecin.setLogin(rs.getString("login"));
        medecin.setMotDePasse(rs.getString("mot_de_passe"));
        medecin.setRole(rs.getString("role"));
        medecin.setSpecialite(rs.getString("specialite"));
        return medecin;
    }
    
 // ... dans ta méthode qui récupère le médecin avec service et hôpital ...
    public Medecin getMedecinWithServiceAndHopital(int utilisateurId) throws SQLException {
        Medecin medecin = null;
        // Tu as déjà une requête pour récupérer les infos de base du médecin.
        // Modifie-la ou ajoute une jointure :
        String sql = "SELECT u.*, m.specialite, m.Grade, m.ID_Service, m.Statut AS MedecinStatut, m.Numero_Ordre, " +
                     "s.Nom_Service_FR, h.Nom_Hopital " +
                     "FROM utilisateur u " +
                     "JOIN medecin m ON u.id = m.ID_Medecin " +
                     "LEFT JOIN service s ON m.ID_Service = s.ID_Service " + // LEFT JOIN au cas où un médecin n'aurait pas de service défini (peu probable mais sûr)
                     "LEFT JOIN hopital h ON s.ID_Hopital = h.ID_Hopital " +
                     "WHERE u.id = ? AND u.role = 'medecin'";

        try (Connection conn = Database.getConnection(); // Ta classe de connexion
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, utilisateurId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    medecin = new Medecin();
                    // Peuple les champs de Utilisateur (super-classe)
                    medecin.setId(rs.getInt("id"));
                    medecin.setNom(rs.getString("nom"));
                    medecin.setPrenom(rs.getString("prenom"));
                    medecin.setEmail(rs.getString("email"));
                    // ... autres champs de l'utilisateur ...
                    medecin.setRole(rs.getString("role"));

                    // Peuple les champs spécifiques à Medecin
                    medecin.setSpecialite(rs.getString("specialite"));
                    medecin.setGrade(rs.getString("Grade"));
                    medecin.setIdService(rs.getInt("ID_Service"));
                    medecin.setStatut(rs.getString("MedecinStatut"));
                    medecin.setNumeroOrdre(rs.getString("Numero_Ordre"));

                    // Peuple nomService et nomHopital
                    medecin.setNomService(rs.getString("Nom_Service_FR"));
                    medecin.setNomHopital(rs.getString("Nom_Hopital"));
                }
            }
        } // La connexion et le PreparedStatement sont fermés automatiquement ici
        return medecin;
    }
    
    public Medecin getMedecinWithDetailsById(int medecinId) throws SQLException {
        Medecin medecin = null;
        String sql = "SELECT u.id, u.nom, u.prenom, u.email, u.sexe, " + // Champs de utilisateur
                     "m.specialite, m.Grade, m.Numero_Ordre " +          // Champs de medecin
                     "FROM utilisateur u " +
                     "JOIN medecin m ON u.id = m.ID_Medecin " +
                     "WHERE u.id = ? AND u.role = 'medecin'";

        System.out.println("MedecinDAO.getMedecinWithDetailsById - SQL: " + sql + " pour ID: " + medecinId);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, medecinId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    medecin = new Medecin();
                    medecin.setId(rs.getInt("id"));
                    medecin.setNom(rs.getString("nom"));
                    medecin.setPrenom(rs.getString("prenom"));
                    medecin.setEmail(rs.getString("email"));
                    medecin.setSexe(rs.getString("sexe"));
                    // Champs spécifiques au médecin
                    medecin.setSpecialite(rs.getString("specialite"));
                    medecin.setGrade(rs.getString("Grade"));
                    medecin.setNumeroOrdre(rs.getString("Numero_Ordre"));
                    // Note: nomService et nomHopital ne sont pas chargés par cette méthode,
                    // mais par getMedecinWithServiceAndHopital. Pour le PDF, la spécialité est souvent suffisante.
                    System.out.println("MedecinDAO: Détails médecin chargés - " + medecin.getNom() + ", Spécialité: " + medecin.getSpecialite());
                } else {
                    System.out.println("MedecinDAO.getMedecinWithDetailsById - Aucun médecin trouvé pour ID: " + medecinId);
                }
            }
        }
        return medecin;
    }
}