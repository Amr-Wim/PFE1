package model;

import java.sql.Date;

public class Diagnostic {
	  private String contenu;
	    private Date date;
	    private int id;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
	    
		public Diagnostic(String contenu, Date date, int id) {
			super();
			this.contenu = contenu;
			this.date = date;
			this.id = id;
		}
		public Diagnostic() {
			super();
		}
		public Diagnostic(String contenu, Date date) {
			super();
			this.contenu = contenu;
			this.date = date;
		}
		public String getContenu() {
			return contenu;
		}
		public void setContenu(String contenu) {
			this.contenu = contenu;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
	    
}
