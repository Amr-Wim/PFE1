<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Tableau de bord - Infirmier</title>

  <!-- Dépendances CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <!-- Style personnalisé inspiré des autres dashboards -->
  <style>
    :root {
        --main-blue: #1e3a5f;
        --accent-red: #7e0021;
        --bg-light: #f4f7f9;
        --text-dark: #2c3e50;
    }
    
    body {
         background-color: #e0f2fe;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        display: flex;
        flex-direction: column;
        min-height: 100vh;
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
    header, footer {
        background-color: var(--main-blue);
        color: white;
        border-top: 4px solid var(--accent-red);
        border-bottom: 4px solid var(--accent-red);
    }
  </style>
</head>
<body>

  <!-- Header standardisé -->
  <!-- Copiez et collez ce bloc <header> pour remplacer l'ancien sur vos pages d'infirmier -->

<header style="background-color: #1e3a5f; border-bottom: 4px solid var(--accent-red);">
  <div class="container-fluid px-4">
    <div class="d-flex align-items-center justify-content-between py-2">
      <!-- Logo + titre -->
      <div class="d-flex align-items-center gap-2">
        <img src="${pageContext.request.contextPath}/image/image2.png" alt="Logo CarePath" style="height: 40px;">
        <span class="fs-4 fw-bold text-white text-uppercase" style="letter-spacing: 1px;">CarePath</span>
      </div>
      
      <!-- Menu de navigation pour l'infirmier -->
      <nav class="d-flex align-items-center gap-3">
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/infirmier_dashboard.jsp">
            <i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span>
         </a>
         
         <!-- LIEN AJOUTÉ ICI -->
         <a class="nav-link text-white d-flex align-items-center gap-1" href="FichePatient.jsp">
            <i class="fas fa-id-card me-1"></i><span>Fiche Patient</span>
         </a>
         
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/RechercherObservationServlet">
            <i class="fas fa-eye me-1"></i><span>Observations</span>
         </a>
         
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/AfficherSoinsServlet">
            <i class="fas fa-hand-holding-medical me-1"></i><span>Soins</span>
         </a>
      </nav>
      
      <!-- Déconnexion -->
      <form action="${pageContext.request.contextPath}/Logout" method="post" class="m-0">
        <button type="submit" class="btn btn-danger btn-sm d-flex align-items-center gap-1">
          <i class="fas fa-sign-out-alt"></i><span>Déconnexion</span>
        </button>
      </form>
    </div>
  </div>
</header>

  <main class="py-5">
    <div class="container">
        <!-- Message de bienvenue -->
        <div class="welcome-message text-center">
            <h1 class="display-5">
                <%-- On utilise l'objet 'infirmier' stocké en session --%>
                Bienvenue, ${sessionScope.utilisateur.prenom} ${sessionScope.utilisateur.nom}
            </h1>
            <p class="lead">Votre espace de travail pour le suivi des soins et des patients.</p>
        </div>
        
        <!-- Cartes de fonctionnalités -->
        <div class="row g-4 justify-content-center">
            
            <div class="col-lg-4 col-md-6">
                <a href="${pageContext.request.contextPath}/AjouterObservationServlet" class="action-card">
                    <div class="card-body">
                        <div class="card-icon"><i class="fas fa-file-signature"></i></div>
                        <h3 class="card-title">Ajouter une Observation</h3>
                        <p class="card-text">Enregistrer les nouvelles constantes vitales et observations cliniques d'un patient.</p>
                        <span class="btn btn-dashboard mt-auto">Commencer</span>
                    </div>
                </a>
            </div>
            
            <div class="col-lg-4 col-md-6">
                <a href="${pageContext.request.contextPath}/RechercherObservationServlet" class="action-card">
                    <div class="card-body">
                        <div class="card-icon"><i class="fas fa-book-medical"></i></div>
                        <h3 class="card-title">Consulter les Observations</h3>
                        <p class="card-text">Rechercher un patient et consulter l'historique de ses observations.</p>
                        <span class="btn btn-dashboard mt-auto">Consulter</span>
                    </div>
                </a>
            </div>

            <div class="col-lg-4 col-md-6">
                <a href="${pageContext.request.contextPath}/AjouterSoinServlet" class="action-card"> <%-- Assurez-vous d'avoir une servlet pour afficher cette page --%>
                    <div class="card-body">
                        <div class="card-icon"><i class="fas fa-syringe"></i></div>
                        <h3 class="card-title">Ajouter un Soin</h3>
                        <p class="card-text">Enregistrer un soin réalisé, comme un pansement, une injection ou une aide.</p>
                        <span class="btn btn-dashboard mt-auto">Enregistrer</span>
                    </div>
                </a>
            </div>

            <div class="col-lg-4 col-md-6">
                <a href="${pageContext.request.contextPath}/AfficherSoinsServlet" class="action-card">
                    <div class="card-body">
                        <div class="card-icon"><i class="fas fa-clipboard-list"></i></div>
                        <h3 class="card-title">Consulter les Soins</h3>
                        <p class="card-text">Voir l'historique de tous les soins prodigués à un patient.</p>
                        <span class="btn btn-dashboard mt-auto">Consulter</span>
                    </div>
                </a>
            </div>
            
            <div class="col-lg-4 col-md-6">
    <a href="FichePatient.jsp" class="action-card">
        <div class="card-body">
            <div class="card-icon"><i class="fas fa-id-card"></i></div>
            <h3 class="card-title">Consulter Fiche Patient</h3>
            <p class="card-text">Rechercher un patient et afficher ses informations personnelles et médicales de base.</p>
            <span class="btn btn-dashboard mt-auto">Rechercher</span>
        </div>
    </a>
</div>
            
        </div>
    </div>
  </main>

  <!-- Footer -->
  <footer>
    © ${java.time.Year.now()} - CarePath. Tous droits réservés.
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>