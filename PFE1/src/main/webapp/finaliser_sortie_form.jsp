<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- On prépare une variable 'now' pour la date par défaut --%>
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Préparer la Sortie - CarePath</title>
    
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
            --success-color: #198754;
        }

        body {
            background-color: #e0f2fe;
            font-family: 'Segoe UI', sans-serif;
            color: var(--text-dark);
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
        

        .form-wizard-container {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
            border: 1px solid var(--border-color);
        }

        .wizard-header {
           background: #1e3a5f;
            color: white;
            padding: 1.5rem 2rem;
            text-align: center;
            border-top-left-radius: 12px;
            border-top-right-radius: 12px;
        }
        .wizard-header h2 { margin: 0; font-weight: 700; }

        .wizard-body { padding: 2.5rem; }

        .patient-recap-card {
            background-color: #eaf2f8; /* Bleu très clair */
            border: 1px solid #bde0fe;
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 2rem;
        }
        .patient-name { font-size: 1.5rem; font-weight: 600; color: var(--main-blue); }
        .info-label { font-size: 0.9rem; color: var(--text-muted); }

        .section-title {
            color: var(--accent-red);
            font-weight: 600;
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            font-size: 1.25rem;
        }
        .section-title i { margin-right: 0.75rem; width: 24px; text-align: center; }

        .form-label { font-weight: 500; }
        
        .btn-submit-sortie {
            background-color: var(--success-color);
            border-color: var(--success-color);
            color: white;
            font-weight: 600;
            padding: 0.8rem 2rem;
            border-radius: 50px;
            transition: all 0.3s ease;
        }
        .btn-submit-sortie:hover {
            background-color: #146c43;
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(25, 135, 84, 0.3);
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

     <main class="container py-5">
        <div class="mx-auto" style="max-width: 900px;">
            <div class="form-wizard-container">
                <div class="wizard-header">
                    <h2>Finalisation du Dossier de Sortie</h2>
                </div>
                
                <div class="wizard-body">
                     <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <!-- Récapitulatif du Patient -->
                    <div class="patient-recap-card">
                        <div class="row align-items-center">
                            <div class="col-md-8">
                                <h4 class="patient-name">${patient.prenom} ${patient.nom}</h4>
                                <span class="info-label">Hospitalisation N°${hospitalisation.id}</span>
                            </div>
                            <div class="col-md-4 text-md-end">
                                <span class="info-label">Admission le:</span>
                                <div class="fw-bold"><fmt:formatDate value="${hospitalisation.dateAdmission}" pattern="dd/MM/yyyy"/></div>
                            </div>
                        </div>
                    </div>
                    
                    <form method="POST" action="${pageContext.request.contextPath}/preparerSortie">
                        <input type="hidden" name="hospitalisationId" value="${hospitalisation.id}">

                        <!-- Section 1: Bilan Clinique -->
                        <div class="mb-4">
                            <h5 class="section-title"><i class="fas fa-file-medical-alt"></i>Bilan Clinique de Sortie</h5>
                            <div class="mb-3">
                                <label for="diagnosticsSortie" class="form-label">Diagnostics de Sortie *</label>
                                <textarea class="form-control" id="diagnosticsSortie" name="diagnosticsSortie" rows="2" required placeholder="Ex: Infarctus du myocarde ST+, HTA essentielle...">${hospitalisation.diagnosticsSortie}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="compteRendu" class="form-label">Compte-Rendu d'Hospitalisation *</label>
                                <textarea class="form-control" id="compteRendu" name="compteRendu" rows="5" required placeholder="Résumé du séjour, évolution, examens clés, état du patient à la sortie...">${hospitalisation.compteRenduHospitalisation}</textarea>
                            </div>
                        </div>

                        <hr class="my-4">

                        <!-- Section 2: Instructions et Suivi -->
                        <div class="mb-4">
                             <h5 class="section-title"><i class="fas fa-hand-holding-medical"></i>Instructions et Suivi</h5>
                             <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="dateSortieReelle" class="form-label">Date et Heure de Sortie Effective *</label>
                                    <input type="datetime-local" class="form-control" id="dateSortieReelle" name="dateSortieReelle" required>
                                </div>
                                <div class="col-md-6">
                                     <label for="infoRdvSuivi" class="form-label">Rendez-vous de Suivi</label>
                                    <input type="text" class="form-control" id="infoRdvSuivi" name="infoRdvSuivi" value="${hospitalisation.informationsRendezVousSuivi}" placeholder="Ex: Dr. Dupont, le 15/07 à 10h...">
                                </div>
                                <div class="col-12">
                                    <label for="instructionsSortie" class="form-label">Instructions pour le Patient (traitement, repos, etc.) *</label>
                                    <textarea class="form-control" id="instructionsSortie" name="instructionsSortie" rows="4" required placeholder="Détail du traitement médicamenteux, repos, régime, signes à surveiller...">${hospitalisation.instructionsSortie}</textarea>
                                </div>
                             </div>
                        </div>

                        <div class="text-center mt-5">
                            <button type="submit" class="btn btn-submit-sortie btn-lg">
                                <i class="fas fa-check-circle me-2"></i>Finaliser la Sortie et Générer le Dossier
                            </button>
                             <a href="${pageContext.request.contextPath}/medecin/mesHospitalisations" class="btn btn-secondary ms-2">Annuler</a>
                             
                        </div>
                        
                        <div class="text-center mt-5">
            <a href="${pageContext.request.contextPath}/medecin_dashboard.jsp" class="btn btn-custom-primary btn-lg">
                <i class="fas fa-arrow-left me-2"></i> Retour au Tableau de Bord
            </a>
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
    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Script pour pré-remplir la date et l'heure actuelles
        document.addEventListener('DOMContentLoaded', function() {
            const dateInput = document.getElementById('dateSortieReelle');
            if(dateInput && !dateInput.value) { // Ne remplit que si le champ est vide
                const now = new Date();
                now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
                const formattedNow = now.toISOString().slice(0,16);
                dateInput.value = formattedNow;
            }
        });
    </script>
</body>
</html>