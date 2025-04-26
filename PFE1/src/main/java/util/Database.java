package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_hospitaliere"; // adapte ici ton nom de BDD
    private static final String USER = "root"; // Ton utilisateur MySQL
    private static final String PASSWORD = "1234"; // Ton mot de passe MySQL (souvent vide en local)

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Assure-toi d'avoir le connecteur MySQL dans ton projet
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erreur de chargement du Driver JDBC", e);
        }
    }
}