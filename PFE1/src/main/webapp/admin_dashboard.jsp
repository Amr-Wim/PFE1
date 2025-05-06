<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Admin</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
       <link rel="stylesheet" href="css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  
     <style>
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
        
        
        .stat-card {
  background-color: white; 
  color: #1c3d5a;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  min-height: 150px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: scale(1.02);
}

.stat-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.stat-desc {
  font-size: 16px;
  text-align: center;
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
            <a class="nav-link active text-white" href="#">Tableau de bord</a>
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
    <h2 class="mb-4">Tableau de bord Administratif</h2>
    
    
    <div class="row" style="gap: 30px; justify-content: center; margin-bottom: 50px;">
  <div class="col-md-4 stat-card">
    <div class="stat-title">Lits Occupés</div>
    <div class="stat-desc">Nombre total de lits occupés dans l'hôpital.</div>
  </div>

  <div class="col-md-4 stat-card">
    <div class="stat-title">Lits Libres</div>
    <div class="stat-desc">Nombre total de lits disponibles pour les patients.</div>
  </div>
</div>
    

    
    <!-- Statistiques d'occupation -->
    <div class="stat-container">
        <h3>📊 Statistiques d'occupation des lits</h3>
        <canvas id="occupationChart" width="400" height="200"></canvas>
    </div>
     
</div>
<footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>

<script>
    var ctx = document.getElementById('occupationChart').getContext('2d');
    var occupationChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Lits occupés', 'Lits libres'],
            datasets: [{
                label: 'Statistiques d\'occupation des lits',
                data: [${occupiedLits}, ${freeLits}], // Dynamique des données récupérées
                backgroundColor: ['#007bff', '#28a745'],
                borderColor: ['#0056b3', '#218838'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>


</body>
</html>
