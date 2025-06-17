package model;

public class Medicament {

    private int id;
    private String nom;
    private Integer typeId;
	public Medicament() {
		super();
	}
	public Medicament(int id, String nom, Integer typeId) {
		super();
		this.id = id;
		this.nom = nom;
		this.typeId = typeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
    
}
