<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>CarePath - Mon Hospitalisation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <%-- Adapte le chemin vers ton fichier CSS principal pour le patient si tu en as un --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #e0f2fe;
            --text-dark: #333;
            --text-muted-custom: #555;
        }
        body {
            background-color: var(--bg-light);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: var(--text-dark);
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin:0;
        }
        /* Adapte le header et footer avec tes jsp:include ou copie le style direct ici */
        header {
            background-color: var(--main-blue);
            color: white;
            padding: 1rem 0;
            border-bottom: 4px solid var(--accent-red);
        }
        header .navbar-brand img { height: 40px; }
        header .nav-link { color: rgba(255,255,255,.8); }
        header .nav-link:hover, header .nav-link.active { color: white; }
        header .btn-danger { background-color: var(--accent-red); border-color: var(--accent-red); }

        main {
            flex: 1;
            padding-top: 2rem;
            padding-bottom: 2rem;
        }
        .details-container {
            background-color: #fff;
            border-radius: 0.75rem;
            padding: 2rem;
            box-shadow: 0 0.5rem 1.5rem rgba(0,0,0,0.1);
            border-top: 5px solid var(--main-blue);
        }
        .details-title {
            color: var(--main-blue);
            font-weight: 700;
            margin-bottom: 1.5rem;
            text-align: center;
            border-bottom: 2px solid var(--bg-light);
            padding-bottom: 0.75rem;
        }
        .info-grid {
            display: grid;
            grid-template-columns: 1fr; /* Une colonne par défaut */
            gap: 1rem;
        }
        @media (min-width: 768px) { /* Deux colonnes sur écrans moyens et plus grands */
            .info-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }
        .info-item {
            padding: 0.75rem;
            background-color: #f8f9fa;
            border-radius: 0.375rem;
            border-left: 4px solid var(--main-blue);
        }
        .info-label {
            font-weight: 600;
            color: var(--main-blue);
            display: block;
            margin-bottom: 0.25rem;
        }
        .info-value {
            color: var(--text-muted-custom);
        }
        .section-divider {
            margin-top: 2rem;
            margin-bottom: 1.5rem;
            border-top: 1px solid #dee2e6;
        }
        .btn-custom-primary {
            background-color: var(--main-blue);
            border-color: var(--main-blue);
            color: white;
        }
        .btn-custom-primary:hover {
            background-color: #162a42;
            border-color: #162a42;
        }
        footer {
            background-color: var(--main-blue);
            color: white;
            padding: 1rem 0;
            text-align: center;
            font-size: 0.9rem;
            border-top: 4px solid var(--accent-red);
        }
    </style>
</head>
<body>

    <%-- Inclure ton header patient ici. Exemple: --%>
    <!-- Header -->
   <header style="background-color: #1e3a5f;">
        <div class="container-fluid">
            <div class="d-flex align-items-center justify-content-between py-2">
                <!-- Logo et titre -->
                <div class="d-flex align-items-center gap-3">
                    <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                    <span class="fs-3 fw-bold text-white text-uppercase" style="letter-spacing: 1px; text-shadow: 0 2px 3px rgba(0,0,0,0.3);">
                        CarePath
                    </span>
                </div>
                
                <!-- Menu et infos utilisateur -->
                <div class="d-flex align-items-center gap-4">
                    <!-- Navigation principale -->
                    <nav class="d-flex align-items-center gap-3 me-4">
                        <a class="nav-link text-white active hover-effect" href="patient_dashboard.jsp">
                            <i class="fas fa-tachometer-alt me-1"></i>
                            <span class="d-none d-lg-inline">Tableau de bord</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="fiche_patient.jsp">
                            <i class="fas fa-user me-1"></i>
                            <span class="d-none d-lg-inline">Informations</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="dossier_medical.jsp">
                            <i class="fas fa-file-medical me-1"></i>
                            <span class="d-none d-lg-inline">Dossier médical</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="patient/hospitalisation">
                            <i class="fas fa-procedures me-1"></i>
                            <span class="d-none d-lg-inline">Hospitalisation</span>
                        </a>
                        <a class="nav-link text-white hover-effect" href="analyses.jsp">
                            <i class="fas fa-flask me-1"></i>
                            <span class="d-none d-lg-inline">Analyses</span>
                        </a>
                    </nav>
                    
                   
                        <!-- Bouton Déconnexion -->
                        <form action="Logout" method="post" class="m-0">
                             <button type="submit" class="btn btn-danger btn-sm">
                        <i class="fas fa-sign-out-alt me-1"></i> Déconnexion
                                
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
   </header>
 

   <%-- Supposons que cette JSP se trouve dans /WEB-INF/jsp/patient/hospitalisation_details.jsp --%>
<%-- Ou si elle est à la racine, ajuste les chemins des includes et CSS --%>

