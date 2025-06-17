package model;

// Classe simple (POJO/Bean) pour représenter une ligne de la table prescription_detail
public class PrescriptionDetail {

    private int id;
    private int idPrescription;
    private int idMedicament;
    private int idDosage;
    private int idPosologie;
    private int idDureeTraitement;

    // Getters et Setters pour tous les champs...
    // Exemple pour un champ :
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	public int getIdPrescription() {
		return idPrescription;
	}
	public void setIdPrescription(int idPrescription) {
		this.idPrescription = idPrescription;
	}
	public int getIdMedicament() {
		return idMedicament;
	}
	public void setIdMedicament(int idMedicament) {
		this.idMedicament = idMedicament;
	}
	public int getIdDosage() {
		return idDosage;
	}
	public void setIdDosage(int idDosage) {
		this.idDosage = idDosage;
	}
	public int getIdPosologie() {
		return idPosologie;
	}
	public void setIdPosologie(int idPosologie) {
		this.idPosologie = idPosologie;
	}
	public int getIdDureeTraitement() {
		return idDureeTraitement;
	}
	public void setIdDureeTraitement(int idDureeTraitement) {
		this.idDureeTraitement = idDureeTraitement;
	}
	public PrescriptionDetail(int id, int idPrescription, int idMedicament, int idDosage, int idPosologie,
			int idDureeTraitement) {
		super();
		this.id = id;
		this.idPrescription = idPrescription;
		this.idMedicament = idMedicament;
		this.idDosage = idDosage;
		this.idPosologie = idPosologie;
		this.idDureeTraitement = idDureeTraitement;
	}
	public PrescriptionDetail() {
		super();
	}
    
    
    // ... Faites de même pour idPrescription, idMedicament, idDosage, etc.
}