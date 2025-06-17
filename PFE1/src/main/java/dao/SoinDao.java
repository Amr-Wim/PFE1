package dao;

import model.Soin;
import util.Database; // Votre classe de connexion
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SoinDao {

    // Méthode pour AJOUTER un soin
    public boolean ajouterSoin(Soin soin) throws SQLException {
        String sql = "INSERT INTO soin (id_infirmier, id_patient, description, date_soin, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, soin.getIdInfirmier());
            ps.setInt(2, soin.getIdPatient());
            ps.setString(3, soin.getDescription());
            ps.setDate(4, soin.getDateSoin());
            ps.setString(5, soin.getType());

            return ps.executeUpdate() > 0;
        }
    }

    // Méthode pour TROUVER les soins d'un patient
    public List<Soin> findByPatientId(int idPatient) throws SQLException {
        List<Soin> soins = new ArrayList<>();
        
        // --- CORRECTION : Utilisation de LEFT JOIN ---
        String sql = "SELECT s.*, u.nom as infirmier_nom, u.prenom as infirmier_prenom " +
                     "FROM soin s " +
                     "LEFT JOIN utilisateur u ON s.id_infirmier = u.id " + // <--- CHANGEMENT ICI
                     "WHERE s.id_patient = ? ORDER BY s.date_soin DESC";
        
        // Log pour le débogage
        System.out.println("SoinDao: Exécution de la recherche de soins pour id_patient = " + idPatient);

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPatient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Soin soin = new Soin();
                    soin.setId(rs.getInt("id"));
                    soin.setIdPatient(rs.getInt("id_patient"));
                    soin.setIdInfirmier(rs.getInt("id_infirmier"));
                    soin.setDescription(rs.getString("description"));
                    soin.setDateSoin(rs.getDate("date_soin"));
                    soin.setType(rs.getString("type"));
                    
                    // On récupère le nom et prénom, ils peuvent être NULL à cause du LEFT JOIN
                    String prenom = rs.getString("infirmier_prenom");
                    String nom = rs.getString("infirmier_nom");

                    // On gère le cas où l'infirmier n'est pas trouvé
                    if (prenom != null && nom != null) {
                        soin.setNomInfirmier(prenom + " " + nom);
                    } else {
                        soin.setNomInfirmier("Infirmier inconnu (ID: " + soin.getIdInfirmier() + ")");
                    }
                    
                    soins.add(soin);
                }
            }
        }
        
        System.out.println("SoinDao: " + soins.size() + " soins trouvés.");
        return soins;
    }
}