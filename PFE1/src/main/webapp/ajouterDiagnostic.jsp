<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Diagnostic - CarePath</title>
    
    <!-- Dépendances -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Style personnalisé -->
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #f4f7f9;
            --text-dark: #2c3e50;
            --text-muted: #7f8c8d;
            --border-color: #dee2e6;
        }

        body {
             background-color: #e0f2fe;
            font-family: 'Segoe UI', sans-serif;
            color: var(--text-dark);
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        main { flex-grow: 1; }

        .form-wizard-container {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
            border: 1px solid var(--border-color);
            overflow: hidden;
        }

        .wizard-header {
           background: #1e3a5f;
            color: white;
            padding: 1.5rem 2rem;
            text-align: center;
        }
        .wizard-header h2 { margin: 0; font-weight: 700; }
        .wizard-header p { margin: 0; opacity: 0.8; }

        .wizard-body { padding: 2.5rem; }

        .form-section {
            margin-bottom: 2.5rem;
            padding-bottom: 1.5rem;
            border-bottom: 1px solid #f0f0f0;
        }
        .form-section:last-child {
            margin-bottom: 0;
            padding-bottom: 0;
            border-bottom: none;
        }

        .section-title {
            color: var(--accent-red);
            font-weight: 600;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            font-size: 1.25rem;
        }
        .section-title i {
            margin-right: 0.75rem;
            width: 24px;
            text-align: center;
        }

        .form-label {
            font-weight: 500;
            color: var(--text-muted);
        }
        
        .form-control[readonly] {
            background-color: #e9ecef;
            cursor: not-allowed;
        }

        .btn-submit-diag {
            background-color: var(--main-blue);
            border-color: var(--main-blue);
            color: white;
            font-weight: 600;
            padding: 0.8rem 2rem;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        .btn-submit-diag:hover {
            background-color: #162a42;
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(30, 58, 95, 0.3);
        }
        
        .char-counter {
            font-size: 0.85em;
            color: #6c757d;
            text-align: right;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<!-- Header (identique à votre exemple pour la cohérence) -->
<header style="background-color: #1e3a5f; border-bottom: 3px solid var(--accent-red);">
  <div class="container-fluid px-4">
    <div class="d-flex align-items-center justify-content-between py-2">
      <!-- Logo + titre -->
      <div class="d-flex align-items-center gap-2">
        <img src="${pageContext.request.contextPath}/image/image2.png" alt="Logo CarePath" style="height: 40px;">
        <span class="fs-4 fw-bold text-white text-uppercase" style="letter-spacing: 1px;">CarePath</span>
      </div>
      <!-- Menu (adapté pour le contexte du médecin) -->
      <nav class="d-flex align-items-center gap-3">
         <a class="nav-link text-white d-flex align-items-center gap-1" href="medecin_dashboard.jsp"><i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span></a>
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/PrepareDiagnosticServlet"><i class="fas fa-microscope me-1"></i><span>Diagnostics</span></a>
         <%-- Ajoutez d'autres liens ici si nécessaire --%>
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

   <main class="container py-5">
        <div class="mx-auto" style="max-width: 850px;">
            <div class="form-wizard-container">
                <div class="wizard-header">
                    <h2>Nouveau Diagnostic</h2>
                    <p>Enregistrement d'une nouvelle note clinique pour un patient hospitalisé</p>
                </div>
                
                <div class="wizard-body">
                    <c:if test="${empty patientsHospitalises}">
                        <div class="alert alert-warning text-center">
                            <i class="fas fa-info-circle me-2"></i>Vous n'avez actuellement aucun patient hospitalisé actif à diagnostiquer.
                        </div>
                    </c:if>

                    <c:if test="${not empty patientsHospitalises}">
                        <form action="${pageContext.request.contextPath}/AjouterDiagnostic" method="POST"> 
                        
                            <!-- Section 1: Identification -->
                            <div class="form-section">
                                <h4 class="section-title"><i class="fas fa-user-injured"></i>Identification du Patient</h4>
                                <div class="row g-3 align-items-center">
                                    <div class="col-md-8">
                                        <label for="patientSelect" class="form-label">Sélectionner le patient *</label>
                                        <select name="patientHospi" id="patientSelect" class="form-select form-select-lg" required>
                                            <option value="" disabled selected>-- Patients actuellement hospitalisés --</option>
                                            <c:forEach var="hosp" items="${patientsHospitalises}">
                                                <option value="${hosp.id};${hosp.idPatient}">
                                                    ${hosp.patientPrenom} ${hosp.patientNom} (Admission: <fmt:formatDate value="${hosp.dateAdmission}" pattern="dd/MM/yyyy"/>)
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="dateDiagnostic" class="form-label">Date</label>
                                        <input type="date" id="dateDiagnostic" name="dateDiagnostic" class="form-control"
                                               value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" readonly>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Section 2: Contenu du Diagnostic -->
                            <div class="form-section">
                                 <h4 class="section-title"><i class="fas fa-file-medical"></i>Contenu du Diagnostic</h4>
                                 <div class="mb-3">
                                    <label for="contenuDiagnostic" class="form-label">Observations, notes cliniques, et conclusion du diagnostic *</label>
                                    <textarea id="contenuDiagnostic" name="contenu" rows="10" class="form-control" required placeholder="Saisissez ici votre analyse..."></textarea>
                                    <div id="char-counter" class="char-counter">Caractères : 0</div>
                                 </div>
                            </div>

                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-submit-diag btn-lg">
                                    <i class="fas fa-save me-2"></i>Enregistrer le Diagnostic
                                </button>
                            </div>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </main>


<footer>
    © <jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" pattern="yyyy" /> - CarePath. Tous droits réservés.
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Script simple pour le compteur de caractères
    document.addEventListener('DOMContentLoaded', function() {
        const textarea = document.getElementById('contenuDiagnostic');
        const counterElement = document.getElementById('char-counter');

        if (textarea && counterElement) {
            textarea.addEventListener('input', function() {
                counterElement.textContent = `Caractères : ${textarea.value.length}`;
            });
        }
    });
</script>
</body>
</html>