package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Hospitalisation {
    private int id;
    private int idPatient;
    private String nomHopital; // Hôpital de l'hospitalisation (où le médecin travaille)
    private String service;    // Service de l'hospitalisation (service du médecin traitant)
    private String duree;
    private String etat;
    private Integer litId;     // ID du lit attribué
    private int idMedecin;
    private String motif;
    private Date dateAdmission;
    private Medecin medecin; // Objet Medecin traitant (peut être chargé séparément)
    private String diagnosticInitial;
    private Date dateSortiePrevue;
    private Timestamp dateSortieReelle;
    
    private String patientCin;
    private String patientNom;
    private String patientPrenom;
    
    
    private String compteRenduHospitalisation;
    private String instructionsSortie;
    private String diagnosticsSortie; // Diagnostics finaux de sortie (texte libre)
    private String informationsRendezVousSuivi;
    
    

    public String getCompteRenduHospitalisation() {
		return compteRenduHospitalisation;
	}

	public void setCompteRenduHospitalisation(String compteRenduHospitalisation) {
		this.compteRenduHospitalisation = compteRenduHospitalisation;
	}

	public String getInstructionsSortie() {
		return instructionsSortie;
	}

	public void setInstructionsSortie(String instructionsSortie) {
		this.instructionsSortie = instructionsSortie;
	}

	public String getDiagnosticsSortie() {
		return diagnosticsSortie;
	}

	public void setDiagnosticsSortie(String diagnosticsSortie) {
		this.diagnosticsSortie = diagnosticsSortie;
	}

	public String getInformationsRendezVousSuivi() {
		return informationsRendezVousSuivi;
	}

	public void setInformationsRendezVousSuivi(String informationsRendezVousSuivi) {
		this.informationsRendezVousSuivi = informationsRendezVousSuivi;
	}

	public String getPatientNom() {
		return patientNom;
	}

	public void setPatientNom(String patientNom) {
		this.patientNom = patientNom;
	}

	public String getPatientPrenom() {
		return patientPrenom;
	}

	public void setPatientPrenom(String patientPrenom) {
		this.patientPrenom = patientPrenom;
	}

	public String getPatientCin() {
		return patientCin;
	}

	public void setPatientCin(String patientCin) {
		this.patientCin = patientCin;
	}
	// Champs transitoires pour affichage (ne sont pas des colonnes directes de la table hospitalisation)
    private String numeroLit;         // Si tu as un numéro de lit distinct de l'ID (sinon on utilisera litId)
    private String numeroChambre;     // Numéro de la chambre où se trouve le lit
    private String nomServiceChambre; // Nom du service où se trouve la chambre/lit

    // Constructeurs
    public Hospitalisation() {
        super();
    }

    // Constructeur complet (ajoute les nouveaux champs si tu veux l'utiliser, mais pas crucial)
    public Hospitalisation(int id, int idPatient, String nomHopital, String service, String duree, String etat,
                           Integer litId, /*Chambre chambre, (on va charger dynamiquement)*/ int idMedecin, String motif, Date dateAdmission, Medecin medecin,
                           String diagnosticInitial, Date dateSortiePrevue, Timestamp dateSortieReelle) {
        this.id = id;
        this.idPatient = idPatient;
        this.nomHopital = nomHopital;
        this.service = service;
        this.duree = duree;
        this.etat = etat;
        this.litId = litId;
        // this.chambre = chambre; // On va le charger différemment
        this.idMedecin = idMedecin;
        this.motif = motif;
        this.dateAdmission = dateAdmission;
        this.medecin = medecin;
        this.diagnosticInitial = diagnosticInitial;
        this.dateSortiePrevue = dateSortiePrevue;
        this.dateSortieReelle = dateSortieReelle;
    }


    // Getters et Setters pour les champs existants...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public String getNomHopital() { return nomHopital; }
    public void setNomHopital(String nomHopital) { this.nomHopital = nomHopital; }
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }
    public String getDuree() { return duree; }
    public void setDuree(String duree) { this.duree = duree; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getLitId() { return litId; }
    public void setLitId(Integer litId) { this.litId = litId; }
    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    public Date getDateAdmission() { return dateAdmission; }
    public void setDateAdmission(Date dateAdmission) { this.dateAdmission = dateAdmission; }
    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
    public String getDiagnosticInitial() { return diagnosticInitial; }
    public void setDiagnosticInitial(String diagnosticInitial) { this.diagnosticInitial = diagnosticInitial; }
    public Date getDateSortiePrevue() { return dateSortiePrevue; }
    public void setDateSortiePrevue(Date dateSortiePrevue) { this.dateSortiePrevue = dateSortiePrevue; }
    public Timestamp getDateSortieReelle() { return dateSortieReelle; }
    public void setDateSortieReelle(Timestamp dateSortieReelle) { // CHANGER LE TYPE DU PARAMÈTRE
        this.dateSortieReelle = dateSortieReelle;}

    // Getters et Setters pour les nouveaux champs transitoires
    public String getNumeroLit() { return numeroLit; }
    public void setNumeroLit(String numeroLit) { this.numeroLit = numeroLit; }

    public String getNumeroChambre() { return numeroChambre; }
    public void setNumeroChambre(String numeroChambre) { this.numeroChambre = numeroChambre; }

    public String getNomServiceChambre() { return nomServiceChambre; }
    public void setNomServiceChambre(String nomServiceChambre) { this.nomServiceChambre = nomServiceChambre; }

    // J'ai supprimé la référence directe à l'objet Chambre pour la remplacer par les infos via jointure
    // public Chambre getChambre() { return chambre; }
    // public void setChambre(Chambre chambre) { this.chambre = chambre; }

    // Le champ 'nomService' que tu avais ajouté semble être une duplication de 'service'.
    // 'service' devrait contenir le nom du service de l'hospitalisation (service du médecin).
    // 'nomServiceChambre' contiendra le nom du service où se trouve physiquement le lit/chambre.
    // Ce sont potentiellement les mêmes, mais pas toujours (ex: un médecin d'un service peut hospitaliser
    // un patient dans un lit d'un autre service si son service est plein, bien que ce soit une logique complexe).
    // Pour l'instant, on suppose que service = nomServiceChambre si le lit est dans le service du médecin.
}