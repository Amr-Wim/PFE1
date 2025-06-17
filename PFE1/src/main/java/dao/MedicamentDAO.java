package dao;

import model.Medicament;
import util.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {
    // Pas de constructeur, pas de variable de connexion. C'est plus propre.

    public List<Medicament> findByTypeId(int typeId) throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        String sql = "SELECT id, nom FROM medicament WHERE id_type_medicament = ? ORDER BY nom";
        
        System.out.println("MedicamentDAO: Exécution de la recherche pour typeId = " + typeId);

        // Le try-with-resources garantit que tout est fermé correctement.
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, typeId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicament m = new Medicament();
                    m.setId(rs.getInt("id"));
                    m.setNom(rs.getString("nom"));
                    medicaments.add(m);
                }
            }
        }
        
        System.out.println("MedicamentDAO: " + medicaments.size() + " médicaments trouvés.");
        return medicaments;
    }
}