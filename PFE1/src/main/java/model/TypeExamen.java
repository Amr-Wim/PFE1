package model;

public class TypeExamen {
    private int id;
    private String nomType;

    // Constructeur vide
    public TypeExamen() {}

    // Constructeur avec paramètres
    public TypeExamen(int id, String nomType) {
        this.id = id;
        this.nomType = nomType;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    @Override
    public String toString() {
        return "TypeExamen{" +
               "id=" + id +
               ", nomType='" + nomType + '\'' +
               '}';
    }
}