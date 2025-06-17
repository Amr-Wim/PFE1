package model;

import java.sql.Date;

public class Observation {
    private int id;
    private int idPatient; // Ajouté pour la cohérence
    private int idInfirmier; // On stocke l'ID
    private float temperature;
    private String tension;
    private int frequence;
    private Date date;
    private String texte;
    private String nomInfirmier; // On garde ce champ pour stocker le nom récupéré par la jointure

    // Constructeurs, Getters et Setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }
    public int getIdInfirmier() { return idInfirmier; }
    public void setIdInfirmier(int idInfirmier) { this.idInfirmier = idInfirmier; }
    public float getTemperature() { return temperature; }
    public void setTemperature(float t) { this.temperature = t; }
    public String getTension() { return tension; }
    public void setTension(String t) { this.tension = t; }
    public int getFrequence() { return frequence; }
    public void setFrequence(int f) { this.frequence = f; }
    public Date getDate() { return date; }
    public void setDate(Date d) { this.date = d; }
    public String getTexte() { return texte; }
    public void setTexte(String t) { this.texte = t; }
    public String getNomInfirmier() { return nomInfirmier; }
    public void setNomInfirmier(String nom) { this.nomInfirmier = nom; }
}