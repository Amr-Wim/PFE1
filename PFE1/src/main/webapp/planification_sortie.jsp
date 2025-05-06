<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Planification de sortie</title>
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
    <h2 class="mb-4">Planification de sortie des patients</h2>

    <!-- Formulaire de planification de sortie -->
    <form action="sortieAction.jsp" method="post">
        <div class="mb-3">
            <label for="patient" class="form-label">Sélectionner un patient</label>
            <select class="form-select" id="patient" name="patientId">
                <option value="1">Patient 1</option>
                <option value="2">Patient 2</option>
                <!-- Dynamique selon les patients dans la BD -->
            </select>
        </div>

        <div class="mb-3">
            <label for="dateSortie" class="form-label">Date de sortie prévue</label>
            <input type="date" class="form-control" id="dateSortie" name="dateSortie" required>
        </div>

        <div class="mb-3">
            <label for="motif" class="form-label">Motif de la sortie</label>
            <textarea class="form-control" id="motif" name="motif" rows="3" required></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Planifier la sortie</button>
    </form>
</div>
<footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>
</html>
