package model;

public class Examen {
    private int id;
    private int idTypeExamen; // Référence à l'ID de TypeExamen
    private String nomExamen;
    private Integer dureePreparationResultatsHeures; // Utiliser Integer pour permettre NULL
    private boolean doitEtreAJeun; // Mappe à tinyint(1)
    private String instructionsPreparatoires;
    private TypeExamen typeExamen;

    public TypeExamen getTypeExamen() {
		return typeExamen;
	}

	public void setTypeExamen(TypeExamen typeExamen) {
		this.typeExamen = typeExamen;
	}

	// Constructeur vide
    public Examen() {}

    // Constructeur avec paramètres
    public Examen(int id, int idTypeExamen, String nomExamen, Integer dureePreparationResultatsHeures, boolean doitEtreAJeun, String instructionsPreparatoires) {
        this.id = id;
        this.idTypeExamen = idTypeExamen;
        this.nomExamen = nomExamen;
        this.dureePreparationResultatsHeures = dureePreparationResultatsHeures;
        this.doitEtreAJeun = doitEtreAJeun;
        this.instructionsPreparatoires = instructionsPreparatoires;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTypeExamen() {
        return idTypeExamen;
    }

    public void setIdTypeExamen(int idTypeExamen) {
        this.idTypeExamen = idTypeExamen;
    }

    public String getNomExamen() {
        return nomExamen;
    }

    public void setNomExamen(String nomExamen) {
        this.nomExamen = nomExamen;
    }

    public Integer getDureePreparationResultatsHeures() {
        return dureePreparationResultatsHeures;
    }

    public void setDureePreparationResultatsHeures(Integer dureePreparationResultatsHeures) {
        this.dureePreparationResultatsHeures = dureePreparationResultatsHeures;
    }

    public boolean isDoitEtreAJeun() { // Convention de nommage pour les booléens
        return doitEtreAJeun;
    }

    public void setDoitEtreAJeun(boolean doitEtreAJeun) {
        this.doitEtreAJeun = doitEtreAJeun;
    }

    public String getInstructionsPreparatoires() {
        return instructionsPreparatoires;
    }

    public void setInstructionsPreparatoires(String instructionsPreparatoires) {
        this.instructionsPreparatoires = instructionsPreparatoires;
    }

    @Override
    public String toString() {
        return "Examen{" +
               "id=" + id +
               ", idTypeExamen=" + idTypeExamen +
               ", nomExamen='" + nomExamen + '\'' +
               ", dureePreparationResultatsHeures=" + dureePreparationResultatsHeures +
               ", doitEtreAJeun=" + doitEtreAJeun +
               ", instructionsPreparatoires='" + instructionsPreparatoires + '\'' +
               '}';
    }
}