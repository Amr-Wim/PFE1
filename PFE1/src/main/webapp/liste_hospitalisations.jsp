<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Hospitalisations en Cours - CarePath</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #f8f9fa; /* Un fond plus clair et standard */
            --border-color: #dee2e6;
            --hover-bg: #eef2f9;
        }

        body {
            background-color: var(--bg-light);
            font-family: 'Segoe UI', sans-serif;
        }

        /* --- Style du Conteneur Principal --- */
        .main-content-card {
            background-color: white;
            border-radius: 0.5rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }
        
        .card-header-custom {
            background-color: transparent;
            border-bottom: 2px solid var(--main-blue);
            padding: 1.25rem;
        }
        
        .card-title-custom {
            color: var(--main-blue);
            font-weight: 600;
            margin: 0;
        }
        
        /* --- Style de la Table --- */
        .table-custom {
            margin-bottom: 0; /* Important pour que ça colle au bas de la carte */
        }
        
        .table-custom thead th {
            background-color: #f8f9fa;
            color: var(--main-blue);
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
            letter-spacing: 0.5px;
            border-bottom-width: 2px;
            border-color: #cdd4dc;
            vertical-align: middle;
        }

        .table-custom tbody tr:hover {
            background-color: var(--hover-bg);
        }
        
        .table-custom td {
            vertical-align: middle;
            padding: 0.9rem;
        }
        
        .patient-name {
            font-weight: 500;
            color: #212529;
        }
        .patient-id {
            display: block;
            font-size: 0.85rem;
            color: #6c757d;
        }
        
        .btn-action {
            white-space: nowrap; /* Empêche le bouton de passer à la ligne */
        }
        
        /* --- Barre de Recherche --- */
        .search-bar {
            max-width: 500px;
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


       <main class="container my-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
             <h2 class="h3 fw-bold" style="color: var(--main-blue);"><i class="fas fa-procedures me-2"></i>Mes Hospitalisations en Cours</h2>
             <%-- Vous pouvez ajouter un bouton ici si nécessaire, ex: <a href="..." class="btn btn-primary">...</a> --%>
        </div>

        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success">${sessionScope.successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger">${sessionScope.errorMessage}</div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>
        
        <div class="main-content-card">
            <div class="card-header-custom d-flex justify-content-between align-items-center">
                <h5 class="card-title-custom">Liste des patients</h5>
                <div class="search-bar">
                    <form method="GET" action="${pageContext.request.contextPath}/medecin/mesHospitalisations">
                        <div class="input-group">
                            <input type="text" class="form-control" name="recherche" placeholder="Rechercher un patient..." value="${param.recherche}">
                            <button class="btn btn-outline-secondary" type="submit"><i class="fas fa-search"></i></button>
                             <c:if test="${not empty param.recherche}">
                                <a href="${pageContext.request.contextPath}/medecin/mesHospitalisations" class="btn btn-outline-danger" title="Effacer la recherche"><i class="fas fa-times"></i></a>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-custom">
                    <thead>
                        <tr>
                            <th>Patient</th>
                            <th>Date d'Admission</th>
                            <th>Motif</th>
                            <th>Lit / Chambre</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty hospitalisationsList}">
                                <c:forEach var="hosp" items="${hospitalisationsList}">
                                    <tr>
                                        <td>
                                            <div class="patient-name">${hosp.patientPrenom} ${hosp.patientNom}</div>
                                            <div class="patient-id">ID: ${hosp.idPatient}</div>
                                        </td>
                                        <td><fmt:formatDate value="${hosp.dateAdmission}" pattern="dd/MM/yyyy"/></td>
                                        <td><c:out value="${hosp.motif}"/></td>
                                        <td>
                                            <c:if test="${not empty hosp.litId}">
                                                <i class="fas fa-bed text-success me-2"></i> ${hosp.numeroLit} / ${hosp.numeroChambre}
                                            </c:if>
                                            <c:if test="${empty hosp.litId}">
                                                <span class="badge bg-warning text-dark">Non assigné</span>
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/preparerSortie?hospitalisationId=${hosp.id}" class="btn btn-sm btn-primary btn-action" title="Préparer la sortie">
                                                <i class="fas fa-walking me-1"></i> Planifier Sortie
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center p-5">
                                        <div class="text-muted">
                                            <i class="fas fa-inbox fa-2x mb-2"></i>
                                            <p class="mb-0">Aucune hospitalisation en cours trouvée.</p>
                                            <c:if test="${not empty param.recherche}">
                                                <small>Essayez de modifier vos critères de recherche.</small>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

     <!-- Footer -->
  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>
    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>