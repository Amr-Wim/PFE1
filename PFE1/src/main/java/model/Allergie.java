package model;

import java.sql.Date;

public class Allergie {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Allergie(int id, String allergie, String niveauGravite, Date dateDetection) {
		super();
		this.id = id;
		this.allergie = allergie;
		this.niveauGravite = niveauGravite;
		this.dateDetection = dateDetection;
	}
	private String allergie, niveauGravite;
    private Date dateDetection;
    
	public Allergie() {
		super();
	}
	public Allergie(String allergie, String niveauGravite, Date dateDetection) {
		super();
		this.allergie = allergie;
		this.niveauGravite = niveauGravite;
		this.dateDetection = dateDetection;
	}
	public String getAllergie() {
		return allergie;
	}
	public void setAllergie(String allergie) {
		this.allergie = allergie;
	}
	public String getNiveauGravite() {
		return niveauGravite;
	}
	public void setNiveauGravite(String niveauGravite) {
		this.niveauGravite = niveauGravite;
	}
	public Date getDateDetection() {
		return dateDetection;
	}
	public void setDateDetection(Date dateDetection) {
		this.dateDetection = dateDetection;
	}
    
}
