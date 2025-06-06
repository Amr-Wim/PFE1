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
            <a class="nav-link active text-white" href="${pageContext.request.contextPath}/adminDashboard">Tableau de bord</a>
            <a class="nav-link text-white" href="creerUtilisateur.jsp">Créer utilisateur</a>
            <a class="nav-link text-white" href="changerLit">Affectation des chambres</a>
           
            
             <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
    </div>
</header>





<div class="container mt-5">
        <h2 class="mb-4">Tableau de bord Administratif</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="row" style="gap: 30px; justify-content: center; margin-bottom: 50px;">
          <div class="col-md-3 stat-card">
            <div class="stat-title">Lits Occupés</div>
            <h2><c:out value="${occupiedLits}"/></h2>
            <div class="stat-desc">Nombre total de lits actuellement occupés.</div>
          </div>

          <div class="col-md-3 stat-card">
            <div class="stat-title">Lits Libres</div>
             <h2><c:out value="${freeLits}"/></h2>
            <div class="stat-desc">Nombre total de lits disponibles.</div>
          </div>
          
          <div class="col-md-3 stat-card">
            <div class="stat-title">Total Lits</div>
            <h2><c:out value="${totalLits}"/></h2>
            <div class="stat-desc">Capacité totale en lits de l'établissement.</div>
          </div>
        </div>

        <div class="stat-container">
            <h3>📊 Occupation des Lits</h3>
            <canvas id="occupationChart" width="400" height="150"></canvas> <%-- Réduit un peu la hauteur --%>
        </div>
    </div>

    <footer class="mt-auto py-3" style="background-color: #1e3a5f; color:white; border-top: 4px solid #7e0021;">
         <div class="container text-center">
            © ${java.time.Year.now()} - CarePath. Tous droits réservés.
         </div>
    </footer>

<script>
    // S'assurer que les variables sont bien des nombres pour Chart.js
    var occupiedCount = parseInt('${occupiedLits}') || 0; // || 0 pour éviter NaN si la valeur est vide/invalide
    var freeCount = parseInt('${freeLits}') || 0;

    if (document.getElementById('occupationChart')) {
        var ctx = document.getElementById('occupationChart').getContext('2d');
        var occupationChart = new Chart(ctx, {
            type: 'bar', // 'pie' ou 'doughnut' sont aussi de bons choix pour ce type de données
            data: {
                labels: ['Lits occupés', 'Lits libres'],
                datasets: [{
                    label: 'Nombre de Lits',
                    data: [occupiedCount, freeCount],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.6)', // Rouge pour occupés
                        'rgba(75, 192, 192, 0.6)'  // Vert/Bleu pour libres
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true, // Peut être mis à false si tu veux contrôler hauteur/largeur plus librement
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1 // Pour s'assurer que l'échelle Y n'a que des entiers si les nombres sont petits
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: true, // Afficher la légende
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Répartition des Lits'
                    }
                }
            }
        });
    } else {
        console.error("L'élément Canvas 'occupationChart' n'a pas été trouvé.");
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>