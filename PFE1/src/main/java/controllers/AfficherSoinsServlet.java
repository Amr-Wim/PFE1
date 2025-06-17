package controllers;

import dao.SoinDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Soin;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/AfficherSoinsServlet") // Assurez-vous que le nom est correct
public class AfficherSoinsServlet extends HttpServlet {
    private SoinDao soinDao;

    @Override
    public void init() {
        this.soinDao = new SoinDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String idPatientParam = request.getParameter("idPatient");
        
        if (idPatientParam != null && !idPatientParam.trim().isEmpty()) {
            try {
                int idPatient = Integer.parseInt(idPatientParam);
                
                // Appel au DAO corrigé
                List<Soin> soins = soinDao.findByPatientId(idPatient);
                
                request.setAttribute("soins", soins);
                request.setAttribute("idPatientRecherche", idPatient);
                
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "L'ID du patient doit être un nombre.");
                request.setAttribute("soins", Collections.emptyList()); // Envoyer une liste vide
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Erreur lors de la récupération des soins.");
                request.setAttribute("soins", Collections.emptyList());
            }
        
        }
        
        // On forward toujours vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/SoinsInfirmiers.jsp");
        dispatcher.forward(request, response);
    }
}