<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*, model.Chambre, model.Lit,model.Patient" %>
<%@ page import="dao.LitDAO" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affectation des chambres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header style="background-color: #1e3a5f; padding: 20px 60px; border-bottom: 4px solid #7e0021; box-shadow: 0 4px 12px rgba(0,0,0,0.2); color: white;">
    <div class="container-fluid d-flex align-items-center justify-content-between">
        <!-- Logo et titre -->
        <div class="d-flex align-items-center gap-3">
            <img src="image/image2.png" alt="Logo" style="height: 50px;">
            <span class="fs-3 fw-bold text-uppercase" style="text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);">
                CarePath
            </span>
        </div>
        
          <!-- Liens de navigation affichés côte à côte -->
        <div class="d-flex gap-3">
            <a class="nav-link active text-white" href="${pageContext.request.contextPath}/adminDashboard">Tableau de bord</a>
            <a class="nav-link text-white" href="creerUtilisateur.jsp">Créer utilisateur</a>
            <a class="nav-link text-white" href="changerLit">Modifier les lits </a>
           
             <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
    </div>
</header>

   <div class="container mt-4">
        <h2>Changer le Lit d'un Patient</h2>

        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:out value="${sessionScope.successMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <% session.removeAttribute("successMessage"); %>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:out value="${sessionScope.errorMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                <% session.removeAttribute("errorMessage"); %>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.error}"> <%-- Erreurs spécifiques à la requête --%>
            <div class="alert alert-danger">${requestScope.error}</div>
        </c:if>
         <c:if test="${not empty requestScope.warningMessage}">
            <div class="alert alert-warning">${requestScope.warningMessage}</div>
        </c:if>


        <!-- Section pour rechercher une hospitalisation (par ID patient ou ID hospitalisation) -->
        <div class="card mb-4">
            <div class="card-header">Rechercher Hospitalisation</div>
            <div class="card-body">
                <form method="GET" action="${pageContext.request.contextPath}/changerLit">
                    <input type="hidden" name="action" value="rechercherLits">
                    <div class="mb-3">
                        <label for="hospitalisationIdSearch" class="form-label">ID Hospitalisation :</label>
                        <input type="number" class="form-control" id="hospitalisationIdSearch" name="hospitalisationId">
                    </div>
                    <button type="submit" class="btn btn-primary">Rechercher Lits pour cette Hospitalisation</button>
                </form>
                <hr>
                <form method="GET" action="${pageContext.request.contextPath}/changerLit">
                    <input type="hidden" name="action" value="rechercherHospitalisationPatient"> <%-- Ou directement rechercherLits --%>
                    <div class="mb-3">
                        <label for="patientIdSearch" class="form-label">Ou ID Patient :</label>
                        <input type="number" class="form-control" id="patientIdSearch" name="patientIdPourRechercheLits">
                    </div>
                    <button type="submit" class="btn btn-info">Trouver Hospitalisation Active & Lits</button>
                </form>
            </div>
        </div>


        <c:if test="${not empty hospitalisation && not empty patient}">
            <div class="card">
                <div class="card-header">
                    Détails Hospitalisation ID: <c:out value="${hospitalisation.id}"/> pour Patient: <c:out value="${patient.nom}"/> <c:out value="${patient.prenom}"/> (ID: <c:out value="${patient.id}"/>)
                </div>
                <div class="card-body">
                    <p><strong>Service Hospitalisation:</strong> <c:out value="${hospitalisation.service}"/></p>
                    <p><strong>Motif:</strong> <c:out value="${hospitalisation.motif}"/></p>
                    <p>
                        <strong>Lit Actuel:</strong>
                        <c:choose>
                            <c:when test="${not empty hospitalisation.litId && hospitalisation.litId > 0}">
                                Lit ID <c:out value="${hospitalisation.litId}"/>
                                <c:if test="${not empty hospitalisation.numeroChambre}">
                                    dans Chambre <c:out value="${hospitalisation.numeroChambre}"/>
                                </c:if>
                                <c:if test="${not empty hospitalisation.nomServiceChambre}">
                                    (Service: <c:out value="${hospitalisation.nomServiceChambre}"/>)
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                Aucun lit attribué.
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Sexe Patient:</strong> <c:out value="${patient.sexe}"/></p>
                    <p><strong>Âge Patient:</strong> <c:out value="${patient.age}"/> ans</p>

                    <hr>
                    <h4>Changer de Lit</h4>
                    <form method="POST" action="${pageContext.request.contextPath}/changerLit">
                        <input type="hidden" name="hospitalisationId" value="${hospitalisation.id}">
                        <input type="hidden" name="ancienLitId" value="${not empty hospitalisation.litId ? hospitalisation.litId : 0}">

                        <div class="mb-3">
                            <label for="nouveauLitId" class="form-label">Sélectionner Nouveau Lit Disponible :</label>
                            <c:choose>
                                <c:when test="${not empty litsDisponibles}">
                                    <select class="form-select" id="nouveauLitId" name="nouveauLitId" required>
                                        <option value="">-- Choisir un lit --</option>
                                        <c:forEach var="lit" items="${litsDisponibles}">
                                            <option value="${lit.id}">
                                                Lit ID ${lit.id}
                                                <c:if test="${not empty lit.numeroChambre}">
                                                    (Chambre: ${lit.numeroChambre}
                                                </c:if>
                                                <c:if test="${not empty lit.nomServiceChambre}">
                                                    , Service: ${lit.nomServiceChambre}
                                                </c:if>
                                                <c:if test="${not empty lit.numeroChambre || not empty lit.nomServiceChambre}">)</c:if>
                                                 - Sexe: ${lit.sexeAutorise}, Âge: ${lit.ageMin}-${lit.ageMax}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-danger">Aucun lit compatible disponible actuellement pour ce patient dans ce service.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="mb-3">
                            <label for="raisonChangement" class="form-label">Raison du changement (optionnel):</label>
                            <textarea class="form-control" id="raisonChangement" name="raisonChangement" rows="2"></textarea>
                        </div>

                        <c:if test="${not empty litsDisponibles}">
                            <button type="submit" class="btn btn-success">Attribuer Nouveau Lit</button>
                        </c:if>
                         <a href="${pageContext.request.contextPath}/changerLit" class="btn btn-secondary">Nouvelle Recherche</a>
                    </form>
                </div>
            </div>
        </c:if>
    </div>
<footer>
    &copy; 2025 - CarePath. Tous droits réservés.
</footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>
</html>