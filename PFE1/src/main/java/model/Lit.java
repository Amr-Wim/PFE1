package model;


public class Lit {
    private int id;
    private String numeroLit;
    private int idChambre;
    private boolean estDisponible;
    
    // Constructeurs
    public Lit() {}
    
    public Lit(int id, String numeroLit, int idChambre, boolean estDisponible) {
        this.id = id;
        this.numeroLit = numeroLit;
        this.idChambre = idChambre;
        this.estDisponible = estDisponible;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNumeroLit() { return numeroLit; }
    public void setNumeroLit(String numeroLit) { this.numeroLit = numeroLit; }
    
    public int getIdChambre() { return idChambre; }
    public void setIdChambre(int idChambre) { this.idChambre = idChambre; }
    
    public boolean isEstDisponible() { return estDisponible; }
    public void setEstDisponible(boolean estDisponible) { this.estDisponible = estDisponible; }
}