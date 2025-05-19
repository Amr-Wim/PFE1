package model;

import java.sql.Date;

public class Hospitalisation {
	 private int id;
	    private int idPatient;
	    private String nomHopital;
	    private String service;
	    private String duree;
	    private String etat;
	    private String chambre;
	    private int idMedecin;
	    private String motif;
	    private Date dateAdmission;
	    private Medecin medecin;
		public Hospitalisation(int id, int idPatient, String nomHopital, String service, String duree, String etat,
				String chambre, int idMedecin, String motif, Date dateAdmission, Medecin medecin) {
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
		public String getChambre() {
			return chambre;
		}
		public void setChambre(String chambre) {
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
