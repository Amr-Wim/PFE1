<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de Bord - Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .role-card {
            transition: transform 0.2s;
        }
        .role-card:hover {
            transform: scale(1.05);
        }
        
        
        .stat-container {
            margin-top: 30px;
        }
        .card-header {
            background-color: #007bff;
            color: white;
        }
        .card-body {
            padding: 30px;
        }
        
    </style>
</head>
<body>
 <!-- Header -->
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
            <a class="nav-link active text-white" href="patient_dashboard.jsp">Tableau de bord</a>
            <a class="nav-link text-white" href="fiche_patient.jsp">Informations personnelles</a>
            <a class="nav-link text-white" href="rendezvous.jsp">Rendez vous</a>
            <a class="nav-link text-white" href="parcours_patient.jsp">Parcours</a>
                        <a class="nav-link text-white" href="bon_sortie.jsp">Bon sortie</a>
                                    <a class="nav-link text-white" href="resultats_analyses.jsp">Résultats d'éxamens</a>
        </div>
 </div>
   </header>
 

<div class="container text-center mt-5">
    <h2 class="mb-4"> Tableau de Bord - Patient</h2>


    <div class="row justify-content-center">
    
    <div class="col-md-4 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">informations personnelles </h5>
                    <a href="fiche_patient.jsp" class="btn btn-outline-success">consulter</a>
                </div>
            </div>
        </div>
        
                <div class="col-md-4 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Consulter rendez vous</h5>
                    <a href="rendezvous.jsp" class="btn btn-outline-danger">consulter</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-4 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">📋 Consulter mon parcours</h5>
                    <a href="parcours_patient.jsp" class="btn btn-outline-primary">Voir</a>
                </div>
            </div>
        </div>

        <div class="col-md-4 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">🔬 Résultats d’analyses</h5>
                    <a href="resultats_analyses.jsp" class="btn btn-outline-success">Voir</a>
                </div>
            </div>
        </div>

        <div class="col-md-4 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">📄 Télécharger mon bon de sortie</h5>
                    <a href="bon_sortie.jsp" class="btn btn-outline-danger">Télécharger</a>
                </div>
            </div>
        </div>
        
        
    </div>

    <a href="index.jsp" class="btn btn-secondary mt-4">⬅️ Retour à l'accueil</a>
</div>

<footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>

</body>
</html>
