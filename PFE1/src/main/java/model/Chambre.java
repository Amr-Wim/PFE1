package model;

import java.util.List;

public class Chambre {
    private int id;
    private String numero;
    private int idService;
    private List<Lit> lits;
    
    // Constructeurs
    public Chambre() {}
    
    public Chambre(int id, String numero, int idService) {
        this.id = id;
        this.numero = numero;
        this.idService = idService;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public int getIdService() { return idService; }
    public void setIdService(int idService) { this.idService = idService; }
    
    public List<Lit> getLits() { return lits; }
    public void setLits(List<Lit> lits) { this.lits = lits; }
}