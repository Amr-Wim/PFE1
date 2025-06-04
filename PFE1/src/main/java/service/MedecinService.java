package service;

import dao.MedecinDAO;
import model.Medecin;

public class MedecinService {
    private MedecinDAO medecinDAO = new MedecinDAO();
    
    public Medecin getMedecinById(int id) {
        try {
            return medecinDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}