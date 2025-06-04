<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <c:set var="medecin" value="${sessionScope.medecin}"/> --%> <%-- Pas nécessaire si tu utilises directement sessionScope.medecin --%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Nouvelle Hospitalisation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <%-- Adapte le chemin vers ton CSS --%>
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
<main class="container mt-5">
    <div class="card">
        <div class="card-header bg-primary text-white">
            <h4>Nouvelle Hospitalisation</h4>
        </div>
        <div class="card-body">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>
            <c:if test="${not empty requestScope.error}"> <%-- Pour les erreurs du CreerHospitalisationServlet --%>
                <div class="alert alert-danger mb-4">${requestScope.error}</div>
            </c:if>
            <c:if test="${not empty param.success}">
                 <div class="alert alert-success" role="alert">
                    ${param.success}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/creerHospitalisation" method="post">
                <!-- Informations du médecin (lecture seule) -->
                <div class="mb-3">
                    <label class="form-label">Médecin traitant</label>
                    <input type="text" class="form-control"
                           value="Dr. ${sessionScope.medecin.prenom} ${sessionScope.medecin.nom}"
                           readonly>
                    <input type="hidden" name="idMedecin" value="${sessionScope.medecin.id}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Hôpital</label>
                    <input type="text" class="form-control"
                           value="${not empty sessionScope.medecin.nomHopital ? sessionScope.medecin.nomHopital : 'Non spécifié'}"
                           readonly>
                    <input type="hidden" name="nomHopital" value="${sessionScope.medecin.nomHopital}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Service</label>
                    <input type="text" class="form-control"
                           value="${not empty sessionScope.medecin.nomService ? sessionScope.medecin.nomService : 'Non spécifié'}"
                           readonly>
                    <input type="hidden" name="service" value="${sessionScope.medecin.nomService}">
                </div>

                <!-- Sélection du Patient -->
                <div class="mb-3">
                    <label for="idPatient" class="form-label">Patient</label>
                    <select class="form-select" id="idPatient" name="idPatient" required>
                        <option value="">-- Sélectionner un patient --</option>
                        <c:forEach var="patient" items="${patientsList}">
                            <option value="${patient.id}">${patient.nom} ${patient.prenom} (CIN: ${patient.cin})</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Champs modifiables -->
                <div class="mb-3">
    <label for="duree" class="form-label">Durée prévue de l'hospitalisation</label>
    <select class="form-select" id="duree" name="duree" required>
        <option value="">-- Sélectionner une durée --</option>
        <option value="1 jour">1 Jour</option>
        <option value="2 jours">2 Jours</option>
        <option value="3 jours">3 Jours</option>
        <option value="4 jours">4 Jours</option>
        <option value="5 jours">5 Jours</option>
        <option value="6 jours">6 Jours</option>
        <option value="1 semaine">1 Semaine</option>
        <option value="10 jours">10 Jours</option>
        <option value="2 semaines">2 Semaines</option>
        <option value="3 semaines">3 Semaines</option>
        <option value="1 mois">1 Mois</option>
        <option value="2 mois">2 Mois</option>
        <option value="3 mois">3 Mois</option>
        <!-- Tu peux ajouter d'autres options courantes ici -->
        <!-- Si tu veux toujours permettre une saisie libre en plus, c'est plus complexe.
             Pour l'instant, restons avec le select. -->
    </select>
</div>

                <div class="mb-3">
                    <label for="motif" class="form-label">Motif d'hospitalisation</label>
                    <textarea class="form-control" id="motif" name="motif" rows="3" required></textarea>
                </div>

                <div class="mb-3">
                    <label for="diagnosticInitial" class="form-label">Diagnostic initial</label>
                    <textarea class="form-control" id="diagnosticInitial" name="diagnosticInitial" rows="3"></textarea> <%-- Rendu non-obligatoire, à toi de voir --%>
                </div>

                

                


                <div class="d-grid">
                    <button type="submit" class="btn btn-primary btn-lg">
                        <i class="fas fa-save me-2"></i> Enregistrer l'hospitalisation
                    </button>
                </div>
            </form>
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