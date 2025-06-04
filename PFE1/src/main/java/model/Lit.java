package model;

public class Lit {
    private int id;
    private int chambreId;
    private String sexeAutorise;
    private int ageMin;
    private int ageMax;
    private boolean occupe;
    
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
    
    // Getters et Setters
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
    
    public boolean isOccupe() { return occupe; }
    public void setOccupe(boolean occupe) { this.occupe = occupe; }
}