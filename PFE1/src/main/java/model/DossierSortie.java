package model;

import java.sql.Timestamp; // Ou java.util.Date si tu préfères date_generation sans l'heure

public class DossierSortie {
    private int idDossierSortie;         // ID_Dossier_Sortie (PK)
    private int idHospitalisation;       // ID_Hospitalisation (FK)
    private String nomFichier;
    private String cheminFichier;
    private String typeMime;
    private Integer tailleFichierOctets; // Integer pour permettre null
    private Timestamp dateGeneration;      // DATETIME en BDD
    private Integer genererParUtilisateurId; // Integer pour permettre null

    // Constructeur vide
    public DossierSortie() {
    }

    // Constructeur avec tous les champs (optionnel, utile pour les tests)
    public DossierSortie(int idDossierSortie, int idHospitalisation, String nomFichier, String cheminFichier,
                         String typeMime, Integer tailleFichierOctets, Timestamp dateGeneration, Integer genererParUtilisateurId) {
        this.idDossierSortie = idDossierSortie;
        this.idHospitalisation = idHospitalisation;
        this.nomFichier = nomFichier;
        this.cheminFichier = cheminFichier;
        this.typeMime = typeMime;
        this.tailleFichierOctets = tailleFichierOctets;
        this.dateGeneration = dateGeneration;
        this.genererParUtilisateurId = genererParUtilisateurId;
    }

    // Getters et Setters
    public int getIdDossierSortie() {
        return idDossierSortie;
    }
    public void setIdDossierSortie(int idDossierSortie) {
        this.idDossierSortie = idDossierSortie;
    }

    public int getIdHospitalisation() {
        return idHospitalisation;
    }
    public void setIdHospitalisation(int idHospitalisation) {
        this.idHospitalisation = idHospitalisation;
    }

    public String getNomFichier() {
        return nomFichier;
    }
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }
    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getTypeMime() {
        return typeMime;
    }
    public void setTypeMime(String typeMime) {
        this.typeMime = typeMime;
    }

    public Integer getTailleFichierOctets() {
        return tailleFichierOctets;
    }
    public void setTailleFichierOctets(Integer tailleFichierOctets) {
        this.tailleFichierOctets = tailleFichierOctets;
    }

    public Timestamp getDateGeneration() {
        return dateGeneration;
    }
    public void setDateGeneration(Timestamp dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Integer getGenererParUtilisateurId() {
        return genererParUtilisateurId;
    }
    public void setGenererParUtilisateurId(Integer genererParUtilisateurId) {
        this.genererParUtilisateurId = genererParUtilisateurId;
    }

    @Override
    public String toString() {
        return "DossierSortie{" +
               "idDossierSortie=" + idDossierSortie +
               ", idHospitalisation=" + idHospitalisation +
               ", nomFichier='" + nomFichier + '\'' +
               ", dateGeneration=" + dateGeneration +
               '}';
    }
}