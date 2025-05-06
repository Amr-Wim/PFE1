package model;



import java.util.List;

public class Chambre {
    private int id;
    private String numero;
    private String service;
    private List<Lit> lits;
    private int nombreLitsDisponibles;
    
    // Constructeurs
    public Chambre() {}
    
    public Chambre(int id, String numero, String service) {
        this.id = id;
        this.numero = numero;
        this.service = service;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    
    public List<Lit> getLits() { return lits; }
    public void setLits(List<Lit> lits) { this.lits = lits; }
    
    public int getNombreLitsDisponibles() { return nombreLitsDisponibles; }
    public void setNombreLitsDisponibles(int nombreLitsDisponibles) { 
        this.nombreLitsDisponibles = nombreLitsDisponibles; 
    }
}