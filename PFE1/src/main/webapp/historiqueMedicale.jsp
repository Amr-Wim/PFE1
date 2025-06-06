<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Historique Médical</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-8x6yQ1EoE8Is0Q9vZff4Kn8Z4Ofy+HEsf3F9EQoTlf9oZ2KvDdBbSz+ukOfT6+f88l+c1E9KgyGlTYKhwD1F2Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />

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

    header {
      background-color: var(--main-blue);
      padding: 20px 60px;
      display: flex;
      align-items: center;
      border-bottom: 4px solid var(--accent-red);
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
    }

    .title {
      font-size: 30px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 1.5px;
    }

    .menu-links {
      display: flex;
      gap: 8px;
      margin-left: auto;
    }

    .menu-links a {
      color: var(--accent-red);
      font-weight: bold;
      background-color: #ffffff;
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
      transition: background-color 0.3s ease;
    }

    .menu-links a:hover {
      background-color: #f1f5f9;
    }

    main {
      flex: 1;
      padding: 50px 20px;
    }

    .card {
      background-color: #ffffff;
      border-radius: 16px;
      padding: 30px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
      max-width: 900px;
      margin: auto;
    }

    .card.search-box {
      background-color: var(--main-blue);
      color: white;
    }

    .card.search-box input,
    .card.search-box button {
      border-radius: 6px;
    }

    .card.search-box .form-control {
      border: none;
    }

    .card.search-box .btn-primary {
      background-color: var(--accent-red);
      border-color: var(--accent-red);
    }

    h2 {
      text-align: center;
      color: white;
      margin-bottom: 30px;
      font-weight: bold;
    }

    h4.section-title {
      color: var(--accent-red) !important;
      font-weight: bold;
      text-align: center;
      margin-bottom: 20px;
    }

    table {
      width: 100%;
    }

    table th {
      background-color: var(--main-blue);
      color: white;
    }

    table td, table th {
      vertical-align: middle !important;
      color: #1e3a5f;
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
        <a class="nav-link text-white d-flex align-items-center gap-1" href="nouvelleHospitalisation.jsp">
          <i class="fas fa-hospital-user"></i><span>Nouvelle Hospitalisation</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="plannifierExamens.jsp">
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


<main>
  <!-- Barre de recherche -->
  <div class="card mb-5 search-box">
    <h2>Recherche de l'historique médical</h2>
    <form method="get" action="HistoriqueServlete">
      <div class="input-group mb-3">
        <input type="text" class="form-control" name="cin" placeholder="Entrez le CIN du patient" required>
        <button class="btn btn-primary" type="submit">Rechercher</button>
      </div>
    </form>
  </div>

  <c:if test="${not empty patient}">
    <!-- Informations du patient -->
    <div class="card mb-4">
      <h4 class="section-title">Informations du Patient</h4>
      <table class="table table-bordered">
        <tr><th>Nom</th><td>${patient.nom}</td></tr>
        <tr><th>Prénom</th><td>${patient.prenom}</td></tr>
        <tr><th>Date de Naissance</th><td><fmt:formatDate value="${patient.dateNaissance}" pattern="dd/MM/yyyy"/></td></tr>
        <tr><th>Adresse</th><td>${patient.adresse}</td></tr>
        <tr><th>Poids</th><td>${patient.poids} kg</td></tr>
        <tr><th>Groupe Sanguin</th><td>${patient.groupeSanguin}</td></tr>
        <tr><th>CIN</th><td>${patient.cin}</td></tr>
      </table>
    </div>

    <!-- Allergies -->
    <div class="card mb-4">
      <h4 class="section-title">Allergies</h4>
      <table class="table table-bordered table-hover">
        <thead><tr><th>Allergie</th><th>Niveau de Gravité</th><th>Date de Détection</th></tr></thead>
        <tbody>
          <c:forEach var="a" items="${allergies}">
            <tr>
              <td>${a.allergie}</td>
              <td>${a.niveauGravite}</td>
              <td><fmt:formatDate value="${a.dateDetection}" pattern="dd/MM/yyyy"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Antécédents -->
    <div class="card mb-4">
      <h4 class="section-title">Antécédents Médicaux</h4>
      <table class="table table-bordered table-hover">
        <thead><tr><th>Type</th><th>Description</th><th>Date</th></tr></thead>
        <tbody>
          <c:forEach var="ant" items="${antecedents}">
            <tr>
              <td>${ant.type}</td>
              <td>${ant.description}</td>
              <td><fmt:formatDate value="${ant.date}" pattern="dd/MM/yyyy"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Diagnostics -->
    <div class="card mb-4">
      <h4 class="section-title">Diagnostics</h4>
      <table class="table table-bordered table-hover">
        <thead><tr><th>Date</th><th>Contenu</th></tr></thead>
        <tbody>
          <c:forEach var="diag" items="${diagnostics}">
            <tr>
              <td><fmt:formatDate value="${diag.date}" pattern="dd/MM/yyyy"/></td>
              <td>${diag.contenu}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Traitements -->
    <div class="card mb-4">
      <h4 class="section-title">Traitements</h4>
      <table class="table table-bordered table-hover">
        <thead><tr><th>Date</th><th>Contenu</th><th>Durée</th></tr></thead>
        <tbody>
          <c:forEach var="t" items="${traitements}">
            <tr>
              <td><fmt:formatDate value="${t.date_enregistrement}" pattern="dd/MM/yyyy"/></td>
              <td>${t.contenu}</td>
              <td>${t.duree}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- Vaccinations -->
    <div class="card mb-4">
      <h4 class="section-title">Vaccinations</h4>
      <table class="table table-bordered table-hover">
        <thead><tr><th>Nom du Vaccin</th><th>Date</th><th>Réactions Éventuelles</th></tr></thead>
        <tbody>
          <c:forEach var="v" items="${vaccinations}">
            <tr>
              <td>${v.nomVaccin}</td>
              <td><fmt:formatDate value="${v.dateVaccination}" pattern="dd/MM/yyyy"/></td>
              <td>${v.reactionsEventuelles}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>

  <c:if test="${empty patient && not empty param.cin}">
    <div class="alert alert-warning text-center">
      Aucun patient trouvé avec le CIN <strong>${param.cin}</strong>.
    </div>
  </c:if>
</main>

<!-- Footer -->
<footer>
  &copy; 2025 - CarePath. Tous droits réservés.
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
