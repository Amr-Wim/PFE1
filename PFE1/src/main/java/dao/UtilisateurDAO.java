package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Utilisateur;
import util.Database;

public class UtilisateurDAO {

    public Utilisateur authentifier(String login, String motDePasse) {
        Utilisateur utilisateur = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM utilisateur WHERE login = ? AND mot_de_passe = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, motDePasse);

            rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                utilisateur.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return utilisateur;
    }
    
    public boolean existeLogin(String login) {
        boolean existe = false;

        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT id FROM utilisateur WHERE login = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true; // Un utilisateur avec ce login existe déjà
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }
 
   

/**
* Vérifie si un login existe déjà
*/
public boolean loginExiste(String login) {
String sql = "SELECT COUNT(*) FROM utilisateur WHERE login = ?";
try (Connection conn = Database.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setString(1, login);
ResultSet rs = stmt.executeQuery();
return rs.next() && rs.getInt(1) > 0;
} catch (SQLException e) {
e.printStackTrace();
return false;
}
}

/**
* Vérifie si un email existe déjà
*/
public boolean emailExiste(String email) {
String sql = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
try (Connection conn = Database.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setString(1, email);
ResultSet rs = stmt.executeQuery();
return rs.next() && rs.getInt(1) > 0;
} catch (SQLException e) {
e.printStackTrace();
return false;
}
}

/**
* Récupère un utilisateur par son login
*/
public Utilisateur getUtilisateurParLogin(String login) {
String sql = "SELECT * FROM utilisateur WHERE login = ?";
try (Connection conn = Database.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setString(1, login);
ResultSet rs = stmt.executeQuery();

if (rs.next()) {
Utilisateur user = new Utilisateur();
user.setId(rs.getInt("id"));
user.setNom(rs.getString("nom"));
user.setPrenom(rs.getString("prenom"));
user.setEmail(rs.getString("email"));
user.setLogin(rs.getString("login"));
user.setMotDePasse(rs.getString("mot_de_passe"));
user.setRole(rs.getString("role"));
return user;
}
} catch (SQLException e) {
e.printStackTrace();
}
return null;
}

// Signature modifiée pour inclure sexe
public boolean ajouterUtilisateur(String nom, String prenom, String email, String cin, String sexe, String login, String motDePasseHash,
        String dateNaissanceStr, String adresse, 
        String tailleStr, String poidsStr, String groupeSanguin, String assuranceMedicale, String numeroAssurance) {
    
    Connection conn = null;
    boolean success = false;
    String roleUtilisateur = "patient";

    try {
        conn = Database.getConnection();
        conn.setAutoCommit(false); 

        // Adapter la requête pour inclure la colonne sexe
        String sqlUser = "INSERT INTO utilisateur (nom, prenom, Date_Naissance, Adresse, cin, email, sexe, Telephone, login, mot_de_passe, Statut, role) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Ajout d'un placeholder pour sexe

        try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
            stmtUser.setString(1, nom);
            stmtUser.setString(2, prenom);

            if (dateNaissanceStr != null && !dateNaissanceStr.isEmpty()) {
                stmtUser.setDate(3, Date.valueOf(dateNaissanceStr));
            } else {
                stmtUser.setNull(3, java.sql.Types.DATE); 
            }
            if (adresse != null && !adresse.isEmpty()) {
                stmtUser.setString(4, adresse);
            } else {
                stmtUser.setNull(4, java.sql.Types.VARCHAR);
            }
            
            if (cin != null && !cin.trim().isEmpty()) {
                stmtUser.setString(5, cin);
            } else {
                stmtUser.setNull(5, java.sql.Types.VARCHAR); 
            }
            
            stmtUser.setString(6, email);

            // Gérer le Sexe
            if (sexe != null && !sexe.isEmpty()) {
                stmtUser.setString(7, sexe);
            } else {
                stmtUser.setNull(7, java.sql.Types.VARCHAR); // Si la colonne sexe est nullable
            }
            
            stmtUser.setNull(8, java.sql.Types.VARCHAR); // Telephone (non fourni, donc NULL)
            stmtUser.setString(9, login);
            stmtUser.setString(10, motDePasseHash);
            stmtUser.setString(11, "Actif"); // Statut par défaut
            stmtUser.setString(12, roleUtilisateur);

            int affectedRows = stmtUser.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmtUser.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idUtilisateur = generatedKeys.getInt(1);

                    insertPatientDetails(conn, idUtilisateur, dateNaissanceStr, adresse, 
                                      tailleStr, poidsStr, groupeSanguin, assuranceMedicale, numeroAssurance);
                    
                    conn.commit(); 
                    success = true;
                } else {
                    conn.rollback();
                }
            } else {
                conn.rollback();
            }
        }
    } catch (SQLException e) {
        // ... (gestion des erreurs et finally inchangée) ...
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(); 
            }
        }
        e.printStackTrace(); 
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return success;
}

