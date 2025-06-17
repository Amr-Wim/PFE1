package controllers;

import dao.ObservationDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Observation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/RechercherObservationServlet")
public class RechercherObservationServlet extends HttpServlet {
    private ObservationDao observationDao;

    @Override
    public void init() {
        this.observationDao = new ObservationDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String idPatientParam = request.getParameter("idPatient");

        if (idPatientParam != null && !idPatientParam.trim().isEmpty()) {
            try {
                int idPatient = Integer.parseInt(idPatientParam);
                List<Observation> observations = observationDao.findByPatientId(idPatient);
                
                request.setAttribute("observations", observations);
                request.setAttribute("idPatientRecherche", idPatient);
                
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "L'ID du patient doit être un nombre.");
                request.setAttribute("observations", Collections.emptyList());
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Erreur lors de la récupération des observations.");
                request.setAttribute("observations", Collections.emptyList());
            }
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/observations.jsp");
        dispatcher.forward(request, response);
    }
}