package model;

public class Lit {
    private int id;
    private int chambreId; // Référence à la chambre
    // private String numero; // Optionnel: si vous avez un "numéro de lit" spécifique dans la table lit
    private String sexeAutorise; // Masculin, Féminin, Mixte
    private int ageMin;
    private int ageMax;
    private boolean occupe;

    // Champs pour stocker les informations jointes (pour affichage)
    private String numeroChambre;
    private String nomServiceChambre; // Nom du service où se trouve la chambre

    // Constructeurs
    public Lit() {}

    public Lit(int id, int chambreId, String sexeAutorise, int ageMin, int ageMax, boolean occupe) {
        this.id = id;
        this.chambreId = chambreId;
        this.sexeAutorise = sexeAutorise;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.occupe = occupe;
    }

    // Getters et Setters pour les champs de base
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getChambreId() { return chambreId; }
    public void setChambreId(int chambreId) { this.chambreId = chambreId; }

    public String getSexeAutorise() { return sexeAutorise; }
    public void setSexeAutorise(String sexeAutorise) { this.sexeAutorise = sexeAutorise; }

    public int getAgeMin() { return ageMin; }
    public void setAgeMin(int ageMin) { this.ageMin = ageMin; }

    public int getAgeMax() { return ageMax; }
    public void setAgeMax(int ageMax) { this.ageMax = ageMax; }

    public boolean isOccupe() { return occupe; } // Bonne pratique d'utiliser 'is' pour les getters de booléens
    public void setOccupe(boolean occupe) { this.occupe = occupe; }

    // Getters et Setters pour les champs d'informations jointes
    public String getNumeroChambre() {
        return numeroChambre;
    }
    public void setNumeroChambre(String numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public String getNomServiceChambre() {
        return nomServiceChambre;
    }
    public void setNomServiceChambre(String nomServiceChambre) {
        this.nomServiceChambre = nomServiceChambre;
    }

    
}