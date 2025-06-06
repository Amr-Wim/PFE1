<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord - CarePath</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #e0f2fe;
        }
        
        body {
            background-color: var(--bg-light);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        .dashboard-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            height: 100%;
            border: none;
        }
        
        .dashboard-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
        }
        
        .card-icon {
            font-size: 2.5rem;
            color: var(--main-blue);
            margin-bottom: 15px;
        }
        
        .section-title {
            color: var(--main-blue);
            font-weight: 600;
            margin-bottom: 15px;
        }
        
        .btn-dashboard {
            background-color: var(--accent-red); /* Changé en rouge */
            border: none;
            padding: 8px 20px;
            border-radius: 8px;
            transition: all 0.3s ease;
            color: white;
        }
        
        .btn-dashboard:hover {
            background-color: #6a001c; /* Nuance plus foncée pour le hover */
            transform: translateY(-2px);
            color: white;
        }
        .welcome-message {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        header {
      background-color: var(--main-blue);
      padding: 20px 60px;
      display: flex;
      align-items: center;
      border-bottom: 4px solid var(--accent-red);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
      color: white;
    }
        footer {
      background-color: var(--main-blue);
      color: #ffffff;
      text-align: center;
      padding: 18px 30px;
      font-size: 14px;
      border-top: 4px solid var(--accent-red);
      font-weight: 500;
      letter-spacing: 0.5px;
    }
    </style>
</head>
<body>
    <!-- Header (identique au dossier médical) -->
    <header style="background-color: #1e3a5f;">
        <div class="container-fluid">
            <div class="d-flex align-items-center justify-content-between py-2">
                <!-- Logo et titre -->
                <div class="d-flex align-items-center gap-3">
                    <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                    <span class="fs-3 fw-bold text-white text-uppercase" style="letter-spacing: 1px; text-shadow: 0 2px 3px rgba(0,0,0,0.3);">
                        CarePath
                    </span>
                </div>
                
                <!-- Menu et infos utilisateur -->
                <div class="d-flex align-items-center gap-4">
                    <!-- Navigation principale -->
                    <nav class="d-flex align-items-center gap-3 me-4">
                        <a class="nav-link text-white active hover-effect" href="patient_dashboard.jsp">
                            <i class="fas fa-tachometer-alt me-1"></i>
                            <span class="d-none d-lg-inline">Tableau de bord</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="fiche_patient.jsp">
                            <i class="fas fa-user me-1"></i>
                            <span class="d-none d-lg-inline">Informations</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="dossier_medical.jsp">
                            <i class="fas fa-file-medical me-1"></i>
                            <span class="d-none d-lg-inline">Dossier médical</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="patient/hospitalisation">
                            <i class="fas fa-procedures me-1"></i>
                            <span class="d-none d-lg-inline">Hospitalisation</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="analyses.jsp">
                            <i class="fas fa-flask me-1"></i>
                            <span class="d-none d-lg-inline">Analyses</span>
                        </a>
                    </nav>
                    
                   
                        <!-- Bouton Déconnexion -->
                        <form action="Logout" method="post" class="m-0">
                             <button type="submit" class="btn btn-danger btn-sm">
                        <i class="fas fa-sign-out-alt me-1"></i> Déconnexion
                                
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <main class="flex-grow-1 py-5">
        <div class="container">
            <!-- Message de bienvenue -->
            <div class="welcome-message text-center mb-5">
                <h1 class="mb-3" style="color: var(--main-blue);">
                    <i class="fas fa-user-circle me-2"></i>Bienvenue, ${utilisateur.prenom}
                </h1>
                <p class="lead">Accédez rapidement à toutes les fonctionnalités de votre espace patient</p>
            </div>
            
            <!-- Cartes de fonctionnalités -->
            <div class="row g-4">
                <!-- Informations personnelles -->
                <div class="col-md-6">
                    <div class="dashboard-card p-4 h-100">
                        <div class="card-body text-center">
                            <i class="fas fa-user-circle card-icon"></i>
                            <h3 class="section-title">Informations personnelles</h3>
                            <p class="card-text">Consultez et mettez à jour vos coordonnées</p>
                            <a href="fiche_patient.jsp" class="btn btn-dashboard mt-3">
                                <i class="fas fa-edit me-2"></i>Gérer mon profil
                            </a>
                        </div>
                    </div>
                </div>
                
                <!-- Dossier médical -->
                <div class="col-md-6">
                    <div class="dashboard-card p-4 h-100">
                        <div class="card-body text-center">
                            <i class="fas fa-file-medical card-icon"></i>
                            <h3 class="section-title">Dossier médical</h3>
                            <p class="card-text">Accédez à votre historique médical complet</p>
                            <a href="dossier_medical.jsp" class="btn btn-dashboard mt-3">
                                <i class="fas fa-folder-open me-2"></i>Consulter
                            </a>
                        </div>
                    </div>
                </div>
                
                <!-- Hospitalisation -->
                <div class="col-md-6">
                    <div class="dashboard-card p-4 h-100">
                        <div class="card-body text-center">
                            <i class="fas fa-procedures card-icon"></i>
                            <h3 class="section-title">Hospitalisation</h3>
                            <p class="card-text">Informations sur votre séjour hospitalier</p>
                            <a href="hospitalisation.jsp" class="btn btn-dashboard mt-3">
                                <i class="fas fa-info-circle me-2"></i>Détails
                            </a>
                        </div>
                    </div>
                </div>
                
                <!-- Analyses médicales -->
                <div class="col-md-6">
                    <div class="dashboard-card p-4 h-100">
                        <div class="card-body text-center">
                            <i class="fas fa-flask card-icon"></i>
                            <h3 class="section-title">Analyses médicales</h3>
                            <p class="card-text">Date de retrait de vos analyses</p>
                            <a href="analyses.jsp" class="btn btn-dashboard mt-3">
                                <i class="fas fa-clock me-2"></i>Consulter
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- Footer (identique au dossier médical) -->
    <footer>
        &copy; 2025 - CarePath. Tous droits réservés.
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