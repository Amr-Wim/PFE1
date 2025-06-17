package model;

import java.sql.Date;

public class Prescription {
    private int id;
    private int idMedecin;
    private int idPatient;
    private String contenu; // Va stocker les médicaments au format JSON
    private Date datePrescription;

    // Constructeurs, Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    public Date getDatePrescription() { return datePrescription; }
    public void setDatePrescription(Date datePrescription) { this.datePrescription = datePrescription; }
}