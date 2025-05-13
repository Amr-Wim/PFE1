<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Fiche Patient</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

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
  <header>
    <div class="logo-title">
      <img src="image/image2.png" alt="Logo">
      <span class="title">CAREPATH</span>
    </div>
     <nav class="ms-auto">
     <a href="infirmier_dashboard.jsp" class="btn btn-light fw-bold" style="color:#7e0021;">Acceuil</a>
  <a href="historiqueMedical.jsp" class="btn btn-light fw-bold" style="color:#7e0021;">Historique Médical</a>
   <a href="prescription.jsp" class="btn btn-light fw-bold" style="color:#7e0021;">Préscriptions</a>
</nav>
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
