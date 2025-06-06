package model;

public class Medecin extends Utilisateur {
    private String specialite;
    private String grade;
    private int idService;
    private String statut; // Statut spécifique du médecin ('Disponible', 'En congé', etc.)
    private String numeroOrdre;
    private String nomService; // Nom du service du médecin (pour affichage)
    private String nomHopital; // Nom de l'hôpital du médecin (pour affichage)

    // Constructeurs...
    public Medecin() { super(); }

    // Getters et Setters pour tous les champs...
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    // ... autres getters/setters ...
    public String getNomService() { return nomService; }
    public void setNomService(String nomService) { this.nomService = nomService; }
    public String getNomHopital() { return nomHopital; }
    public void setNomHopital(String nomHopital) { this.nomHopital = nomHopital; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public int getIdService() { return idService; }
    public void setIdService(int idService) { this.idService = idService; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getNumeroOrdre() { return numeroOrdre; }
    public void setNumeroOrdre(String numeroOrdre) { this.numeroOrdre = numeroOrdre; }
}