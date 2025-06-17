package model;

// La classe Infirmier hérite de Utilisateur pour avoir id, nom, prenom, etc.
public class Infirmier extends Utilisateur {
    private int idService;
    private int idHopital;
    private String nomService; // Pour affichage
    
    // Constructeurs, Getters et Setters
    public int getIdService() { return idService; }
    public void setIdService(int idService) { this.idService = idService; }
    public int getIdHopital() { return idHopital; }
    public void setIdHopital(int idHopital) { this.idHopital = idHopital; }
    public String getNomService() { return nomService; }
    public void setNomService(String nomService) { this.nomService = nomService; }
}