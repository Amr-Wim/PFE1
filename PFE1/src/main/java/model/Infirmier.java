package model;

public class Infirmier extends Utilisateur {
    private String service;

	public Infirmier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Infirmier(int id, String nom, String prenom, String email, String login, String motDePasse, String role) {
		super(id, nom, prenom, email, login, motDePasse, role);
		// TODO Auto-generated constructor stub
	}

	public Infirmier(String service) {
		super();
		this.service = service;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

    // Constructeurs, getters et setters
    
}