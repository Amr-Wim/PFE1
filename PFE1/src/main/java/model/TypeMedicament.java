package model;

public class TypeMedicament {
	 private int id;
	    private String nom;
		public TypeMedicament() {
			super();
		}

		public TypeMedicament(int id, String nom) {
			super();
			this.id = id;
			this.nom = nom;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}
		
}
