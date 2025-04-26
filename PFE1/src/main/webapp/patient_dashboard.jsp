<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - Patient | CarePath</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="css/styles.css">
    <style>
        :root {
            --primary-blue: #1e3a5f;
            --secondary-red: #7e0021;
            --light-blue: #e0f2fe;
        }
        
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }
        
        .dashboard-card {
            transition: all 0.3s ease;
            border-radius: 10px;
            border-left: 4px solid var(--primary-blue);
            height: 100%;
        }
        
        .dashboard-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
        }
        
        .card-urgent {
            border-left-color: var(--secondary-red);
            background-color: #fff5f5;
        }
        
        .appointment-badge {
            position: absolute;
            top: -10px;
            right: -10px;
        }
        
        .nav-link {
            position: relative;
        }
        
        .nav-link.active:after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 100%;
            height: 3px;
            background: white;
        }
        
        footer {
            background-color: var(--primary-blue);
            color: white;
            border-top: 4px solid var(--secondary-red);
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
         <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
        
 </div>
   </header>

    <div class="container py-5">
        <!-- Section Prochain Rendez-vous -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm card-urgent">
                    <div class="card-body py-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h4 class="mb-1"><i class="fas fa-bell me-2 text-danger"></i> Votre prochain rendez-vous</h4>
                                <p class="mb-0"><strong>Demain à 14h30</strong> - Cardiologie avec Dr. Dupont</p>
                                <small class="text-muted">Bâtiment A, Niveau 2 - Durée estimée: 30min</small>
                            </div>
                            <div>
                                <button class="btn btn-sm btn-outline-danger me-2"><i class="fas fa-phone-alt me-1"></i> Reporter</button>
                                <button class="btn btn-sm btn-primary"><i class="fas fa-map-marker-alt me-1"></i> Itinéraire</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Dashboard Grid -->
        <h2 class="mb-4 text-center text-primary">Tableau de Bord Patient</h2>
        
        <div class="row g-4">
            <!-- Colonne 1 -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card shadow-sm h-100">
                    <div class="card-body text-center">
                        <i class="fas fa-user-circle fa-3x mb-3 text-primary"></i>
                        <h5 class="card-title">Informations personnelles</h5>
                        <p class="card-text text-muted small">Consultez et mettez à jour vos coordonnées</p>
                        <a href="fiche_patient.jsp" class="btn btn-outline-primary stretched-link">
                            <i class="fas fa-edit me-1"></i> Gérer mon profil
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- Colonne 2 -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card shadow-sm h-100">
                    <div class="card-body text-center">
                        <i class="fas fa-calendar-alt fa-3x mb-3 text-success"></i>
                        <h5 class="card-title">Mes rendez-vous</h5>
                        <p class="card-text text-muted small">Gérez vos consultations à venir</p>
                        <a href="rendezvous.jsp" class="btn btn-outline-success stretched-link">
                            <i class="fas fa-search me-1"></i> Consulter
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- Colonne 3 -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card shadow-sm h-100 position-relative">
                    <span class="badge bg-danger appointment-badge">3 nouveaux</span>
                    <div class="card-body text-center">
                        <i class="fas fa-file-medical fa-3x mb-3 text-danger"></i>
                        <h5 class="card-title">Résultats d'analyses</h5>
                        <p class="card-text text-muted small">Accédez à vos derniers examens</p>
                        <a href="resultats_analyses.jsp" class="btn btn-outline-danger stretched-link">
                            <i class="fas fa-download me-1"></i> Télécharger
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- Colonne 4 -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card shadow-sm h-100">
                    <div class="card-body text-center">
                        <i class="fas fa-procedures fa-3x mb-3 text-info"></i>
                        <h5 class="card-title">Mon parcours médical</h5>
                        <p class="card-text text-muted small">Suivi complet de votre traitement</p>
                        <a href="parcours_patient.jsp" class="btn btn-outline-info stretched-link">
                            <i class="fas fa-history me-1"></i> Voir l'historique
                        </a>
                    </div>
                </div>
            </div>
           
            
            <!-- Colonne 6 -->
            <div class="col-md-6 col-lg-4">
                <div class="card dashboard-card shadow-sm h-100">
                    <div class="card-body text-center">
                        <i class="fas fa-door-open fa-3x mb-3 text-secondary"></i>
                        <h5 class="card-title">Bon de sortie</h5>
                        <p class="card-text text-muted small">Téléchargez votre autorisation</p>
                        <a href="bon_sortie.jsp" class="btn btn-outline-secondary stretched-link">
                            <i class="fas fa-file-pdf me-1"></i> Générer PDF
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        
    </div>

    <footer class="py-3 mt-5">
        <div class="container text-center">
            <p class="mb-0">&copy; 2025 CarePath - Tous droits réservés</p>
            <small class="text-white-50">Plateforme sécurisée de suivi médical</small>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Active le tooltip partout
        document.addEventListener('DOMContentLoaded', function() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl)
            });
        });
    </script>
</body>
</html>