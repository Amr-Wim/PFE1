<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CAREPATH - Mes Analyses</title>
    
    <!-- Dépendances externes -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Style personnalisé -->
    <style>
        /* --- Thème de couleurs et polices --- */
        :root {
            --carepath-blue: #1e3a5f;
            --carepath-red: #7e0021;
            --carepath-bg: #f4f7f9; /* Fond général plus neutre */
            --carepath-card-bg: #ffffff;
            --carepath-text-dark: #2c3e50;
            --carepath-text-muted: #7f8c8d;
            --carepath-success: #27ae60;
            --carepath-warning: #f39c12;
            --carepath-info: #3498db;
        }

        body {
            background-color: var(--carepath-bg);
            font-family: 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
            color: var(--carepath-text-dark);
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        /* --- Composants réutilisables --- */
        .header, footer {
            background-color: var(--carepath-blue);
            color: white;
            border-top: 4px solid var(--carepath-red);
            border-bottom: 4px solid var(--carepath-red);
        }

        main {
            flex-grow: 1;
        }

        /* --- Style de la page --- */
        .main-container {
            padding-top: 2rem;
            padding-bottom: 2rem;
        }
        
        .page-title {
            color: var(--carepath-blue);
            font-weight: 700;
        }
        
        /* Style des cartes d'examen */
        .exam-card {
            background-color: var(--carepath-card-bg);
            border: 1px solid #e9ecef;
            border-radius: 0.75rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            transition: all 0.3s ease-in-out;
            overflow: hidden; /* Pour que le header ne dépasse pas */
        }

        .exam-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.08);
        }
        
        .exam-header {
            background: linear-gradient(135deg, var(--carepath-blue), #2c3e50);
            color: white;
            padding: 1rem 1.5rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .exam-title-in-card {
            font-size: 1.25rem;
            font-weight: 600;
            margin: 0;
        }
        
        .exam-body {
            padding: 1.5rem;
        }

        .exam-meta-label {
            font-size: 0.85rem;
            color: var(--carepath-text-muted);
            text-transform: uppercase;
            font-weight: 600;
            margin-bottom: 0.25rem;
            display: flex;
            align-items: center;
        }
        .exam-meta-label i {
            margin-right: 0.5rem;
            width: 16px; /* Pour aligner les textes */
            text-align: center;
        }

        .exam-value {
            font-size: 1.1rem;
            font-weight: 500;
            color: var(--carepath-text-dark);
            margin-left: 24px; /* Aligner avec le texte après l'icône */
        }
        
        .notes-medecin {
            background-color: #eaf2f8; /* Un bleu très clair */
            border-left: 4px solid var(--carepath-info);
            padding: 1rem;
            border-radius: 0.5rem;
        }
        
        /* Statuts et badges */
        .badge-status {
            padding: 0.4em 0.8em;
            font-size: 0.8rem;
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .badge-status.bg-success { background-color: var(--carepath-success) !important; }
        .badge-status.bg-warning { background-color: var(--carepath-warning) !important; }
        .badge-status.bg-info { background-color: var(--carepath-info) !important; }

        .ajeun-badge {
            background-color: var(--carepath-warning);
            color: white;
            font-size: 0.75rem;
            vertical-align: middle;
        }
        
        /* Message "Aucun Examen" */
        .no-exams {
            background-color: var(--carepath-card-bg);
            border: 2px dashed #dee2e6;
            border-radius: 0.75rem;
            padding: 3rem;
            text-align: center;
            color: var(--carepath-text-muted);
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                     <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                    <h1 class="h4 mb-0 ms-3">CAREPATH </h1>
                </div>
                <div class="d-flex gap-3">
                <a class="nav-link text-white" href="patient_dashboard.jsp">
                    <i class="fas fa-tachometer-alt me-1"></i> Tableau de bord
                </a>
                <a class="nav-link text-white active" href="fiche_patient.jsp">
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
                
                <form action="Logout" method="post" class="m-0">
                    <button type="submit" class="btn btn-danger btn-sm">
                        <i class="fas fa-sign-out-alt me-1"></i> Déconnexion
                    </button>
                </form>
            </div>
            </div>
        </div>
    </header>

      <main class="container main-container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="h3 mb-0 page-title">
                <i class="fas fa-flask-vial me-2"></i>Mes Demandes d'Analyses
            </h2>
            <span class="badge bg-primary rounded-pill fs-6">
                ${fn:length(demandesExamens)} Total
            </span>
        </div>
        
        <c:choose>
            <c:when test="${not empty demandesExamens}">
                <c:forEach items="${demandesExamens}" var="demande">
                    <div class="exam-card">
                        <div class="exam-header d-flex justify-content-between align-items-center">
                            <h3 class="exam-title-in-card">
                                ${demande.examen.nomExamen}
                            </h3>
                            <span class="badge ${demande.statutDemande == 'Terminé' ? 'bg-success' : demanda.statutDemande == 'En cours' ? 'bg-warning' : 'bg-info'} badge-status">
                                <i class="fas ${demande.statutDemande == 'Terminé' ? 'fa-check-circle' : 'fa-hourglass-half'} me-1"></i>
                                ${demande.statutDemande}
                            </span>
                        </div>
                        
                        <div class="exam-body">
                            <div class="row g-4">
                                <!-- Colonne Gauche -->
                                <div class="col-lg-6">
                                    <div class="mb-3">
                                        <p class="exam-meta-label"><i class="far fa-calendar-alt"></i>Demandé le</p>
                                        <p class="exam-value">
                                            <fmt:formatDate value="${demande.dateDemande}" pattern="dd MMMM yyyy"/>
                                        </p>
                                    </div>
                                    <div class="mb-3">
                                        <p class="exam-meta-label"><i class="far fa-calendar-check"></i>Résultats prévus</p>
                                        <p class="exam-value">
                                            <fmt:formatDate value="${demande.dateEstimeeResultatsPrets}" pattern="dd MMMM yyyy"/>
                                        </p>
                                    </div>
                                </div>
                                <!-- Colonne Droite -->
                                <div class="col-lg-6">
                                     <div class="mb-3">
                                        <p class="exam-meta-label"><i class="fas fa-vials"></i>Type d'Analyse</p>
                                        <p class="exam-value">
                                            ${demande.examen.typeExamen.nomType}
                                        </p>
                                    </div>
                                    <div class="mb-3">
                                        <p class="exam-meta-label"><i class="fas fa-info-circle"></i>Instructions</p>
                                        <p class="exam-value">
                                            <c:if test="${demande.examen.doitEtreAJeun}">
                                                <span class="badge ajeun-badge me-2">À jeun</span>
                                            </c:if>
                                            <c:out value="${empty demande.examen.instructionsPreparatoires ? 'Aucune instruction spécifique' : demande.examen.instructionsPreparatoires}" />
                                        </p>
                                    </div>
                                </div>
                            </div>
                            
                            <c:if test="${not empty demande.notesMedecin}">
                                <hr class="my-3">
                                <div class="notes-medecin">
                                    <h6 class="exam-meta-label mb-2">
                                        <i class="fas fa-comment-medical"></i>Commentaires du médecin
                                    </h6>
                                    <p class="mb-0">${demande.notesMedecin}</p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-exams mt-4">
                    <i class="fas fa-inbox fa-3x mb-3 text-black-50"></i>
                    <h4 class="h5">Aucun examen demandé</h4>
                    <p class="text-muted">Vous n'avez aucune demande d'analyse enregistrée pour le moment.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
     <footer>
        &copy; 2025 - CarePath. Tous droits réservés.
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Activation des tooltips Bootstrap
        document.addEventListener('DOMContentLoaded', function() {
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        });
    </script>
</body>
</html>