package service;

import dao.HospitalisationDAO;
import dao.LitDAO;
import dao.ServiceDAO;
import model.Hospitalisation;
import model.Lit;
import model.Patient;
import exception.AucunLitDisponibleException; // Ton exception custom

import java.sql.Connection;
import java.sql.SQLException;
// Retirer import dao.ChambreDAO et List si non utilisés directement ici

public class HospitalisationService {
    private HospitalisationDAO hospitalisationDAO = new HospitalisationDAO();
    private LitDAO litDAO = new LitDAO();
    private AttributionLitService attributionLitService = new AttributionLitService(); // Utilisera la version corrigée

    public boolean creerHospitalisationAvecLit(Hospitalisation hosp, Patient patient) throws SQLException, AucunLitDisponibleException {
        // L'objet hosp arrive avec les infos de base (patientId, motif, etc., mais PAS litId)
        // Le nom du service est dans hosp.getService() (mis par le servlet depuis le médecin)

        Connection conn = null;
        try {
            // 1. Trouver un lit adapté
            // On utilise directement AttributionLitService qui a déjà la logique pour serviceId, sexe, age
            Lit litDisponible = attributionLitService.trouverLitAdaptePourPatient(hosp.getService(), patient);

            

            // 2. Lit trouvé ! Mettre à jour l'objet Hospitalisation
            hosp.setLitId(litDisponible.getId());
            // etat et dateAdmission sont gérés par la BDD ou settés avant l'appel

            // ----- DEBUT TRANSACTION (si possible) -----
            // conn = DatabaseConnection.getConnection(); // Obtenir une connexion
            // conn.setAutoCommit(false); // Désactiver l'auto-commit

            // 3. Insérer l'hospitalisation en BDD
            // Si tu gères la transaction ici, tu devras passer 'conn' à tes méthodes DAO
            // ou adapter tes DAO pour qu'ils utilisent une connexion fournie.
            // Pour l'instant, on garde l'appel simple.
            boolean hospitalisationCreee = hospitalisationDAO.insertHospitalisation(hosp);

            if (hospitalisationCreee) {
                // 4. Marquer le lit comme occupé
                boolean litMarqueOccupe = litDAO.attribuerLit(litDisponible.getId());
                if (!litMarqueOccupe) {
                    // Problème: l'hospitalisation a été créée mais le lit n'a pas pu être marqué comme occupé.
                    System.err.println("ERREUR CRITIQUE: Hospitalisation ID " + hosp.getId() + " créée, mais le lit ID " + litDisponible.getId() + " n'a pas pu être marqué comme occupé.");
                    // if (conn != null) conn.rollback(); // Annuler la transaction si tu en gères une
                    // Lever une exception ou retourner false
                    throw new SQLException("Échec de la mise à jour du statut du lit après création de l'hospitalisation.");
                }
                // if (conn != null) conn.commit(); // Valider la transaction
                return true; // Succès complet
            } else {
                // if (conn != null) conn.rollback();
                return false; // Échec de la création de l'hospitalisation
            }
        } catch (SQLException e) {
            // if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            throw e; // Relancer pour que le servlet puisse la gérer
        } catch (AucunLitDisponibleException e) {
            // if (conn != null) { try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            throw e; // Relancer
        } finally {
            // if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
            // ----- FIN TRANSACTION -----
        }
    }

    // La méthode libererLitHospitalisation doit être adaptée si chambre_id a été supprimé
    public boolean libererLitHospitalisation(int hospitalisationId) throws SQLException {
        Hospitalisation hosp = hospitalisationDAO.getById(hospitalisationId);

        if (hosp == null || hosp.getLitId() == null || hosp.getLitId() == 0) {
            System.err.println("Hospitalisation non trouvée ou aucun lit assigné pour ID: " + hospitalisationId);
            return false;
        }

        boolean litLibere = litDAO.libererLit(hosp.getLitId());

        if (litLibere) {
            // Optionnel: mettre à jour l'état de l'hospitalisation
            if ("En cours".equals(hosp.getEtat())) {
                hosp.setEtat("Sortie");
                
                // --- LA CORRECTION EST ICI ---
                // On crée un objet Timestamp, qui correspond au type attendu par le setter
                hosp.setDateSortieReelle(new java.sql.Timestamp(System.currentTimeMillis()));
                
                hospitalisationDAO.updateHospitalisation(hosp);
            }
            return true;
        }
        return false;
    }
}