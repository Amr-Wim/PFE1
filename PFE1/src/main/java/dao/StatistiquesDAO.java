package dao;

import util.Database; // Ta classe de connexion
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatistiquesDAO {

    public int getNombreLitsOccupes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM lit WHERE est_occupe = TRUE";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getNombreTotalLits() throws SQLException {
        String sql = "SELECT COUNT(*) FROM lit";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("StatistiquesDAO: Total Lits (BDD) = " + count);
                return count;
            }
        }
        System.out.println("StatistiquesDAO: Total Lits (BDD) = 0 (pas de résultat)");
        return 0;
    }
   

    // Exemple d'une autre statistique
    public int getNombrePatientsActifsHospitalises() throws SQLException {
        String sql = "SELECT COUNT(DISTINCT ID_Patient) FROM hospitalisation WHERE etat = 'En cours'";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

     public int getNombreTotalPatients() throws SQLException {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE role = 'patient' AND Statut = 'Actif'";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}