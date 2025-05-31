<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Historique Médical</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

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

    h2 {
      text-align: center;
      color: var(--accent-red);
      margin-bottom: 30px;
      font-weight: bold;
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
  <header>
  <div class="logo-title">
    <img src="image/image2.png" alt="Logo">
    <span class="title">CAREPATH</span>
  </div>
  <div class="menu-links">
    <a href="medecin_dashboard.jsp">
      <i class="fas fa-home"></i> Accueil
    </a>
     <div class="menu-links">
    <a href="plannifierExamens.jsp">
      <i class="fas fa-flask"></i> Plannifier Examens
    </a>
    <a href="historique_medical.jsp">
      <i class="fas fa-file-medical"></i> Historique Médical
    </a>
    <a href="resultats_examens.jsp">
      <i class="fas fa-chart-line"></i> Résultats Examens
    </a>
    <a href="hospitalisation.jsp">
      <i class="fas fa-procedures"></i> Hospitalisation
    </a>
    <a href="javascript:history.back()">
      <i class="fas fa-arrow-left"></i> Retour
    </a>
  </div>
</header>
<main>
  <div class="card">
    <h2><i class="fas fa-procedures me-2"></i> Formulaire d'Hospitalisation</h2>
    
    <%-- Affichage des messages d'erreur/succès --%>
    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger mb-4">
        <%= request.getAttribute("error") %>
      </div>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
      <div class="alert alert-success mb-4">
        <%= request.getAttribute("success") %>
      </div>
    <% } %>
    
    <form action="HospitalisationServlet" method="post">
      <!-- CIN Patient -->
      <div class="mb-3">
        <label for="cinPatient" class="form-label">CIN du Patient</label>
        <input type="text" class="form-control" id="cinPatient" name="cinPatient" required>
      </div>
      
      <!-- Hôpital (pré-rempli) -->
      <div class="mb-3">
        <label class="form-label">Hôpital</label>
        <input type="text" class="form-control" value="<%= session.getAttribute("nomHopital") %>" readonly>
        <input type="hidden" name="nomHopital" value="<%= session.getAttribute("nomHopital") %>">
      </div>
      
      <!-- Service (pré-rempli) -->
      <div class="mb-3">
        <label class="form-label">Service</label>
        <input type="text" class="form-control" value="<%= session.getAttribute("service") %>" readonly>
        <input type="hidden" name="service" value="<%= session.getAttribute("service") %>">
      </div>
       <div class="mb-3">
        <label for="diagnostic" class="form-label">Diagnostic Initial </label>
        <input type="text" class="form-control" id="diagnostic" name="diagnostic" required>
      </div>
      <!-- Durée -->
      <div class="mb-3">
        <label for="duree" class="form-label">Durée prévue (jours)</label>
        <input type="number" class="form-control" id="duree" name="duree" min="1" required>
      </div>
      
      <!-- État du patient -->
      <div class="mb-3">
        <label for="etat" class="form-label">État du patient</label>
        <select class="form-select" id="etat" name="etat" required>
          <option value="" selected disabled>Sélectionnez un état</option>
          <option value="Stable">Stable</option>
          <option value="Grave">Grave</option>
          <option value="Critique">Critique</option>
        </select>
      </div>
      
      <!-- Chambre -->
      
      <!-- Motif -->
      <div class="mb-3">
        <label for="motif" class="form-label">Motif d'hospitalisation</label>
        <textarea class="form-control" id="motif" name="motif" rows="3" required></textarea>
      </div>
      
      <!-- Date admission -->
      <div class="mb-4">
        <label for="dateAdmission" class="form-label">Date d'admission</label>
        <input type="date" class="form-control" id="dateAdmission" name="dateAdmission" required>
      </div>
      
      <!-- Bouton soumission -->
      <div class="d-grid">
        <button type="submit" class="btn btn-primary btn-lg">
          <i class="fas fa-save me-2"></i> Enregistrer l'hospitalisation
        </button>
      </div>
    </form>
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