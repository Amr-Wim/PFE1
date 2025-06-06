package model;

import java.sql.Date;

public class Vaccination {
	 private int id;
	    private int idPatient;
	    private String nomVaccin;
	    private Date dateVaccination;
	    private String reactionsEventuelles;
		public Vaccination() {
			super();
		}
		public Vaccination(int id, int idPatient, String nomVaccin, Date dateVaccination, String reactionsEventuelles) {
			super();
			this.id = id;
			this.idPatient = idPatient;
			this.nomVaccin = nomVaccin;
			this.dateVaccination = dateVaccination;
			this.reactionsEventuelles = reactionsEventuelles;
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
		public String getNomVaccin() {
			return nomVaccin;
		}
		public void setNomVaccin(String nomVaccin) {
			this.nomVaccin = nomVaccin;
		}
		public Date getDateVaccination() {
			return dateVaccination;
		}
		public void setDateVaccination(Date dateVaccination) {
			this.dateVaccination = dateVaccination;
		}
		public String getReactionsEventuelles() {
			return reactionsEventuelles;
		}
		public void setReactionsEventuelles(String reactionsEventuelles) {
			this.reactionsEventuelles = reactionsEventuelles;
		}
	    
}
