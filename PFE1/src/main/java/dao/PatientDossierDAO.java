package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class PatientDossierDAO {
    private Connection con;

    public PatientDossierDAO(Connection con) {
        this.con = con;
    }

    public List<Allergie> getAllergies(int idPatient) throws SQLException {
        List<Allergie> allergies = new ArrayList<>();
        String sql = "SELECT * FROM allergie WHERE id_patient = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Allergie a = new Allergie();
      
                a.setAllergie(rs.getString("allergie"));
                a.setNiveauGravite(rs.getString("niveau_gravite"));
                a.setDateDetection(rs.getDate("date_detection"));
                allergies.add(a);
            }
        }
        return allergies;
    }

    public List<Antecedant> getAntecedants(int idPatient) throws SQLException {
        List<Antecedant> antecedants = new ArrayList<>();
        String sql = "SELECT * FROM antecedant WHERE id_patient = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Antecedant a = new Antecedant();
               
                a.setType(rs.getString("type_antecedant"));
                a.setDescription(rs.getString("description"));
                a.setDate(rs.getDate("date_enregistrement"));
                antecedants.add(a);
            }
        }
        return antecedants;
    }

    public List<Diagnostic> getDiagnostics(int idPatient) throws SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        String sql = "SELECT * FROM diagnostic WHERE id_patient = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Diagnostic d = new Diagnostic();
              
                d.setContenu(rs.getString("contenu"));
                d.setDate(rs.getDate("date_diagnostic"));
                diagnostics.add(d);
            }
        }
        return diagnostics;
    }

    public List<Traitement> getTraitements(int idPatient) throws SQLException {
        List<Traitement> traitements = new ArrayList<>();
        String sql = "SELECT * FROM traitement WHERE id_patient = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPatient);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Traitement t = new Traitement();
             
                t.setContenu(rs.getString("contenu"));
                t.setDuree(rs.getString("duree"));
                t.setDate_enregistrement(rs.getDate("date_enregistrement"));
                traitements.add(t);
            }
        }
        return traitements;
    }
}