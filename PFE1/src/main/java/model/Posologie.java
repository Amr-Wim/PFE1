package model;
public class Posologie {
    private int id;
    private String libelle;
    // Constructeurs, getters, setters...
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Posologie(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
	public Posologie() {
		super();
		// TODO Auto-generated constructor stub
	}
	
      
     
}