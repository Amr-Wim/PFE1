package controllers;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreerUser")
public class CreerUserServlet extends HttpServlet {
    private UtilisateurDAO userDAO;

    

    @Override
    public void init() throws ServletException {
        // Initialisation simple avec votre Database
        userDAO = new UtilisateurDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Affiche le formulaire d'inscription
        request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Récupération des paramètres
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        String role = request.getParameter("role");
        
        // Initialisation des paramètres optionnels
        String dateNaissance = "";
        String adresse = "";
        String specialite = "";
        String service = "";
        
        // Récupération des champs spécifiques
        if ("patient".equals(role)) {
            dateNaissance = request.getParameter("date_naissance");
            adresse = request.getParameter("adresse");
        } else if ("medecin".equals(role)) {
            specialite = request.getParameter("specialite");
        } else if ("infirmier".equals(role)) {
            service = request.getParameter("service");
        }
        
        // Vérifications préalables
        if (userDAO.loginExiste(login)) {
            request.setAttribute("error", "Ce nom d'utilisateur est déjà pris");
            doGet(request, response);
            return;
        }
        
        if (userDAO.emailExiste(email)) {
            request.setAttribute("error", "Cet email est déjà utilisé");
            doGet(request, response);
            return;
        }
        
        // Hashage du mot de passe (exemple avec BCrypt)
        String motDePasseHash = BCrypt.hashpw(mdp, BCrypt.gensalt());
        
        // Appel au DAO
        boolean resultat = userDAO.ajouterUtilisateur(
            nom, prenom, email, login, motDePasseHash, 
            role, dateNaissance, adresse, specialite, service
        );
        
        if (resultat) {
            response.sendRedirect("admin_dashboard.jsp");
        } else {
            request.setAttribute("error", "Une erreur est survenue lors de la création du compte");
            doGet(request, response);
        }
    }
}