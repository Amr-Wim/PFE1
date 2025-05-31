package controllers;

import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
// import java.math.BigDecimal; // Pas nécessaire ici si la conversion est gérée dans le DAO

//L'import correct est :
import org.mindrot.jbcrypt.BCrypt;

import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/CreerUser")
public class CreerUserServlet extends HttpServlet {
    private UtilisateurDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UtilisateurDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String cin = request.getParameter("cin");
        String sexe = request.getParameter("sexe"); // Récupérer le Sexe
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        String role = "patient"; 
        
        String dateNaissanceStr = request.getParameter("date_naissance");
        String adresse = request.getParameter("adresse");
        String tailleStr = request.getParameter("taille");
        String poidsStr = request.getParameter("poids");
        String groupeSanguin = request.getParameter("groupe_sanguin");
        String assuranceMedicale = request.getParameter("assurance_medicale");
        String numeroAssurance = request.getParameter("numero_assurance");
        
        if (cin != null && !cin.trim().isEmpty() && userDAO.cinExiste(cin)) {
            request.setAttribute("error", "Ce numéro CIN est déjà utilisé.");
            request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
            return;
        }

        if (userDAO.loginExiste(login)) {
            request.setAttribute("error", "Ce nom d'utilisateur est déjà pris");
            request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
            return;
        }
        
        if (userDAO.emailExiste(email)) {
            request.setAttribute("error", "Cet email est déjà utilisé");
            request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
            return;
        }
        
        String motDePasseHash = BCrypt.hashpw(mdp, BCrypt.gensalt());
        
        boolean resultat = userDAO.ajouterUtilisateur(
            nom, prenom, email, cin, sexe, login, motDePasseHash, // Passer le Sexe au DAO
            dateNaissanceStr, 
            adresse,
            tailleStr,        
            poidsStr,         
            groupeSanguin,    
            assuranceMedicale,
            numeroAssurance   
        );
        
        if (resultat) {
            request.setAttribute("successMessage", "Le compte patient a été créé avec succès !");
            request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Une erreur est survenue lors de la création du compte patient.");
             request.getRequestDispatcher("creerUtilisateur.jsp").forward(request, response);
        }
    }
}