// ... insertPatientDetails reste inchangée ...
private void insertPatientDetails(Connection conn, int idUtilisateur, String dateNaissanceStr, String adresse,
                           String tailleStr, String poidsStr, String groupeSanguin, 
                           String assuranceMedicale, String numeroAssurance) throws SQLException {
    
    String sql = "INSERT INTO patient (id, date_naissance, adresse, Taille, Poids, Groupe_Sanguin, Assurance_Medicale, Numero_Assurance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idUtilisateur);

        if (dateNaissanceStr != null && !dateNaissanceStr.isEmpty()) {
            stmt.setDate(2, Date.valueOf(dateNaissanceStr));
        } else {
            stmt.setNull(2, java.sql.Types.DATE);
        }
        
        if (adresse != null && !adresse.isEmpty()) {
            stmt.setString(3, adresse);
        } else {
            stmt.setNull(3, java.sql.Types.VARCHAR);
        }

        if (tailleStr != null && !tailleStr.isEmpty()) {
            try {
                stmt.setInt(4, Integer.parseInt(tailleStr));
            } catch (NumberFormatException e) {
                stmt.setNull(4, java.sql.Types.INTEGER); 
                System.err.println("Format Taille invalide pour patient ID " + idUtilisateur + ": " + tailleStr + ". Mis à NULL.");
            }
        } else {
            stmt.setNull(4, java.sql.Types.INTEGER);
        }

        if (poidsStr != null && !poidsStr.isEmpty()) {
            try {
                stmt.setBigDecimal(5, new BigDecimal(poidsStr));
            } catch (NumberFormatException e) {
                stmt.setNull(5, java.sql.Types.DECIMAL);
                System.err.println("Format Poids invalide pour patient ID " + idUtilisateur + ": " + poidsStr + ". Mis à NULL.");
            }
        } else {
            stmt.setNull(5, java.sql.Types.DECIMAL);
        }

        if (groupeSanguin != null && !groupeSanguin.isEmpty()) {
            stmt.setString(6, groupeSanguin);
        } else {
            stmt.setNull(6, java.sql.Types.VARCHAR);
        }

        if (assuranceMedicale != null && !assuranceMedicale.isEmpty()) {
            stmt.setString(7, assuranceMedicale);
        } else {
            stmt.setNull(7, java.sql.Types.VARCHAR);
        }

        if (numeroAssurance != null && !numeroAssurance.isEmpty()) {
            stmt.setString(8, numeroAssurance);
        } else {
            stmt.setNull(8, java.sql.Types.VARCHAR);
        }
        
        stmt.executeUpdate();
    }
}
// ... (cinExiste, loginExiste, emailExiste, authentifier, getUtilisateurParLogin) ...



public boolean cinExiste(String cin) {
    if (cin == null || cin.trim().isEmpty()) {
        return false; 
    }
    String sql = "SELECT COUNT(*) FROM utilisateur WHERE cin = ?";
    try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cin);
        try(ResultSet rs = stmt.executeQuery()){
            return rs.next() && rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return true;
    }
}
}