<main class="container mt-4 mb-5">
    <div class="details-container">
        <h2 class="details-title">
            <i class="fas fa-notes-medical me-2"></i>Détails de Mon Hospitalisation
        </h2>

        

        <c:choose>
            <c:when test="${not empty sessionScope.hospitalisation}">
                <h4>Informations Générales</h4>
                <div class="info-grid mb-4">
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-hospital me-1"></i>Hôpital :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.nomHopital}"/></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-clinic-medical me-1"></i>Service (Admission) :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.service}"/></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-user-md me-1"></i>Médecin traitant :</span>
                        <span class="info-value">
                            <c:if test="${not empty sessionScope.hospitalisation.medecin}">
                                Dr. <c:out value="${sessionScope.hospitalisation.medecin.prenom}"/> <c:out value="${sessionScope.hospitalisation.medecin.nom}"/>
                                <c:if test="${not empty sessionScope.hospitalisation.medecin.specialite}">
                                    (<c:out value="${sessionScope.hospitalisation.medecin.specialite}"/>)
                                </c:if>
                            </c:if>
                            <c:if test="${empty sessionScope.hospitalisation.medecin && not empty sessionScope.hospitalisation.idMedecin}">
                                Information non complètement chargée (ID: ${sessionScope.hospitalisation.idMedecin})
                            </c:if>
                             <c:if test="${empty sessionScope.hospitalisation.medecin && empty sessionScope.hospitalisation.idMedecin}">
                                Non spécifié
                            </c:if>
                        </span>
                    </div>
                     <div class="info-item">
                        <span class="info-label"><i class="fas fa-sign-in-alt me-1"></i>Date d'admission :</span>
                        <span class="info-value"><fmt:formatDate value="${sessionScope.hospitalisation.dateAdmission}" pattern="dd/MM/yyyy"/></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-clock me-1"></i>Durée prévue :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.duree}"/></span>
                    </div>
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-calendar-day me-1"></i>Sortie prévue le :</span>
                        <span class="info-value">
                            <c:if test="${not empty sessionScope.hospitalisation.dateSortiePrevue}">
                                <fmt:formatDate value="${sessionScope.hospitalisation.dateSortiePrevue}" pattern="dd/MM/yyyy"/>
                            </c:if>
                            <c:if test="${empty sessionScope.hospitalisation.dateSortiePrevue}">Non définie</c:if>
                        </span>
                    </div>
                     <div class="info-item">
                        <span class="info-label"><i class="fas fa-sign-out-alt me-1"></i>Date de sortie réelle :</span>
                        <span class="info-value">
                            <c:if test="${not empty sessionScope.hospitalisation.dateSortieReelle}">
                                <fmt:formatDate value="${sessionScope.hospitalisation.dateSortieReelle}" type="both" dateStyle="medium" timeStyle="short"/>
                            </c:if>
                            <c:if test="${empty sessionScope.hospitalisation.dateSortieReelle && sessionScope.hospitalisation.etat.equalsIgnoreCase('Sortie')}">Non enregistrée</c:if>
                            <c:if test="${not sessionScope.hospitalisation.etat.equalsIgnoreCase('Sortie')}">Pas encore sorti(e)</c:if>
                        </span>
                    </div>
                    <div class="info-item">
                        <span class="info-label"><i class="fas fa-info-circle me-1"></i>État actuel :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.etat}"/></span>
                    </div>
                </div>

                <c:if test="${not empty sessionScope.hospitalisation.litId}">
                    <h4>Lieu d'Hébergement</h4>
                    <div class="info-grid mb-4">
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-bed me-1"></i>Lit :</span>
                            <span class="info-value"><c:out value="${sessionScope.hospitalisation.numeroLit}"/></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-door-open me-1"></i>Chambre :</span>
                            <span class="info-value"><c:out value="${sessionScope.hospitalisation.numeroChambre}"/></span>
                        </div>
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-hospital-symbol me-1"></i>Service de la chambre :</span>
                            <span class="info-value"><c:out value="${sessionScope.hospitalisation.nomServiceChambre}"/></span>
                        </div>
                    </div>
                </c:if>
                 <c:if test="${empty sessionScope.hospitalisation.litId}">
                     <div class="info-item">
                        <span class="info-label"><i class="fas fa-bed me-2"></i>Lit/Chambre :</span>
                        <span class="info-value">Information non disponible pour le moment</span>
                    </div>
                </c:if>

                <h4>Motif et Diagnostic Initial</h4>
                <div class="info-grid mb-4">
                    <div class="info-item" style="grid-column: 1 / -1;">
                        <span class="info-label"><i class="fas fa-file-medical-alt me-1"></i>Motif d'admission :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.motif}"/></span>
                    </div>
                    <div class="info-item" style="grid-column: 1 / -1;">
                        <span class="info-label"><i class="fas fa-stethoscope me-1"></i>Diagnostic initial :</span>
                        <span class="info-value"><c:out value="${sessionScope.hospitalisation.diagnosticInitial}"/></span>
                    </div>
                </div>

                <%-- Section Dossier de Sortie --%>
                <c:if test="${not empty sessionScope.hospitalisation && sessionScope.hospitalisation.etat.equalsIgnoreCase('Sortie')}">
                    <hr class="section-divider">
                    <h4><i class="fas fa-file-pdf me-2"></i>Dossier de Sortie</h4>
                    <c:choose>
                        <c:when test="${requestScope.dossierSortieDisponible == true}">
                            <p>Votre dossier de sortie complet est disponible.</p>
                            <a href="${pageContext.request.contextPath}/patient/telechargerDossier?hospitalisationId=${sessionScope.hospitalisation.id}"
                               class="btn btn-lg btn-success"
                               target="_blank">
                                <i class="fas fa-download me-2"></i>Télécharger mon Dossier de Sortie (PDF)
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning">
                                Votre dossier de sortie est en cours de préparation ou n'a pas encore été généré.
                                (DEBUG JSP: dossierSortieDisponible est [${requestScope.dossierSortieDisponible}])
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:if>

            </c:when>
            <c:otherwise>
                <div class="alert alert-info text-center">
                    <i class="fas fa-info-circle me-2"></i>Vous n'avez aucune hospitalisation à afficher pour le moment.
                </div>
            </c:otherwise>
        </c:choose>

        <div class="text-center mt-5">
            <a href="${pageContext.request.contextPath}/patient_dashboard.jsp" class="btn btn-custom-primary btn-lg">
                <i class="fas fa-arrow-left me-2"></i> Retour au Tableau de Bord
            </a>
        </div>
    </div>
</main>

    <%-- Inclure ton footer patient ici. Exemple: --%>
    <footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>