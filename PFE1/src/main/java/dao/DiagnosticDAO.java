package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Diagnostic;

import util.Database;

public class DiagnosticDAO {
    public List<Diagnostic> getDiagnosticsByPatientId(int patientId) throws SQLException {
        List<Diagnostic> diagnostics = new ArrayList<>();
        String sql = "SELECT id, contenu, date_diagnostic as date FROM diagnostic WHERE id_patient = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Diagnostic diagnostic = new Diagnostic();
                diagnostic.setId(rs.getInt("id"));
                diagnostic.setContenu(rs.getString("contenu"));
                diagnostic.setDate(rs.getDate("date"));
                diagnostics.add(diagnostic);
            }
        }
        return diagnostics;
    }
}