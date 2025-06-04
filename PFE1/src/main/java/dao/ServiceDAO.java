package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Database; // Assure-toi que c'est le bon chemin pour ta classe de connexion

public class ServiceDAO {

    public int getServiceIdByName(String nomService) throws SQLException {
        // CORRECTION: Utiliser les bons noms de colonnes
        String sql = "SELECT ID_Service FROM service WHERE Nom_Service_FR = ?";
        System.out.println("ServiceDAO.getServiceIdByName - Exécution SQL: " + sql + " avec paramètre: " + nomService); // Log de débogage

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomService);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int serviceId = rs.getInt("ID_Service"); // CORRECTION: Récupérer "ID_Service"
                    System.out.println("ServiceDAO.getServiceIdByName - Service trouvé, ID: " + serviceId); // Log de débogage
                    return serviceId;
                } else {
                    // Si aucun service n'est trouvé avec ce nom, il est préférable de lever une exception
                    // ou de retourner une valeur indiquant "non trouvé" (ex: 0 ou -1, selon ta convention)
                    // plutôt que de laisser le code atteindre la fin de la méthode sans return explicite dans le if.
                    System.err.println("ServiceDAO.getServiceIdByName - Aucun service trouvé pour le nom: " + nomService); // Log d'erreur
                    throw new SQLException("Service non trouvé: " + nomService);
                }
            }
        } catch (SQLException e) {
            System.err.println("ServiceDAO.getServiceIdByName - Erreur SQL: " + e.getMessage());
            // Il est souvent utile de relancer l'exception pour que la couche appelante puisse la gérer
            throw e; // ou throw new SQLException("Erreur lors de la recherche du service: " + nomService, e);
        }
        // Ce point ne devrait pas être atteint si un service est trouvé ou si une exception est levée.
        // Mais pour satisfaire le compilateur, si le 'throw' dans le 'else' n'est pas là,
        // il faut un return ou un throw ici. Avec le throw dans le else, c'est bon.
    }

    // Tu pourrais avoir d'autres méthodes ici, par exemple pour lister tous les services, etc.
    // public Service getServiceById(int serviceId) throws SQLException { ... }
}