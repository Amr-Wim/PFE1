package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt; 

import model.Utilisateur;
import util.Database;

public class UtilisateurDAO {

	public Utilisateur authentifier(String loginInput, String motDePasseInput) throws SQLException { // Ajout de throws SQLException
        Utilisateur utilisateur = null;
        // 1. Récupérer l'utilisateur par login seulement
        String sql = "SELECT * FROM utilisateur WHERE login = ?";

        System.out.println("UtilisateurDAO.authentifier: Tentative pour login = [" + loginInput.trim() + "]");

        // Gestion correcte des ressources JDBC avec try-with-resources
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loginInput.trim()); // Bonne pratique d'utiliser trim()

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 2. Utilisateur trouvé, récupérer son mot de passe haché de la BDD
                    String hashedPasswordFromDB = rs.getString("mot_de_passe");

                    // 3. Vérifier le mot de passe fourni contre le hachage stocké
                    //    Assure-toi que motDePasseInput n'est pas null avant BCrypt.checkpw
                    if (hashedPasswordFromDB != null && motDePasseInput != null && BCrypt.checkpw(motDePasseInput, hashedPasswordFromDB)) {
                        // Mot de passe correct !
                        utilisateur = new Utilisateur();
                        utilisateur.setId(rs.getInt("id"));
                        utilisateur.setNom(rs.getString("nom"));
                        utilisateur.setPrenom(rs.getString("prenom"));
                        utilisateur.setEmail(rs.getString("email"));
                        utilisateur.setLogin(rs.getString("login"));
                        // NE PAS stocker le motDePasseHache dans l'objet utilisateur en session
                        utilisateur.setRole(rs.getString("role"));
                        utilisateur.setSexe(rs.getString("sexe")); // Assure-toi de le récupérer
                        // ... mapper les autres champs nécessaires de l'utilisateur ...
                        System.out.println("UtilisateurDAO.authentifier: Utilisateur trouvé et mot de passe vérifié pour ID: " + utilisateur.getId());
                    } else {
                        // Mot de passe incorrect ou pas de hachage en BDD ou motDePasseInput est null
                        System.out.println("UtilisateurDAO.authentifier: Login trouvé, mais mot de passe incorrect, ou hachage BDD/input manquant.");
                        System.out.println("    Hachage BDD: [" + hashedPasswordFromDB + "]"); // Pour débogage
                        // NE PAS loguer motDePasseInput en production
                    }
                } else {
                    // Aucun utilisateur trouvé pour ce login
                    System.out.println("UtilisateurDAO.authentifier: Aucun utilisateur trouvé pour le login: " + loginInput.trim());
                }
            }
        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.authentifier: SQLException - " + e.getMessage());
            e.printStackTrace(); // Logguer l'erreur SQL
            throw e; // Relancer l'exception pour que la couche appelante (servlet) puisse la gérer
        } catch (Exception e) { // Attraper d'autres exceptions potentielles (ex: IllegalArgumentException de BCrypt)
             System.err.println("UtilisateurDAO.authentifier: Exception - " + e.getMessage());
            e.printStackTrace();
            // Tu pourrais envelopper cela dans une SQLException ou une exception personnalisée
            throw new SQLException("Erreur inattendue pendant l'authentification.", e);
        }
        return utilisateur;
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
