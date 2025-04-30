package model;

import java.util.Date;

public class Patient extends Utilisateur {
    private Date dateNaissance;
    private String adresse;
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Patient(int id, String nom, String prenom, String email, String login, String motDePasse, String role) {
		super(id, nom, prenom, email, login, motDePasse, role);
		// TODO Auto-generated constructor stub
	}
	public Patient(Date dateNaissance, String adresse) {
		super();
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
    

    // Constructeurs, getters et setters
}