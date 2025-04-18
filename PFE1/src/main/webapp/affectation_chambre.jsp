<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <a class="nav-link text-white" href="affectation_chambre.jsp">Affectation des chambres</a>
            <a class="nav-link text-white" href="planification_sortie.jsp">Planification des sorties</a>
        </div>
    </div>
</header>

<div class="container mt-5">
    <h2 class="mb-4">Affectation des chambres aux patients</h2>

    <!-- Table des chambres -->
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
                <th>#</th>
                <th>Numéro de Chambre</th>
                <th>Service</th>
                <th>Nombre de lits</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>101</td>
                <td>Cardiologie</td>
                <td>2</td>
                <td><button class="btn btn-primary">Affecter</button></td>
            </tr>
            <tr>
                <td>2</td>
                <td>102</td>
                <td>Neurologie</td>
                <td>3</td>
                <td><button class="btn btn-primary">Affecter</button></td>
            </tr>
            <!-- Dynamique selon les chambres disponibles -->
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
