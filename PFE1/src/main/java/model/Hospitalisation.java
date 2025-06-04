package model;

import java.sql.Date;

public class Hospitalisation {
	 private int id;
	    private int idPatient;
	    private String nomHopital;
	    private String service;
	    private String duree;
	    private String etat;
	    private Chambre chambre;
	    
	    private Integer litId;
	    
	     
	    public Integer getLitId() {
			return litId;
		}
		public void setLitId(Integer litId) {
			this.litId = litId;
		}
		private String nomService; // Ajouté pour faciliter l'affichage

	    
	    public String getNomService() {
			return nomService;
		}
		public void setNomService(String nomService) {
			this.nomService = nomService;
		}
		
		private int idMedecin;
	    private String motif;
	    private Date dateAdmission;
	    private Medecin medecin;
	    private String diagnosticInitial;
	    private Date dateSortiePrevue;
	    private Date dateSortieReelle;
	    
	    
		public Date getDateSortiePrevue() {
			return dateSortiePrevue;
		}
		public void setDateSortiePrevue(Date dateSortiePrevue) {
			this.dateSortiePrevue = dateSortiePrevue;
		}
		public Date getDateSortieReelle() {
			return dateSortieReelle;
		}
		public void setDateSortieReelle(Date dateSortieReelle) {
			this.dateSortieReelle = dateSortieReelle;
		}
		public String getDiagnosticInitial() {
			return diagnosticInitial;
		}
		public void setDiagnosticInitial(String diagnosticInitial) {
			this.diagnosticInitial = diagnosticInitial;
		}
		public Hospitalisation(int id, int idPatient, String nomHopital, String service, String duree, String etat,
				Chambre chambre, int idMedecin, String motif, Date dateAdmission, Medecin medecin) {
			super();
			this.id = id;
			this.idPatient = idPatient;
			this.nomHopital = nomHopital;
			this.service = service;
			this.duree = duree;
			this.etat = etat;
			this.chambre = chambre;
			this.idMedecin = idMedecin;
			this.motif = motif;
			this.dateAdmission = dateAdmission;
			this.medecin = medecin;
		}
		public Hospitalisation() {
			super();
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getIdPatient() {
			return idPatient;
		}
		public void setIdPatient(int idPatient) {
			this.idPatient = idPatient;
		}
		public String getNomHopital() {
			return nomHopital;
		}
		public void setNomHopital(String nomHopital) {
			this.nomHopital = nomHopital;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		public String getDuree() {
			return duree;
		}
		public void setDuree(String duree) {
			this.duree = duree;
		}
		public String getEtat() {
			return etat;
		}
		public void setEtat(String etat) {
			this.etat = etat;
		}
		public Chambre getChambre() {
			return chambre;
		}
		public void setChambre(Chambre chambre) {
			this.chambre = chambre;
		}
		public int getIdMedecin() {
			return idMedecin;
		}
		public void setIdMedecin(int idMedecin) {
			this.idMedecin = idMedecin;
		}
		public String getMotif() {
			return motif;
		}
		public void setMotif(String motif) {
			this.motif = motif;
		}
		public Date getDateAdmission() {
			return dateAdmission;
		}
		public void setDateAdmission(Date dateAdmission) {
			this.dateAdmission = dateAdmission;
		}
		public Medecin getMedecin() {
			return medecin;
		}
		public void setMedecin(Medecin medecin) {
			this.medecin = medecin;
		}
	    
	    

}
