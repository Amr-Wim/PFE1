package model;

public class Service {
    private int id;                 // Corresponds à ID_Service
    private int idHopital;          // Corresponds à ID_Hopital
    private String nomServiceFR;    // Corresponds à Nom_Service_FR
    private String nomServiceAR;    // Corresponds à Nom_Service_AR
    private String acronyme;        // Corresponds à Acronyme
    private String type;            // Corresponds à Type (enum: 'Médical','Administratif','Technique')
    private Integer capacite;       // Corresponds à Capacite (Integer pour permettre null)
    private Integer responsableId;  // Corresponds à Responsable_ID (Integer pour permettre null)

    // Optionnel : Pour stocker directement le nom de l'hôpital si vous faites une jointure
    private String nomHopital;

    // Constructeur vide
    public Service() {
    }

    // Constructeur avec les champs principaux (vous pouvez en ajouter d'autres)
    public Service(int id, int idHopital, String nomServiceFR, String type) {
        this.id = id;
        this.idHopital = idHopital;
        this.nomServiceFR = nomServiceFR;
        this.type = type;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHopital() {
        return idHopital;
    }

    public void setIdHopital(int idHopital) {
        this.idHopital = idHopital;
    }

    public String getNomServiceFR() {
        return nomServiceFR;
    }

    public void setNomServiceFR(String nomServiceFR) {
        this.nomServiceFR = nomServiceFR;
    }

    public String getNomServiceAR() {
        return nomServiceAR;
    }

    public void setNomServiceAR(String nomServiceAR) {
        this.nomServiceAR = nomServiceAR;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }

    public String getNomHopital() {
        return nomHopital;
    }

    public void setNomHopital(String nomHopital) {
        this.nomHopital = nomHopital;
    }

    // Optionnel: toString() pour le débogage
    @Override
    public String toString() {
        return "Service{" +
               "id=" + id +
               ", nomServiceFR='" + nomServiceFR + '\'' +
               ", type='" + type + '\'' +
               ", idHopital=" + idHopital +
               (nomHopital != null ? ", nomHopital='" + nomHopital + '\'' : "") +
               '}';
    }
}