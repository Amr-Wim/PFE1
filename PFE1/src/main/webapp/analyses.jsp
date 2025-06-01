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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --carepath-blue: #1e3a5f;
            --carepath-red: #7e0021;
            --carepath-light: #f8f9fa;
            --carepath-dark: #212529;
        }
        
        body {
            background-color: #e0f2fe;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
        }
        
        .header {
            background-color: var(--carepath-blue);
            color: white;
            padding: 12px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
         footer {
      background-color: var(--carepath-blue);
      color: #ffffff;
      text-align: center;
      padding: 18px 30px;
      font-size: 14px;
      border-top: 4px solid var(--accent-red);
      font-weight: 500;
      letter-spacing: 0.5px;
    }
        
        .main-container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.05);
            padding: 25px;
            margin-top: 30px;
            margin-bottom: 30px;
        }
        
        .exam-card {
            border-left: 4px solid var(--carepath-red);
            border-radius: 8px;
            margin-bottom: 20px;
            transition: transform 0.3s ease;
        }
        
        .exam-card:hover {
            transform: translateY(-3px);
        }
        
        .exam-header {
            background-color: var(--carepath-blue);
            color: white;
            border-radius: 8px 8px 0 0 !important;
            padding: 15px 20px;
        }
        
        .exam-body {
            padding: 20px;
        }
        
        .exam-footer {
            background-color: #f8f9fa;
            border-top: 1px solid #eee;
            padding: 10px 20px;
            font-size: 0.85rem;
            color: #6c757d;
        }
        
        .badge-status {
            font-size: 0.8rem;
            padding: 5px 10px;
            font-weight: 500;
        }
        
        .ajeun-badge {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeeba;
        }
        
        .nav-pills .nav-link.active {
            background-color: var(--carepath-red);
        }
        
        .sidebar {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        
        .sidebar-title {
            color: var(--carepath-blue);
            border-bottom: 2px solid var(--carepath-red);
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        
        .btn-carepath {
            background-color: var(--carepath-red);
            color: white;
        }
        
        .btn-carepath:hover {
            background-color: #6e001b;
            color: white;
        }
        
        .exam-title {
            font-weight: 600;
            color: var(--carepath-blue);
        }
        
        .exam-meta {
            font-size: 0.9rem;
            color: #6c757d;
        }
        
        .exam-value {
            font-weight: 500;
            color: var(--carepath-dark);
        }
        
        .no-exams {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 30px;
            text-align: center;
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                     <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                    <h1 class="h4 mb-0 ms-3">CAREPATH - Mes Analyses</h1>
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

    <div class="container main-container">
        <div class="row">
            <!-- Sidebar Filters -->
            <div class="col-lg-3 mb-4">
                <div class="sidebar">
                    <h5 class="sidebar-title"><i class="fas fa-filter me-2"></i>Filtrer les analyses</h5>
                    
                    <div class="mb-3">
                        <label class="form-label exam-meta">Statut</label>
                        <select class="form-select mb-3">
                            <option>Tous les statuts</option>
                            <option>Demandé</option>
                            <option>En cours</option>
                            <option>Terminé</option>
                        </select>
                        
                        <label class="form-label exam-meta">Type d'analyse</label>
                        <select class="form-select mb-3">
                            <option>Tous les types</option>
                            <option>Biologie</option>
                            <option>Imagerie</option>
                            <option>Microbiologie</option>
                        </select>
                        
                        <label class="form-label exam-meta">Période</label>
                        <select class="form-select">
                            <option>Toutes périodes</option>
                            <option>Cette semaine</option>
                            <option>Ce mois</option>
                            <option>3 derniers mois</option>
                        </select>
                    </div>
                    
                    <button class="btn btn-carepath w-100 mt-2">
                        <i class="fas fa-check me-1"></i> Appliquer
                    </button>
                </div>
            </div>
            
            <!-- Main Content -->
            <div class="col-lg-9">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="h4 mb-0 exam-title">
                        <i class="fas fa-flask me-2"></i>Mes Examens Demandés
                    </h2>
                    <span class="badge bg-primary">
                        ${fn:length(demandesExamens)} analyses
                    </span>
                </div>
                
                <c:choose>
                    <c:when test="${not empty demandesExamens}">
                        <c:forEach items="${demandesExamens}" var="demande">
                            <div class="exam-card shadow-sm">
                                <div class="exam-header d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5 class="mb-0 text-white">
                                            <c:if test="${not empty demande.examen.typeExamen}">
                                                ${demande.examen.typeExamen.nomType} : 
                                            </c:if>
                                            ${demande.examen.nomExamen}
                                            <c:if test="${demande.examen.doitEtreAJeun}">
                                                <span class="badge ajeun-badge ms-2">À jeun</span>
                                            </c:if>
                                        </h5>
                                    </div>
                                    <span class="badge ${demande.statutDemande == 'Terminé' ? 'bg-success' : 
                                        demanda.statutDemande == 'En cours' ? 'bg-warning' : 'bg-info'} badge-status">
                                        ${demande.statutDemande}
                                    </span>
                                </div>
                                
                                <div class="exam-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <span class="exam-meta"><i class="far fa-calendar-alt me-2"></i>Demandé le :</span>
                                                <p class="exam-value">
                                                    <fmt:formatDate value="${demande.dateDemande}" pattern="dd/MM/yyyy"/>
                                                </p>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <span class="exam-meta"><i class="far fa-clock me-2"></i>Résultats prévus :</span>
                                                <p class="exam-value">
                                                    <fmt:formatDate value="${demande.dateEstimeeResultatsPrets}" pattern="dd/MM/yyyy"/>
                                                    <c:if test="${not empty demande.examen.dureePreparationResultatsHeures}">
                                                        <br>
                                                        <small class="text-muted">(Délai: ${demande.examen.dureePreparationResultatsHeures} heures)</small>
                                                    </c:if>
                                                </p>
                                            </div>
                                        </div>
                                        
                                        <div class="col-md-6">
                                            <c:if test="${not empty demande.examen.instructionsPreparatoires}">
                                                <div class="mb-3">
                                                    <span class="exam-meta"><i class="fas fa-info-circle me-2"></i>Instructions :</span>
                                                    <p class="exam-value">${demande.examen.instructionsPreparatoires}</p>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    
                                    <c:if test="${not empty demande.notesMedecin}">
                                        <div class="alert alert-light mt-3">
                                            <h6 class="alert-heading exam-meta">
                                                <i class="fas fa-comment-medical me-2"></i>Commentaires du médecin
                                            </h6>
                                            <p class="mb-0 exam-value">${demande.notesMedecin}</p>
                                        </div>
                                    </c:if>
                                </div>
                                
                                <div class="exam-footer">
                                    <i class="far fa-calendar me-1"></i>
                                    <fmt:formatDate value="${demande.dateCreation}" pattern="dd/MM/yyyy HH:mm"/>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-exams">
                            <i class="fas fa-flask fa-3x mb-3" style="color: #ddd;"></i>
                            <h4 class="exam-title">Aucun examen demandé</h4>
                            <p class="exam-meta">Vous n'avez aucun examen enregistré pour le moment.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

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