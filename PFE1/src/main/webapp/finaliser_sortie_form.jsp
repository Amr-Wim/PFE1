<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Préparer la Sortie du Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_medecin_dashboard.css">
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
        <h2><i class="fas fa-user-check"></i> Préparer la Sortie du Patient: ${patient.prenom} ${patient.nom} (Hosp. ID: ${hospitalisation.id})</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="card mt-3">
            <div class="card-header">
                Informations de Sortie
            </div>
            <div class="card-body">
                <form method="POST" action="${pageContext.request.contextPath}/preparerSortie">
                    <input type="hidden" name="hospitalisationId" value="${hospitalisation.id}">

                    <div class="mb-3">
                        <label for="dateSortieReelle" class="form-label">Date et Heure de Sortie Réelle *</label>
                        <input type="datetime-local" class="form-control" id="dateSortieReelle" name="dateSortieReelle" 
                               value="<fmt:formatDate value="${hospitalisation.dateSortieReelle != null ? hospitalisation.dateSortieReelle : now}" pattern="yyyy-MM-dd'T'HH:mm"/>" required>
                    </div>

                    <div class="mb-3">
                        <label for="diagnosticsSortie" class="form-label">Diagnostics de Sortie *</label>
                        <textarea class="form-control" id="diagnosticsSortie" name="diagnosticsSortie" rows="3" required>${hospitalisation.diagnosticsSortie}</textarea>
                    </div>

                    <div class="mb-3">
                        <label for="compteRendu" class="form-label">Compte-Rendu d'Hospitalisation *</label>
                        <textarea class="form-control" id="compteRendu" name="compteRendu" rows="5" required>${hospitalisation.compteRenduHospitalisation}</textarea>
                    </div>

                    <div class="mb-3">
                        <label for="instructionsSortie" class="form-label">Instructions pour le Patient *</label>
                        <textarea class="form-control" id="instructionsSortie" name="instructionsSortie" rows="4" required>${hospitalisation.instructionsSortie}</textarea>
                    </div>

                    <div class="mb-3">
                        <label for="infoRdvSuivi" class="form-label">Informations Rendez-vous de Suivi</label>
                        <textarea class="form-control" id="infoRdvSuivi" name="infoRdvSuivi" rows="2">${hospitalisation.informationsRendezVousSuivi}</textarea>
                    </div>
                    
                    <%-- Ici, plus tard, tu ajouteras la section pour l'ordonnance de sortie --%>
                    <%-- <div class="mb-3"> --%>
                    <%--     <label class="form-label">Ordonnance de Sortie</label> --%>
                    <%--     Si une ordonnance de sortie existe déjà, l'afficher. Sinon, bouton pour en créer une. --%>
                    <%-- </div> --%>


                    <button type="submit" class="btn btn-success"><i class="fas fa-check-circle"></i> Valider la Sortie et Préparer Dossier</button>
                    <a href="${pageContext.request.contextPath}/medecin/mesHospitalisations" class="btn btn-secondary">Annuler</a>
                </form>
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