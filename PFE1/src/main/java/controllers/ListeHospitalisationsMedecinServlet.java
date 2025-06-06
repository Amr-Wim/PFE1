package controllers; // Ou controllers.medecin

import dao.HospitalisationDAO;
import model.Hospitalisation;
import model.Medecin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors; // Pour le filtrage

@WebServlet("/medecin/mesHospitalisations") // URL pour cette page
public class ListeHospitalisationsMedecinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HospitalisationDAO hospitalisationDAO;

    @Override
    public void init() throws ServletException {
        hospitalisationDAO = new HospitalisationDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("medecin") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Medecin medecin = (Medecin) session.getAttribute("medecin");
        String termeRecherche = request.getParameter("recherche"); // Pour le champ de recherche

        try {
            List<Hospitalisation> hospitalisations = hospitalisationDAO.getHospitalisationsEnCoursParMedecin(medecin.getId());

            if (termeRecherche != null && !termeRecherche.trim().isEmpty()) {
                String rechercheLower = termeRecherche.trim().toLowerCase();
                hospitalisations = hospitalisations.stream()
                    .filter(h -> (h.getPatientNom() != null && h.getPatientNom().toLowerCase().contains(rechercheLower)) ||
                                 (h.getPatientPrenom() != null && h.getPatientPrenom().toLowerCase().contains(rechercheLower)) ||
                                 (h.getPatientCin() != null && h.getPatientCin().toLowerCase().contains(rechercheLower)) || // AJOUT DE LA RECHERCHE SUR CIN
                                 String.valueOf(h.getIdPatient()).contains(rechercheLower) ) // Garder la recherche par ID aussi
                    .collect(Collectors.toList());
                request.setAttribute("termeRecherche", termeRecherche);
        
            }

            request.setAttribute("hospitalisationsList", hospitalisations);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération de la liste des hospitalisations: " + e.getMessage());
        }
        // Chemin vers ta nouvelle JSP
        request.getRequestDispatcher("/liste_hospitalisations.jsp").forward(request, response);
    }
}