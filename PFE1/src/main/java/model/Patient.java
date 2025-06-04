package model;

// package model; // Supposons que c'est déjà là

import java.util.Date;
import java.math.BigDecimal; // Importer BigDecimal

public class Patient extends Utilisateur {
    private Date dateNaissance;     // Déjà présent, mais pour la clarté
    private String adresse;         // Déjà présent, mais pour la clarté
    private Integer taille;          // Utiliser Integer pour permettre null
    private double poids;
    private String groupeSanguin;
    private String assuranceMedicale;
    private String numeroAssurance;
    private String cin;
    
    

    public Patient(int id, String nom, String prenom, String email, String sexe, String login, String motDePasse,
			String role, Date dateNaissance, String adresse, Integer taille, double poids, String groupeSanguin,
			String assuranceMedicale, String numeroAssurance, String cin) {
		super(id, nom, prenom, email, sexe, login, motDePasse, role);
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.taille = taille;
		this.poids = poids;
		this.groupeSanguin = groupeSanguin;
		this.assuranceMedicale = assuranceMedicale;
		this.numeroAssurance = numeroAssurance;
		this.cin = cin;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	// Constructeur vide
    public Patient() {
        super();
    }

    // Constructeur pour les champs hérités
    public Patient(int id, String nom, String prenom, String email, String login, String motDePasse, String role) {
        super(id, nom, prenom, email, login, motDePasse, role);
    }

   
    // Getters et Setters pour les champs existants (dateNaissance, adresse)
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

    // Getters et Setters pour les nouveaux champs
    public Integer getTaille() {
        return taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double  poids) {
        this.poids = poids;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getAssuranceMedicale() {
        return assuranceMedicale;
    }

    public void setAssuranceMedicale(String assuranceMedicale) {
        this.assuranceMedicale = assuranceMedicale;
    }

    public String getNumeroAssurance() {
        return numeroAssurance;
    }

    public void setNumeroAssurance(String numeroAssurance) {
        this.numeroAssurance = numeroAssurance;
    }
    
    public int getAge() {
        if (dateNaissance == null) return 0;
        long ageInMillis = System.currentTimeMillis() - dateNaissance.getTime();
        return (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
    }
}