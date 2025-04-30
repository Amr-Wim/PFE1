package dao;

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
 
    public boolean ajouterUtilisateur(String nom, String prenom, String email, String login, String motDePasse,
            String role, String dateNaissance, String adresse, String specialite, String service) {
Connection conn = null;
boolean success = false;

try {
conn = Database.getConnection();
conn.setAutoCommit(false); // Démarrer une transaction

// 1. Insérer dans la table utilisateur
String sqlUser = "INSERT INTO utilisateur (nom, prenom, email, login, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?)";
PreparedStatement stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
stmtUser.setString(1, nom);
stmtUser.setString(2, prenom);
stmtUser.setString(3, email);
stmtUser.setString(4, login);
stmtUser.setString(5, motDePasse);
stmtUser.setString(6, role);

int affectedRows = stmtUser.executeUpdate();

if (affectedRows > 0) {
ResultSet generatedKeys = stmtUser.getGeneratedKeys();
if (generatedKeys.next()) {
int idUtilisateur = generatedKeys.getInt(1);

// 2. Insérer dans la table spécifique selon le rôle
switch (role) {
case "patient":
    insertPatient(conn, idUtilisateur, dateNaissance, adresse);
    break;
case "medecin":
    insertMedecin(conn, idUtilisateur, specialite);
    break;
case "infirmier":
    insertInfirmier(conn, idUtilisateur, service);
    break;
case "admin":
    insertAdmin(conn, idUtilisateur);
    break;
}

conn.commit(); // Valider la transaction
success = true;
}
}
} catch (SQLException e) {
// Annuler la transaction en cas d'erreur
if (conn != null) {
try {
conn.rollback();
} catch (SQLException ex) {
ex.printStackTrace();
}
}
e.printStackTrace();
} finally {
// Restaurer l'autocommit et fermer la connexion
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

// Méthodes privées pour l'insertion spécifique
private void insertPatient(Connection conn, int id, String dateNaissance, String adresse) throws SQLException {
String sql = "INSERT INTO patient (id, date_naissance, adresse) VALUES (?, ?, ?)";
try (PreparedStatement stmt = conn.prepareStatement(sql)) {
stmt.setInt(1, id);
stmt.setDate(2, Date.valueOf(dateNaissance));
stmt.setString(3, adresse);
stmt.executeUpdate();
}
}

private void insertMedecin(Connection conn, int id, String specialite) throws SQLException {
String sql = "INSERT INTO medecin (id, specialite) VALUES (?, ?)";
try (PreparedStatement stmt = conn.prepareStatement(sql)) {
stmt.setInt(1, id);
stmt.setString(2, specialite);
stmt.executeUpdate();
}
}

private void insertInfirmier(Connection conn, int id, String service) throws SQLException {
String sql = "INSERT INTO infirmier (id, service) VALUES (?, ?)";
try (PreparedStatement stmt = conn.prepareStatement(sql)) {
stmt.setInt(1, id);
stmt.setString(2, service);
stmt.executeUpdate();
}
}

private void insertAdmin(Connection conn, int id) throws SQLException {
String sql = "INSERT INTO admin (id) VALUES (?)";
try (PreparedStatement stmt = conn.prepareStatement(sql)) {
stmt.setInt(1, id);
stmt.executeUpdate();
}
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
}