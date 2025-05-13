package models;

import java.sql.Date;

public class patient {
	 private int id;
	    private String adresse;
	    private java.sql.Date datenaissance;
		public patient(int id, String adresse, Date datenaissance) {
			super();
			this.id = id;
			this.adresse = adresse;
			this.datenaissance = datenaissance;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getAdresse() {
			return adresse;
		}
		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}
		public java.sql.Date getDatenaissance() {
			return datenaissance;
		}
		public void setDatenaissance(java.sql.Date datenaissance) {
			this.datenaissance = datenaissance;
		}
	    
}


