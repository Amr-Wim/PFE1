// Dans AttributionLitService.java
package service;

import dao.LitDAO;
import dao.ServiceDAO;
import exception.AucunLitDisponibleException;
import model.Lit;
import model.Patient;
import java.sql.SQLException;

public class AttributionLitService {
    private LitDAO litDAO = new LitDAO();
    private ServiceDAO serviceDAO = new ServiceDAO();

    public Lit trouverLitAdaptePourPatient(String nomDuService, Patient patient) throws SQLException, AucunLitDisponibleException {
        if (patient == null || nomDuService == null || nomDuService.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du service et l'objet patient sont requis.");
        }

        String sexeInitialPatient = patient.getSexe(); // Récupérer la valeur une seule fois

        // ----- DEBUT BLOC DE DEBUG INTENSIF -----
        System.out.println("AttributionLitService DEBUG: Valeur brute de patient.getSexe(): [" + sexeInitialPatient + "]");
        if (sexeInitialPatient == null) {
            System.out.println("AttributionLitService DEBUG: sexeInitialPatient est NULL.");
            throw new IllegalArgumentException("Le sexe du patient est requis (valeur null reçue).");
        }
        System.out.println("AttributionLitService DEBUG: Longueur de sexeInitialPatient: " + sexeInitialPatient.length());
        // Afficher les codes ASCII/Unicode de chaque caractère pour détecter les invisibles
        for (int i = 0; i < sexeInitialPatient.length(); i++) {
            char c = sexeInitialPatient.charAt(i);
            System.out.println("AttributionLitService DEBUG: Caractère à l'index " + i + ": '" + c + "' (code: " + (int) c + ")");
        }

        // Comparaison avec trim() pour éliminer les espaces de début/fin
        String sexeTrimmed = sexeInitialPatient.trim();
        System.out.println("AttributionLitService DEBUG: Valeur de sexeInitialPatient.trim(): [" + sexeTrimmed + "]");
        System.out.println("AttributionLitService DEBUG: Comparaison \"F\".equalsIgnoreCase(sexeTrimmed): " + "F".equalsIgnoreCase(sexeTrimmed));
        System.out.println("AttributionLitService DEBUG: Comparaison \"Féminin\".equalsIgnoreCase(sexeTrimmed): " + "Féminin".equalsIgnoreCase(sexeTrimmed));
        // ----- FIN BLOC DE DEBUG INTENSIF -----


        if (sexeInitialPatient.trim().isEmpty()) { // Vérifier après le log si c'est une chaîne vide après trim
            throw new IllegalArgumentException("Le sexe du patient est requis (valeur vide reçue).");
        }


        String sexePourRecherche;

        // Utiliser la valeur trimmée pour les comparaisons
        if ("M".equalsIgnoreCase(sexeTrimmed)) {
            sexePourRecherche = "Masculin";
        } else if ("F".equalsIgnoreCase(sexeTrimmed)) {
            sexePourRecherche = "Féminin";
        } else if ("Masculin".equalsIgnoreCase(sexeTrimmed)) {
            sexePourRecherche = "Masculin";
        } else if ("Féminin".equalsIgnoreCase(sexeTrimmed)) {
            sexePourRecherche = "Féminin";
        } else if ("Mixte".equalsIgnoreCase(sexeTrimmed)) {
             throw new IllegalArgumentException("La valeur 'Mixte' n'est pas un sexe valide pour un patient en vue d'une attribution de lit spécifique (Masculin/Féminin attendu).");
        }
         else {
            // Ce bloc est atteint si aucune des conditions ci-dessus n'est vraie
            throw new IllegalArgumentException("Valeur de sexe du patient non reconnue pour la recherche de lit: [" + sexeInitialPatient + "]"); // Mettre entre crochets pour voir les espaces
        }

        int serviceId = serviceDAO.getServiceIdByName(nomDuService);
        if (serviceId <= 0) {
            throw new SQLException("Service '" + nomDuService + "' non trouvé ou ID invalide.");
        }
        
        System.out.println("AttributionLitService: Recherche de lit pour serviceId=" + serviceId + ", sexePatientNormalisé=" + sexePourRecherche + ", age=" + patient.getAge());

        Lit litDisponible = litDAO.trouverLitDisponible(
                serviceId,
                sexePourRecherche,
                patient.getAge()
        );

        if (litDisponible == null) {
            throw new AucunLitDisponibleException("Aucun lit disponible ne correspond aux critères pour le patient (sexe: " + sexePourRecherche + ", âge: " + patient.getAge() + ") dans le service " + nomDuService);
        }
        return litDisponible;
    }
}