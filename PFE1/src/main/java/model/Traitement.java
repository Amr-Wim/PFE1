package model;

import java.sql.Date;



public class Traitement {
    private String contenu;
    private Date date_enregistrement;
    private String duree; // Changé de "Duree" à "duree"
    private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    
    public Traitement(String contenu, Date date_enregistrement, String duree, int id) {
		super();
		this.contenu = contenu;
		this.date_enregistrement = date_enregistrement;
		this.duree = duree;
		this.id = id;
	}
	public Traitement() {
        super();
    }
    
    public Traitement(String contenu, Date date_enregistrement, String duree) {
        this.contenu = contenu;
        this.date_enregistrement = date_enregistrement;
        this.duree = duree;
    }
    
    // Getters et setters (corrigez les noms)
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public Date getDate_enregistrement() {
        return date_enregistrement;
    }
    
    public void setDate_enregistrement(Date date_enregistrement) {
        this.date_enregistrement = date_enregistrement;
    }
    
    public String getDuree() {
        return duree;
    }
    
    public void setDuree(String duree) {
        this.duree = duree;
    }
}