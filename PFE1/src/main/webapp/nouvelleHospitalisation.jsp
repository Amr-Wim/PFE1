<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Nouvelle Hospitalisation - CarePath</title>
    
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
        
         /* Header et Footer */
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

        main { flex-grow: 1; }

        .form-wizard-container {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
            border: 1px solid var(--border-color);
            overflow: hidden; /* Pour que le header ne dépasse pas */
        }

        .wizard-header {
            background: #1e3a5f;
            color: white;
            padding: 1.5rem 2rem;
            text-align: center;
        }
        .wizard-header h2 {
            margin: 0;
            font-weight: 700;
        }
        .wizard-header p {
            margin: 0;
            opacity: 0.8;
        }

        .wizard-body {
            padding: 2.5rem;
        }

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
            width: 24px; /* Pour aligner les textes */
            text-align: center;
        }

        .form-label {
            font-weight: 500;
            color: var(--text-muted);
        }
        
        /* Style pour les champs en lecture seule */
        .form-control[readonly] {
            background-color: #e9ecef;
            cursor: not-allowed;
            font-weight: 500;
            color: var(--text-dark);
        }

        .btn-submit-hosp {
            background-color: var(--accent-red);
            border: none;
            color: white;
            font-weight: 600;
            padding: 0.8rem 2rem;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        .btn-submit-hosp:hover {
            background-color: #6a001c;
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(126, 0, 33, 0.3);
            color: white;
        }
    </style>
</head>
<body>

  <!-- Header -->
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
<main class="container py-5">
        <div class="mx-auto" style="max-width: 850px;">
            <div class="form-wizard-container">
                <div class="wizard-header">
                    <h2>Nouvelle Hospitalisation</h2>
                    <p>Formulaire d'admission d'un patient</p>
                </div>

                <div class="wizard-body">
                    <%-- Affichage des messages d'erreur/succès --%>
                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-danger mb-4"><i class="fas fa-exclamation-triangle me-2"></i>${requestScope.error}</div>
                    </c:if>
                    <c:if test="${not empty param.success}">
                         <div class="alert alert-success mb-4"><i class="fas fa-check-circle me-2"></i>${param.success}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/creerHospitalisation" method="post">
                        
                        <!-- Section 1: Informations du Contexte -->
                        <div class="form-section">
                            <h4 class="section-title"><i class="fas fa-user-md"></i>Contexte de l'Admission</h4>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label">Médecin traitant</label>
                                    <input type="text" class="form-control" value="Dr. ${sessionScope.medecin.prenom} ${sessionScope.medecin.nom}" readonly>
                                    <input type="hidden" name="idMedecin" value="${sessionScope.medecin.id}">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Hôpital</label>
                                    <input type="text" class="form-control" name="nomHopital" value="${sessionScope.medecin.nomHopital}" readonly>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Service d'admission</label>
                                    <input type="text" class="form-control" name="service" value="${sessionScope.medecin.nomService}" readonly>
                                </div>
                            </div>
                        </div>

                        <!-- Section 2: Sélection du Patient -->
                        <div class="form-section">
                             <h4 class="section-title"><i class="fas fa-user-injured"></i>Identification du Patient</h4>
                             <div class="mb-3">
                                <label for="idPatient" class="form-label">Sélectionner le patient *</label>
                                <select class="form-select form-select-lg" id="idPatient" name="idPatient" required>
                                    <option value="" disabled selected>-- Cliquez pour choisir un patient dans la liste --</option>
                                    <c:forEach var="patient" items="${patientsList}">
                                        <option value="${patient.id}">${patient.prenom} ${patient.nom} (CIN: ${patient.cin})</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <!-- Section 3: Détails de l'Hospitalisation -->
                        <div class="form-section">
                            <h4 class="section-title"><i class="fas fa-file-medical"></i>Détails de l'Hospitalisation</h4>
                            <div class="row g-3">
                                <div class="col-md-12">
                                    <label for="motif" class="form-label">Motif d'hospitalisation *</label>
                                    <textarea class="form-control" id="motif" name="motif" rows="3" required placeholder="Ex: Douleurs thoraciques, Bilan pré-opératoire..."></textarea>
                                </div>
                                <div class="col-md-12">
                                    <label for="diagnosticInitial" class="form-label">Diagnostic initial (si connu)</label>
                                    <textarea class="form-control" id="diagnosticInitial" name="diagnosticInitial" rows="2" placeholder="Ex: Suspicion d'infarctus du myocarde..."></textarea>
                                </div>
                                <div class="col-md-6">
                                    <label for="duree" class="form-label">Durée prévue *</label>
                                    <select class="form-select" id="duree" name="duree" required>
                                        <option value="" disabled selected>-- Sélectionner une durée --</option>
                                        <option value="1 jour">1 Jour</option>
                                        <option value="2 jours">2 Jours</option>
                                        <option value="3 jours">3 Jours</option>
                                        <option value="5 jours">5 Jours</option>
                                        <option value="1 semaine">1 Semaine</option>
                                        <option value="2 semaines">2 Semaines</option>
                                        <option value="1 mois">1 Mois</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        
                        <div class="text-center mt-4">
                            <button type="submit" class="btn btn-submit-hosp btn-lg">
                                <i class="fas fa-check-circle me-2"></i> Valider et Trouver un Lit
                            </button>
                        </div>
                    </form>
                </div>
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