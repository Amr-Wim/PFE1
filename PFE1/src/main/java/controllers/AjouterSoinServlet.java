package controllers;

import dao.SoinDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Infirmier;
import model.Soin;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/AjouterSoinServlet")
public class AjouterSoinServlet extends HttpServlet {
    private SoinDao soinDao;

    @Override
    public void init() {
        this.soinDao = new SoinDao();
    }
    
    /**
     * NOUVELLE MÉTHODE - Gère l'affichage du formulaire.
     * C'est la porte d'entrée lorsque l'on clique sur un lien.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // --- ESPION N°2 : VÉRIFICATION D'ENTRÉE ---
        System.out.println("\nAJOUTER_SOIN_SERVLET (doGet): Tentative d'affichage du formulaire.");
        if (session != null) {
            System.out.println("AJOUTER_SOIN_SERVLET (doGet): Session trouvée. ID de session: " + session.getId());
            if (session.getAttribute("infirmier") != null) {
                System.out.println("AJOUTER_SOIN_SERVLET (doGet): L'attribut 'infirmier' est PRÉSENT. Affichage de la page.");
            } else {
                System.err.println("AJOUTER_SOIN_SERVLET (doGet): ERREUR - L'attribut 'infirmier' est MANQUANT dans la session !");
            }
        } else {
            System.err.println("AJOUTER_SOIN_SERVLET (doGet): ERREUR - La session est NULL !");
        }

        // Le code de protection original
        if (session == null || session.getAttribute("infirmier") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=accesRefuse");
            return;
        }
        
        request.getRequestDispatcher("/ajoutersoin.jsp").forward(request, response);
    }
    
    /**
     * Gère la soumission du formulaire (cette méthode était déjà correcte).
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("infirmier") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=accesRefuse");
            return;
        }
        Infirmier infirmierConnecte = (Infirmier) session.getAttribute("infirmier");
        
        try {
            Soin nouveauSoin = new Soin();
            nouveauSoin.setIdPatient(Integer.parseInt(request.getParameter("idPatient")));
            nouveauSoin.setIdInfirmier(infirmierConnecte.getId());
            nouveauSoin.setType(request.getParameter("type"));
            nouveauSoin.setDescription(request.getParameter("description"));
            nouveauSoin.setDateSoin(Date.valueOf(request.getParameter("date_soin")));

            soinDao.ajouterSoin(nouveauSoin);
            
            session.setAttribute("successMessage", "Le soin a été ajouté avec succès !");
            // On redirige vers la page de consultation pour voir le résultat
            response.sendRedirect(request.getContextPath() + "/AfficherSoinsServlet?idPatient=" + nouveauSoin.getIdPatient());
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Erreur lors de l'ajout du soin : " + e.getMessage());
            // En cas d'erreur, on ré-affiche le formulaire
            request.getRequestDispatcher("/ajoutersoin.jsp").forward(request, response);
        }
    }
}