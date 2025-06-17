<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Fiche Patient</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    :root {
      --main-blue: #1e3a5f;
      --accent-red: #7e0021;
      --bg-light: #e0f2fe;
      --text-dark: #0f172a;
      --header-font: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    body {
      background-color: var(--bg-light);
      font-family: var(--header-font);
      margin: 0;
      padding: 0;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
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
    }
    .title {
      font-size: 30px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 1.5px;
    }
    main {
      flex: 1;
      padding: 50px 20px;
  }
    .soins-title {
      color: var(--accent-red);
      font-weight: bold;
      margin-bottom: 30px;
    }
    .search-bar {
      max-width: 500px;
      margin: 0 auto 40px;
    }

    .card {
      border: none;
      border-radius: 16px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
      background-color: #ffffff;
    }
    .btn-primary {
      background-color: var(--main-blue);
      border: none;
      font-weight: 500;
      letter-spacing: 0.5px;
      transition: background-color 0.2s ease-in-out;
    }
    .btn-primary:hover {
      background-color: #16314f;
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
     .info-label {
            font-weight: bold;
            color:#7e0021;
        }
  </style>
</head>
<body>
  <!-- Header -->
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

  <!-- Main Content -->
  <main class="container">

    <h2 class="text-center soins-title">Fiche du Patient</h2>

    <!-- Barre de recherche -->
    <div class="search-bar">
    <form method="get" action="<%= request.getContextPath() %>/AfficherFicheServlet1">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="Rechercher par numéro de patient..." name="idPatient">
          <button class="btn btn-primary" type="submit">Rechercher</button>
        </div>
      </form>
    </div>
    <!-- Résultats simulés (si patient trouvé) -->
    <div class="card p-4">
 <%
 String idPatient = (String) request.getAttribute("idPatient");
String nom = (String) request.getAttribute("nom");
String prenom = (String) request.getAttribute("prenom");
String dateNaissance = (String) request.getAttribute("dateNaissance");
String diagnostic = (String) request.getAttribute("diagnostic");
Boolean rechercheEffectuee = (Boolean) request.getAttribute("rechercheEffectuee");
%>
<% if (idPatient != null && nom != null && prenom != null) { %>
<div class="card p-4">
  <h5 class="mb-3">Résultats pour le patient <strong>#P<%= idPatient %></strong> :</h5>
  <ul class="list-group">
    <li class="list-group-item"><span class="info-label">Nom :</span> <%= nom %></li>
    <li class="list-group-item"><span class="info-label">Prénom :</span> <%= prenom %></li>
    <li class="list-group-item"><span class="info-label">Date de naissance :</span> <%= dateNaissance %></li>
    <li class="list-group-item"><span class="info-label">Diagnostic :</span> <%= (diagnostic != null ? diagnostic : "Non renseigné") %></li>
  </ul>
</div>
<% } else if (Boolean.TRUE.equals(rechercheEffectuee)) { %>
<div class="alert alert-warning mt-4 text-center">
  Aucun patient trouvé avec ce numéro.
</div>
<% } %>
   </main>
  <!-- Footer -->
  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>
  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
