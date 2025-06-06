<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Dashboard Médecin</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
 <!-- Bootstrap CSS -->
 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-8x6yQ1EoE8Is0Q9vZff4Kn8Z4Ofy+HEsf3F9EQoTlf9oZ2KvDdBbSz+ukOfT6+f88l+c1E9KgyGlTYKhwD1F2Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    body {
      background-color: #e0f2fe;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      margin: 0;
      padding: 0;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }
  
  .AB p{
    color :#7e0021;
     text-align: center;
     font-size: 28px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 2px;
    }
    header {
      background-color: #1e3a5f;
      padding: 20px 60px;
      display: flex;
      align-items: center;
      border-bottom: 4px solid #7e0021;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
      color: white;
    }

    .logo-title {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .logo-title img {
      height: 48px;
      width: auto;
    }

    .title {
      font-size: 28px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 2px;
    }

    main {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 50px 20px;
    }

    .card-group {
      gap: 30px;
    }

    .menu-card {
      border-radius: 20px;
      padding: 30px;
      text-align: center;
      box-shadow: 0 10px 30px rgba(0,0,0,0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      background-color: #fff;
      border-top: 6px solid #7e0021;
    }

    .menu-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 12px 35px rgba(0,0,0,0.15);
    }

    .menu-card h5 {
      font-weight: bold;
      color: #1e3a5f;
      margin-bottom: 15px;
    }

    .menu-card p {
      color: #334155;
    }

    footer {
      background-color: #1e3a5f;
      color: #ffffff;
      text-align: center;
      padding: 16px 30px;
      font-size: 14px;
      border-top: 4px solid #7e0021;
      font-weight: 500;
      letter-spacing: 0.5px;
    }

    @media (max-width: 991px) {
      .card-group {
        flex-direction: column;
        align-items: center;
      }
    }
        .menu-links {
      margin-left: auto;
      display: flex;
      gap: 10px;
    }

    .menu-links a {
      background-color: #ffffff;
      color: #7e0021;
      font-weight: bold;
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
    }

    .menu-links a:hover {
      background-color: #f1f5f9;
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
        <a class="nav-link text-white d-flex align-items-center gap-1" href="nouvelleHospitalisation.jsp">
          <i class="fas fa-hospital-user"></i><span>Nouvelle Hospitalisation</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PlanifierExamen1">
          <i class="fas fa-calendar-check"></i><span>Plannifier Examens</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="diagnostic.jsp">
          <i class="fas fa-microscope"></i><span>Diagnostics</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PrescriptionMedicale.jsp">
          <i class="fas fa-pills"></i><span>Préscriptions</span>
        </a>
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

<div class="container">
            <!-- Message de bienvenue -->
            <div class="welcome-message text-center mb-5">
                <h1 class="mb-3" style="color: var(--main-blue);">
                    <i class="fas fa-user-circle me-2"></i>Bienvenue, ${utilisateur.prenom}
                </h1>
                <p class="lead">Accédez rapidement à toutes les fonctionnalités de votre espace medecin</p>
            </div>
<div class="AB">

</div>
  <!-- Main Dashboard -->
  <main>
    <div class="container">
      <div class="row justify-content-center card-group">

        <div class="col-md-4">
          <a href="nouvelleHospitalisationForm" style="text-decoration: none;">
            <div class="menu-card">
              <h5>Nouvelle Hospitalisation</h5>
              <p>Initiez une nouvelle hospitalisation et renseignez les données du patient.</p>
            </div>
          </a>
        </div>

        <div class="col-md-4">
          <a href="historiqueMedicale.jsp" style="text-decoration: none;">
            <div class="menu-card">
              <h5>Historique Médical</h5>
              <p>Consultez les antécédents médicaux et traitements passés du patient.</p>
            </div>
          </a>
        </div>
        
        <div class="col-md-4">
          <a href="PlanifierExamen" style="text-decoration: none;">
            <div class="menu-card">
              <h5>Plannifier Examens</h5>
              <p>Donner au patient des examens cliniques à faire.</p>
            </div>
          </a>
        </div>

        <div class="col-md-4">
          <a href="resultatsExamens.jsp" style="text-decoration: none;">
            <div class="menu-card">
              <h5>Résultats examens</h5>
              <p>Affichez les résultats d’analyses et d’examens cliniques du patient.</p>
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