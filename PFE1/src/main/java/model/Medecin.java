

package model;

public class Medecin extends Utilisateur {
    private String specialite;
    private String grade;
    private int idService;
    private String statut;
    private String numeroOrdre;
    private String nomService; // Ajouté pour faciliter l'affichage
    private String nomHopital; // Ajouté pour faciliter l'affichage

    // Constructeurs
    public Medecin() {
        super();
    }

    public Medecin(int id, String nom, String prenom, String email, String sexe, 
                  String login, String motDePasse, String role,
                  String specialite, String grade, int idService, 
                  String statut, String numeroOrdre) {
        super(id, nom, prenom, email, sexe, login, motDePasse, role);
        this.specialite = specialite;
        this.grade = grade;
        this.idService = idService;
        this.statut = statut;
        this.numeroOrdre = numeroOrdre;
    }

    // Getters et Setters
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getNomHopital() {
        return nomHopital;
    }

    public void setNomHopital(String nomHopital) {
        this.nomHopital = nomHopital;
    }
}