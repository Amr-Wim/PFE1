<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Hospitalisations en Cours</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_medecin_dashboard.css"> <%-- Adapte ton CSS --%>
    <style>
        .table-hover tbody tr:hover {
            background-color: #f5f5f5;
            cursor: pointer;
        }
        .search-container {
            margin-bottom: 20px;
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
    <main class="container mt-4">
        <h2><i class="fas fa-procedures"></i> Mes Hospitalisations en Cours</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>

        <div class="search-container card p-3">
            <form method="GET" action="${pageContext.request.contextPath}/medecin/mesHospitalisations">
                <div class="input-group">
                    <input type="text" class="form-control" name="recherche" placeholder="Rechercher par nom ou ID patient..." value="${param.recherche}">
                    <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i> Rechercher</button>
                    <c:if test="${not empty param.recherche}">
                        <a href="${pageContext.request.contextPath}/medecin/mesHospitalisations" class="btn btn-outline-secondary"><i class="fas fa-times"></i> Effacer</a>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                Liste des patients hospitalisés
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty hospitalisationsList}">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Patient (ID)</th>
                                        <th>Date d'Admission</th>
                                        <th>Service (Hosp.)</th>
                                        <th>Motif</th>
                                        <th>Lit / Chambre</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="hosp" items="${hospitalisationsList}">
                                        <tr>
                                            <td>${hosp.patientPrenom} ${hosp.patientNom} (${hosp.idPatient})</td>
                                            <td><fmt:formatDate value="${hosp.dateAdmission}" pattern="dd/MM/yyyy"/></td>
                                            <td>${hosp.service}</td>
                                            <td><c:out value="${hosp.motif}"/></td>
                                            <td>
                                                <c:if test="${not empty hosp.litId}">
                                                    ${hosp.numeroLit} / ${hosp.numeroChambre}
                                                </c:if>
                                                <c:if test="${empty hosp.litId}">
                                                    Non assigné
                                                </c:if>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/preparerSortie?hospitalisationId=${hosp.id}" class="btn btn-sm btn-outline-primary" title="Préparer la sortie">
                                                    <i class="fas fa-walking"></i> Préparer Sortie
                                                </a>
                                                <%-- Tu pourrais ajouter d'autres actions ici : voir dossier, etc. --%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">Aucune hospitalisation en cours trouvée <c:if test="${not empty param.recherche}">pour les critères de recherche actuels</c:if>.</div>
                    </c:otherwise>
                </c:choose>
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