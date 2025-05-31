<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Allergie, model.Antecedant, model.Diagnostic, model.Traitement" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Historique Médical</title>

  <!-- Bootstrap CSS -->
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
    .search-bar {
      max-width: 500px;
      margin: 0 auto 40px;
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
  </style>
</head>
<body>
 <!-- Header -->
    <header>
        <div class="container-fluid d-flex align-items-center justify-content-between">
            <!-- Logo et titre -->
            <div class="d-flex align-items-center gap-3">
                <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                <span class="fs-3 fw-bold text-uppercase" style="text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);">
                    CarePath
                </span>
            </div>
            
           <div class="d-flex gap-3">
    <a class="nav-link text-white" href="patient_dashboard.jsp">
        <i class="fas fa-tachometer-alt me-1"></i> Tableau de bord
    </a>
    <a class="nav-link text-white" href="fiche_patient.jsp">
        <i class="fas fa-user me-1"></i> Informations
    </a>
    <a class="nav-link text-white" href="dossier_medical.jsp">
        <i class="fas fa-file-medical me-1"></i> Dossier médical
    </a>
    <a class="nav-link text-white" href="hospitalisation.jsp">
        <i class="fas fa-procedures me-1"></i> Hospitalisation
    </a>
    <a class="nav-link text-white" href="analyses.jsp">
        <i class="fas fa-flask me-1"></i> Analyses
    </a>
                
                <!-- Bouton Déconnexion -->
                <form action="Logout" method="post" class="m-0">
                    <button type="submit" class="btn btn-danger btn-sm">
                        <i class="fas fa-sign-out-alt me-1"></i> Déconnexion
                    </button>
                </form>
            </nav>
        </div>
    </header>
 
  
 <main class="container my-5">
    <div class="row mb-4">
      <div class="col">
        <h1 class="text-center mb-4" style="color: var(--main-blue);">
          <i class="fas fa-file-medical me-2"></i>Dossier Médical
        </h1>
        <div class="alert alert-info">
          <h4 class="alert-heading"><i class="fas fa-info-circle me-2"></i>Informations importantes</h4>
          <p>Ce dossier contient l'ensemble de vos informations médicales enregistrées dans notre système. Consultez régulièrement pour rester informé de votre état de santé.</p>
        </div>
      </div>
    </div>

    <!-- Section Allergies -->
    <div class="medical-section">
      <div class="section-header">
        <h2 class="section-title"><i class="fas fa-allergies me-2"></i>Allergies</h2>
        <span class="badge bg-secondary">${not empty allergies ? allergies.size() : 0} enregistrement(s)</span>
      </div>
      <div class="section-body">
        <c:choose>
          <c:when test="${not empty allergies}">
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>Allergie</th>
                    <th>Niveau de gravité</th>
                    <th>Date de détection</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="a" items="${allergies}">
                    <tr>
                      <td>${a.allergie}</td>
                      <td>
                        <c:choose>
                          <c:when test="${a.niveauGravite eq 'élevé' or a.niveauGravite eq 'high'}">
                            <span class="badge-severity high-severity">Élevé</span>
                          </c:when>
                          <c:when test="${a.niveauGravite eq 'moyen' or a.niveauGravite eq 'medium'}">
                            <span class="badge-severity medium-severity">Moyen</span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge-severity low-severity">Faible</span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>${a.dateDetection}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:when>
          <c:otherwise>
            <div class="no-data">
              <i class="fas fa-check-circle me-2"></i>Aucune allergie enregistrée
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- Section Traitements -->
    <div class="medical-section">
      <div class="section-header">
        <h2 class="section-title"><i class="fas fa-prescription-bottle-alt me-2"></i>Traitements</h2>
        <span class="badge bg-secondary">${not empty traitements ? traitements.size() : 0} enregistrement(s)</span>
      </div>
      <div class="section-body">
        <c:choose>
          <c:when test="${not empty traitements}">
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>Prescription</th>
                    <th>Durée</th>
                    <th>Date d'enregistrement</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="t" items="${traitements}">
                    <tr>
                      <td>${t.contenu}</td>
                      <td>${t.duree}</td>
                      <td>${t.date_enregistrement}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:when>
          <c:otherwise>
            <div class="no-data">
              <i class="fas fa-check-circle me-2"></i>Aucun traitement enregistré
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- Section Antécédents -->
    <div class="medical-section">
      <div class="section-header">
        <h2 class="section-title"><i class="fas fa-history me-2"></i>Antécédents Médicaux</h2>
        <span class="badge bg-secondary">${not empty antecedants ? antecedants.size() : 0} enregistrement(s)</span>
      </div>
      <div class="section-body">
        <c:choose>
          <c:when test="${not empty antecedants}">
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>Type</th>
                    <th>Description</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="ant" items="${antecedants}">
                    <tr>
                      <td>${ant.type}</td>
                      <td>${ant.description}</td>
                      <td>${ant.date}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:when>
          <c:otherwise>
            <div class="no-data">
              <i class="fas fa-check-circle me-2"></i>Aucun antécédent médical enregistré
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <!-- Section Diagnostics -->
    <div class="medical-section">
      <div class="section-header">
        <h2 class="section-title"><i class="fas fa-stethoscope me-2"></i>Diagnostics</h2>
        <span class="badge bg-secondary">${not empty diagnostics ? diagnostics.size() : 0} enregistrement(s)</span>
      </div>
      <div class="section-body">
        <c:choose>
          <c:when test="${not empty diagnostics}">
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Diagnostic</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="d" items="${diagnostics}">
                    <tr>
                      <td style="width: 120px;">${d.date}</td>
                      <td>${d.contenu}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:when>
          <c:otherwise>
            <div class="no-data">
              <i class="fas fa-check-circle me-2"></i>Aucun diagnostic enregistré
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <div class="last-updated">
      <i class="fas fa-sync-alt me-1"></i>Dernière mise à jour : <%= new java.util.Date() %>
    </div>
  </main>



 <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
  