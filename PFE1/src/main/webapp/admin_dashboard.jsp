<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Admin</title>
    
    <!-- Dépendances -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <!-- Style personnalisé -->
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #f4f7f9;
            --text-dark: #2c3e50;
            --text-muted: #7f8c8d;
            --color-occupied: #dc3545; /* Rouge Bootstrap */
            --color-free: #198754;     /* Vert Bootstrap */
        }
        
        body {
            background-color: #e0f2fe;
            font-family: 'Segoe UI', sans-serif;
            color: var(--text-dark);
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        main { flex: 1; }

        .stat-card {
            background-color: white;
            border: 1px solid #e9ecef;
            border-radius: 0.75rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            transition: all 0.3s ease;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        }

        .stat-card .card-body {
            display: flex;
            align-items: center;
            gap: 1.5rem;
        }

        .stat-icon {
            font-size: 2.5rem;
            width: 60px;
            height: 60px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            flex-shrink: 0;
        }

        .icon-occupied { background-color: var(--color-occupied); color: white; }
        .icon-free { background-color: var(--color-free); color: white; }
        .icon-total { background-color: var(--main-blue); color: white; }

        .stat-content .stat-value {
            font-size: 2.25rem;
            font-weight: 700;
            line-height: 1.1;
        }

        .stat-content .stat-label {
            font-size: 1rem;
            color: var(--text-muted);
        }
        
        .chart-container {
            background-color: white;
            padding: 2rem;
            border-radius: 0.75rem;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            border: 1px solid #e9ecef;
        }
    </style>
</head>

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
        
      <div class="d-flex gap-3">
            <a class="nav-link active text-white" href="${pageContext.request.contextPath}/adminDashboard">Tableau de bord</a>
            <a class="nav-link text-white" href="creerUtilisateur.jsp">Créer utilisateur</a>
            <a class="nav-link text-white" href="changerLit">Modifier les lits </a>
            
             <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
    </div>
</header>





<main class="container py-5">
        <h2 class="mb-4 fw-bold" style="color: var(--main-blue);">Tableau de Bord Administratif</h2>

        <%-- CORRECTION : On utilise requestScope pour être explicite --%>
        <c:set var="occupied" value="${requestScope.occupiedLits}" />
        <c:set var="free" value="${requestScope.freeLits}" />
        <c:set var="total" value="${requestScope.totalLits}" />
        
        <%-- Calcul du pourcentage (on évite la division par zéro) --%>
        <c:set var="occupationPercentage" value="${total > 0 ? (occupied * 100) / total : 0}" />
        <fmt:formatNumber var="formattedPercentage" value="${occupationPercentage}" maxFractionDigits="1" />

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Cartes de Statistiques -->
        <div class="row g-4 mb-5">
            <div class="col-lg-4 col-md-6">
                <div class="stat-card">
                    <div class="card-body">
                        <div class="stat-icon icon-occupied"><i class="fas fa-bed"></i></div>
                        <div class="stat-content">
                            <div class="stat-value">${occupied}</div>
                            <div class="stat-label">Lits Occupés</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6">
                <div class="stat-card">
                    <div class="card-body">
                        <div class="stat-icon icon-free"><i class="fas fa-check-circle"></i></div>
                        <div class="stat-content">
                            <div class="stat-value">${free}</div>
                            <div class="stat-label">Lits Libres</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-12">
                <div class="stat-card">
                    <div class="card-body">
                        <div class="stat-icon icon-total"><i class="fas fa-hospital"></i></div>
                        <div class="stat-content">
                            <div class="stat-value">${total}</div>
                            <div class="stat-label">Capacité Totale</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Graphiques et Visualisations -->
        <div class="row g-4">
            <div class="col-lg-7">
                <div class="chart-container">
                    <h5 class="mb-3 fw-bold">Occupation des Lits (Barres)</h5>
                    <canvas id="barChart" height="150"></canvas>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="chart-container d-flex flex-column h-100">
                    <h5 class="mb-3 fw-bold">Taux d'Occupation</h5>
                    <div class="text-center my-auto">
                        <div style="position: relative; display: inline-block;">
                            <canvas id="doughnutChart" width="200" height="200"></canvas>
                            <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
                                <span class="h2 fw-bold">${formattedPercentage}%</span>
                            </div>
                        </div>
                        <p class="mt-3 text-muted">
                            ${occupied} lits occupés sur un total de ${total}.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Votre Footer est bon, on le garde -->
    <footer class="mt-auto">...</footer>

<script>
    // Utilisation de requestScope pour être certain de récupérer les bonnes variables
    const occupiedCount = parseInt('${requestScope.occupiedLits}') || 0;
    const freeCount = parseInt('${requestScope.freeLits}') || 0;
    const totalCount = parseInt('${requestScope.totalLits}') || 0;

    // Graphique en Barres
    const barCtx = document.getElementById('barChart')?.getContext('2d');
    if (barCtx) {
        new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: ['Lits occupés', 'Lits libres'],
                datasets: [{
                    label: 'Nombre de Lits',
                    data: [occupiedCount, freeCount],
                    backgroundColor: ['rgba(220, 53, 69, 0.7)', 'rgba(25, 135, 84, 0.7)'],
                    borderColor: ['rgb(220, 53, 69)', 'rgb(25, 135, 84)'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
            }
        });
    }

    // Graphique en Donut
    const doughnutCtx = document.getElementById('doughnutChart')?.getContext('2d');
    if (doughnutCtx) {
        new Chart(doughnutCtx, {
            type: 'doughnut',
            data: {
                labels: ['Occupés', 'Libres'],
                datasets: [{
                    data: [occupiedCount, freeCount],
                    backgroundColor: ['var(--color-occupied)', 'var(--color-free)'],
                    borderColor: 'white',
                    borderWidth: 4,
                    hoverOffset: 4
                }]
            },
            options: {
                responsive: true,
                cutout: '75%', // Rend le donut plus fin
                plugins: { legend: { display: false } },
                animation: { animateScale: true, animateRotate: true }
            }
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>