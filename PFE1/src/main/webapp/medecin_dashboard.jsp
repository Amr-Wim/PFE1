<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Tableau de bord - Médecin</title>

  <!-- Dépendances CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <!-- Style personnalisé inspiré du dashboard patient -->
  <style>
    :root {
        --main-blue: #1e3a5f;
        --accent-red: #7e0021;
        --bg-light: #f4f7f9;
    }
    
    body {
     background-color: #e0f2fe;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }
    
    main {
        flex-grow: 1;
    }

    /* Message de bienvenue */
    .welcome-message {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
    .welcome-message h1 {
        font-weight: 700;
    }

    /* Cartes d'action */
    .action-card {
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.07);
        transition: all 0.3s ease;
        height: 100%;
        display: flex;
        flex-direction: column;
        border: 1px solid #e9ecef;
        text-decoration: none;
        color: inherit;
    }
    
    .action-card:hover {
        transform: translateY(-8px);
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        text-decoration: none;
        color: inherit;
    }
    
    .card-body {
        flex-grow: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        padding: 2rem;
    }

    .card-icon {
        font-size: 3rem;
        color: var(--main-blue);
        margin-bottom: 1rem;
    }
    
    .card-title {
        font-size: 1.25rem;
        font-weight: 600;
        color: var(--main-blue);
        margin-bottom: 0.5rem;
    }
    
    .card-text {
        color: #555;
        flex-grow: 1;
        margin-bottom: 1.5rem;
    }

    .btn-dashboard {
        background-color: var(--accent-red);
        border: none;
        color: white;
        font-weight: 500;
        padding: 0.6rem 1.5rem;
        border-radius: 50px; /* Boutons en pilule */
        transition: all 0.3s ease;
    }
    
    .btn-dashboard:hover {
        background-color: #6a001c;
        transform: scale(1.05);
        color: white;
    }

    /* Header et Footer */
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

 <!-- Header -->
<header style="background-color: #1e3a5f;">
  <div class="container-fluid px-4">
    <div class="d-flex align-items-center justify-content-between py-2">

      <!-- Logo + titre -->
      <div class="d-flex align-items-center gap-2">
        <img src="image/image2.png" alt="Logo CarePath" style="height: 40px;">
        <span class="fs-4 fw-bold text-white text-uppercase" style="letter-spacing: 1px;">
          CarePath
        </span>
      </div>

      <!-- Menu -->
      <nav class="d-flex align-items-center gap-3">
       <a class="nav-link text-white d-flex align-items-center gap-1" href="medecin_dashboard.jsp">
          <i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="nouvelleHospitalisationForm">
          <i class="fas fa-hospital-user"></i><span>Nouvelle Hospitalisation</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PlanifierExamen1">
          <i class="fas fa-calendar-check"></i><span>Plannifier Examens</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PrepareDiagnosticServlet">
          <i class="fas fa-microscope"></i><span>Diagnostics</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PrescriptionServlet">
          <i class="fas fa-pills"></i><span>Préscriptions</span>
        </a>
        
         <a class="nav-link text-white" href="medecin/mesHospitalisations">Planification des sorties</a>
      </nav>

      <!-- Déconnexion -->
      <form action="Logout" method="post" class="m-0">
        <button type="submit" class="btn btn-danger btn-sm d-flex align-items-center gap-1">
          <i class="fas fa-sign-out-alt"></i><span>Déconnexion</span>
        </button>
      </form>
    </div>
  </div>
</header>

 <main class="py-5">
    <div class="container">
            <!-- Message de bienvenue amélioré -->
            <div class="welcome-message text-center">
                <h1 class="display-5">
                    Bienvenue, Dr. ${sessionScope.medecin.prenom} ${sessionScope.medecin.nom}
                </h1>
                <p class="lead">Votre tableau de bord centralisé pour la gestion des patients.</p>
            </div>
            
            <!-- Cartes de fonctionnalités -->
            <div class="row g-4">
                
                <div class="col-lg-4 col-md-6">
                    <a href="${pageContext.request.contextPath}/nouvelleHospitalisationForm" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-hospital-user"></i></div>
                            <h3 class="card-title">Nouvelle Hospitalisation</h3>
                            <p class="card-text">Admettre un nouveau patient, choisir son service et lui attribuer un lit.</p>
                            <span class="btn btn-dashboard mt-auto">Commencer</span>
                        </div>
                    </a>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <a href="${pageContext.request.contextPath}/medecin/mesHospitalisations" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-procedures"></i></div>
                            <h3 class="card-title">Gérer les Sorties</h3>
                            <p class="card-text">Consulter les patients hospitalisés, planifier leur sortie et générer les dossiers.</p>
                            <span class="btn btn-dashboard mt-auto">Gérer</span>
                        </div>
                    </a>
                </div>
                
                 <div class="col-lg-4 col-md-6">
                    <a href="${pageContext.request.contextPath}/PlanifierExamen1" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-calendar-check"></i></div>
                            <h3 class="card-title">Planifier des Examens</h3>
                            <p class="card-text">Prescrire des analyses (biologiques, radiologiques...) pour un patient.</p>
                             <span class="btn btn-dashboard mt-auto">Planifier</span>
                        </div>
                    </a>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <a href="${pageContext.request.contextPath}/PrepareDiagnosticServlet" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-stethoscope"></i></div>
                            <h3 class="card-title">Poser un Diagnostic</h3>
                            <p class="card-text">Ajouter un diagnostic au dossier d'un patient hospitalisé.</p>
                             <span class="btn btn-dashboard mt-auto">Diagnostiquer</span>
                        </div>
                    </a>
                </div>
                
                <div class="col-lg-4 col-md-6">
                    <a href="${pageContext.request.contextPath}/PrescriptionServlet" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-pills"></i></div>
                            <h3 class="card-title">Créer une Prescription</h3>
                            <p class="card-text">Rédiger une ordonnance de médicaments pour un patient.</p>
                             <span class="btn btn-dashboard mt-auto">Prescrire</span>
                        </div>
                    </a>
                </div>

                <div class="col-lg-4 col-md-6">  
                   <a href="${pageContext.request.contextPath}/HistoriqueServlete" class="action-card">
                        <div class="card-body">
                            <div class="card-icon"><i class="fas fa-notes-medical"></i></div>
                            <h3 class="card-title">Consulter un Dossier</h3>
                            <p class="card-text">Rechercher un patient et accéder à son historique médical complet.</p>
                             <span class="btn btn-dashboard mt-auto">Consulter</span>
                        </div>
                    </a>
                </div>

            </div>
    </div>
  </main>

  <!-- Footer -->
  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>