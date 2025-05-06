<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.Chambre, model.Lit,model.Patient" %>
<%@ page import="dao.LitDAO" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affectation des chambres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header style="background-color: #1e3a5f; padding: 20px 60px; border-bottom: 4px solid #7e0021; box-shadow: 0 4px 12px rgba(0,0,0,0.2); color: white;">
    <div class="container-fluid d-flex align-items-center justify-content-between">
        <!-- Logo et titre -->
        <div class="d-flex align-items-center gap-3">
            <img src="image/image2.png" alt="Logo" style="height: 50px;">
            <span class="fs-3 fw-bold text-uppercase" style="text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);">
                CarePath
            </span>
        </div>
        
        <!-- Liens de navigation affichés côte à côte -->
        <div class="d-flex gap-3">
            <a class="nav-link active text-white" href="admin_dashboard.jsp">Tableau de bord</a>
            <a class="nav-link text-white" href="creerUtilisateur.jsp">Créer utilisateur</a>
            <a class="nav-link text-white" href="Affectation">Affectation des chambres</a>
            <a class="nav-link text-white" href="planification_sortie.jsp">Planification des sorties</a>
             <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
    </div>
</header>

<div class="container mt-5">
    <h2 class="mb-4">Affectation des chambres aux patients</h2>

    <%-- Messages d'erreur/succès --%>
    <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    
    
    

    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <th>Numéro de Chambre</th>
                <th>Service</th>
                <th>Lits disponibles</th>
                <th>Nombre de lits disponibles</th>
                <th>Affecter un lit</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<Chambre> chambres = (List<Chambre>) request.getAttribute("chambres");
            List<Patient> patients = (List<Patient>) request.getAttribute("patients");
            
            if (chambres != null) {
                for (Chambre chambre : chambres) { 
                    List<Lit> lits = chambre.getLits();
                    List<Lit> litsDisponibles = new ArrayList<>();
                    if (lits != null) {
                        for (Lit lit : lits) {
                            if (lit.isEstDisponible()) {
                                litsDisponibles.add(lit);
                            }
                        }
                    }
            %>
            <tr>
                <td><%= chambre.getNumero() %></td>
                <td><%= chambre.getService() %></td>
                <td>
                    <% for (Lit lit : litsDisponibles) { %>
                        <span class="badge bg-success">Lit <%= lit.getNumeroLit() %></span>
                    <% } %>
                </td>
                <td><%= chambre.getNombreLitsDisponibles() %></td>
                <td>
                    <% if (!litsDisponibles.isEmpty()) { %>
                        <form action="Affectation" method="post" class="d-flex align-items-center">
                            <input type="hidden" name="idLit" value="<%= litsDisponibles.get(0).getId() %>">
                            
                            <select class="form-select me-2" name="idPatient" required>
                                <option value="">Sélectionner un patient</option>
                                <% if (patients != null) {
                                    for (Patient patient : patients) { %>
                                        <option value="<%= patient.getId() %>">
                                            <%= patient.getNom() %> <%= patient.getPrenom() %> (ID: <%= patient.getId() %>)
                                        </option>
                                <%  }
                                   } %>
                            </select>
                            
                            <button type="submit" class="btn btn-primary">Affecter</button>
                        </form>
                    <% } else { %>
                        <span class="text-danger">Aucun lit disponible</span>
                    <% } %>
                </td>
            </tr>
            <%  } 
               } else { %>
            <tr>
                <td colspan="5" class="text-center">Aucune chambre trouvée</td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<footer>
    &copy; 2025 - CarePath. Tous droits réservés.
</footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>
</html>