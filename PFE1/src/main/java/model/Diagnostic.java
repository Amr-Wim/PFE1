package model;

import java.sql.Date;

public class Diagnostic {
    private int id;
    private int idPatient; // NOUVEAU
    private int idMedecin; // NOUVEAU
    private Integer idHospitalisation; // NOUVEAU (Integer car peut être null si le diagnostic n'est pas lié à une hospitalisation)
    private String contenu;
    private Date date; // Corresponds à date_diagnostic dans la DB

    // Constructeurs
    public Diagnostic() {
        super();
    }

    // Constructeur utile pour l'insertion
    public Diagnostic(int idPatient, int idMedecin, Integer idHospitalisation, String contenu, Date date) {
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.idHospitalisation = idHospitalisation;
        this.contenu = contenu;
        this.date = date;
    }

    // Getters et Setters
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
    public int getIdMedecin() {
        return idMedecin;
    }
    public void setIdMedecin(int idMedecin) {
        this.idMedecin = idMedecin;
    }
    public Integer getIdHospitalisation() {
        return idHospitalisation;
    }
    public void setIdHospitalisation(Integer idHospitalisation) {
        this.idHospitalisation = idHospitalisation;
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