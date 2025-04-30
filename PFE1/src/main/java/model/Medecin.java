package model;

public class Medecin extends Utilisateur {
    private String specialite;

	public Medecin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Medecin(int id, String nom, String prenom, String email, String login, String motDePasse, String role) {
		super(id, nom, prenom, email, login, motDePasse, role);
		// TODO Auto-generated constructor stub
	}

	public Medecin(String specialite) {
		super();
		this.specialite = specialite;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

}