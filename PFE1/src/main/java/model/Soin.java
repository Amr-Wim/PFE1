package model;

import java.sql.Date;

public class Soin {
    private int id;
    private int idInfirmier;
    private int idPatient;
    private String description;
    private Date dateSoin; // Utiliser java.sql.Date
    private String type;

    // Champ "transitoire" pour stocker le nom de l'infirmier après une jointure
    private String nomInfirmier;

    // Constructeurs, Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdInfirmier() { return idInfirmier; }
    public void setIdInfirmier(int idInfirmier) { this.idInfirmier = idInfirmier; }
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDateSoin() { return dateSoin; }
    public void setDateSoin(Date dateSoin) { this.dateSoin = dateSoin; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getNomInfirmier() { return nomInfirmier; }
    public void setNomInfirmier(String nomInfirmier) { this.nomInfirmier = nomInfirmier; }
}