package model;

import java.util.Date; // java.util.Date pour la flexibilité, sera converti en java.sql.Date pour la DB

public class DemandeExamen {
    private int id;
    private int idPatient;
    private int idMedecinPrescripteur;
    private int idExamen;
    private Date dateDemande; // java.util.Date
    private String statutDemande;
    private String notesMedecin;
    private Date dateEstimeeResultatsPrets; // java.util.Date
    private Date dateRealisationEffective; // java.util.Date, peut inclure l'heure

    private String resultatsTexte;
    private String resultatsFichierUrl;
    private Date dateResultatsDisponibles; // java.util.Date, peut inclure l'heure
    private Date dateCreation; // Automatiquement géré par la DB
    private Date dateModification; // Automatiquement géré par la DB

    // Constructeur vide
    public DemandeExamen() {}

    // Getters et Setters pour tous les champs
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public int getIdMedecinPrescripteur() { return idMedecinPrescripteur; }
    public void setIdMedecinPrescripteur(int idMedecinPrescripteur) { this.idMedecinPrescripteur = idMedecinPrescripteur; }
    public int getIdExamen() { return idExamen; }
    public void setIdExamen(int idExamen) { this.idExamen = idExamen; }
    public Date getDateDemande() { return dateDemande; }
    public void setDateDemande(Date dateDemande) { this.dateDemande = dateDemande; }
    public String getStatutDemande() { return statutDemande; }
    public void setStatutDemande(String statutDemande) { this.statutDemande = statutDemande; }
    public String getNotesMedecin() { return notesMedecin; }
    public void setNotesMedecin(String notesMedecin) { this.notesMedecin = notesMedecin; }
    public Date getDateEstimeeResultatsPrets() { return dateEstimeeResultatsPrets; }
    public void setDateEstimeeResultatsPrets(Date dateEstimeeResultatsPrets) { this.dateEstimeeResultatsPrets = dateEstimeeResultatsPrets; }
    public Date getDateRealisationEffective() { return dateRealisationEffective; }
    public void setDateRealisationEffective(Date dateRealisationEffective) { this.dateRealisationEffective = dateRealisationEffective; }
   
    public String getResultatsTexte() { return resultatsTexte; }
    public void setResultatsTexte(String resultatsTexte) { this.resultatsTexte = resultatsTexte; }
    public String getResultatsFichierUrl() { return resultatsFichierUrl; }
    public void setFichierUrl(String resultatsFichierUrl) { this.resultatsFichierUrl = resultatsFichierUrl; }
    public Date getDateResultatsDisponibles() { return dateResultatsDisponibles; }
    public void setDateResultatsDisponibles(Date dateResultatsDisponibles) { this.dateResultatsDisponibles = dateResultatsDisponibles; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
    public Date getDateModification() { return dateModification; }
    public void setDateModification(Date dateModification) { this.dateModification = dateModification; }

    @Override
    public String toString() {
        return "DemandeExamen{" + "id=" + id + ", idPatient=" + idPatient + // ... autres champs
               '}';
    }
}