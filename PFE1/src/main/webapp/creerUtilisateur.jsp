<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>créer compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="css/styles.css">
            
           
 <style>
.bg-logo {
    background-color:  #1e3a5f; /* Bleu nuit du logo */
    color: #fdf5e6;            /* Texte crème */
}</style>

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
 
    <div class="container mt-5">
    <h2>Création d’un compte Patient</h2>

    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger text-center mt-3 alert-dismissible fade show" role="alert">
            ${requestScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.successMessage}">
        <div class="alert alert-success text-center mt-3 alert-dismissible fade show" role="alert">
            ${requestScope.successMessage}
             <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="card shadow-sm">
        <div class="card-header bg-logo">
            Formulaire de création de compte Patient
        </div>
        <div class="card-body">
            <form method="POST" action="${pageContext.request.contextPath}/CreerUser">
                <input type="hidden" name="role" value="patient">

                <!-- Informations de base Utilisateur -->
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="nom" class="form-label">Nom *</label>
                        <input type="text" class="form-control" id="nom" name="nom" required>
                    </div>
                    <div class="col-md-6">
                        <label for="prenom" class="form-label">Prénom *</label>
                        <input type="text" class="form-control" id="prenom" name="prenom" required>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="email" class="form-label">Email *</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="col-md-6">
                        <label for="cin" class="form-label">CIN</label> <!-- Champ CIN ajouté -->
                        <input type="text" class="form-control" id="cin" name="cin" maxlength="20">
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="login" class="form-label">Nom d'utilisateur *</label>
                        <input type="text" class="form-control" id="login" name="login" required>
                    </div>
                    <div class="col-md-6">
                        <label for="mdp" class="form-label">Mot de passe *</label>
                        <input type="password" class="form-control" id="mdp" name="mdp" required>
                    </div>
                </div>

                <!-- Informations Spécifiques Patient -->
                <h5>Informations Complémentaires Patient</h5>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="date_naissance" class="form-label">Date de naissance *</label>
                        <input type="date" class="form-control" id="date_naissance" name="date_naissance" required>
                    </div>
                    <div class="col-md-6">
                        <label for="taille" class="form-label">Taille (cm)</label>
                        <input type="number" class="form-control" id="taille" name="taille" min="0">
                    </div>
                </div>
               <div class="col-md-4">
    <label class="form-label">Sexe</label>
    <div class="d-flex gap-3"> <!-- Alignement horizontal -->
        <!-- Option Masculin -->
        <div class="form-check">
            <input class="form-check-input" type="radio" name="sexe" id="sexe_masculin" value="Masculin">
            <label class="form-check-label" for="sexe_masculin">Masculin</label>
        </div>
        
        <!-- Option Féminin -->
        <div class="form-check">
            <input class="form-check-input" type="radio" name="sexe" id="sexe_feminin" value="Féminin">
            <label class="form-check-label" for="sexe_feminin">Féminin</label>
        </div>
    </div>
</div>
                <div class="mb-3">
                    <label for="adresse" class="form-label">Adresse *</label>
                    <textarea class="form-control" id="adresse" name="adresse" rows="3" required></textarea>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="poids" class="form-label">Poids (kg)</label>
                        <input type="number" step="0.01" class="form-control" id="poids" name="poids" min="0">
                    </div>
                    <div class="col-md-6">
    <label for="groupe_sanguin" class="form-label">Groupe Sanguin</label>
    <select class="form-control" id="groupe_sanguin" name="groupe_sanguin">
        <option value="">-- Sélectionnez votre groupe --</option>
        <option value="A+">A+</option>
        <option value="A-">A-</option>
        <option value="B+">B+</option>
        <option value="B-">B-</option>
        <option value="AB+">AB+</option>
        <option value="AB-">AB-</option>
        <option value="O+">O+</option>
        <option value="O-">O-</option>
        <option value="Inconnu">Inconnu</option>
    </select>
</div>
                </div>
               <div class="mb-3">
    <label for="assurance_medicale" class="form-label">Assurance Médicale</label>
    <select class="form-control" id="assurance_medicale" name="assurance_medicale">
        <option value="">-- Sélectionnez une assurance --</option>
        
        <!-- Assurances Publiques -->
        <optgroup label="Assurances Publiques">
            <option value="CNSS">CNSS (Caisse Nationale de Sécurité Sociale)</option>
            <option value="CNOPS">CNOPS (Caisse Nationale des Organismes de Prévoyance Sociale)</option>
        </optgroup>
        
        <!-- Assurances Privées -->
        <optgroup label="Assurances Privées">
            <option value="AXA">AXA Assurance Maroc</option>
            <option value="RMA">RMA Watanya</option>
            <option value="Sanad">Sanad (Groupe CDG)</option>
            <option value="AtlantaSanad">AtlantaSanad</option>
            <option value="Allianz">Allianz Maroc</option>
            <option value="Sanlam">Sanlam Maroc (ex-Saham)</option>
            <option value="Wafa">Wafa Assurance</option>
            <option value="Takafoul">Mutuelle Takafoul (Islamique)</option>
            <option value="Zurich">Zurich Maroc</option>
            <option value="MAMDA">MAMDA-MCMA</option>
            <option value="La Marocaine Vie">La Marocaine Vie</option>
        </optgroup>
        
        <!-- Mutuelles & Spécialisées -->
        <optgroup label="Mutuelles et Spécialisées">
            <option value="FAR">FAR (Risques professionnels)</option>
            <option value="MGPAP">MGPAP (Fonctionnaires)</option>
        </optgroup>
        
        <!-- Internationales -->
        <optgroup label="Assurances Internationales">
            <option value="Cigna">Cigna Global</option>
            <option value="Allianz International">Allianz Worldwide Care</option>
            <option value="Aetna">Aetna International</option>
        </optgroup>
        
        <!-- Autre option -->
        <option value="autre">Autre (précisez)</option>
    </select>
</div>

<!-- Champ caché pour "Autre" -->
<div class="mb-3" id="autre_assurance_container" style="display: none;">
    <label for="autre_assurance" class="form-label">Précisez votre assurance</label>
    <input type="text" class="form-control" id="autre_assurance" name="autre_assurance">
</div>
                <div class="mb-3">
                    <label for="numero_assurance" class="form-label">Numéro d'Assurance</label>
                    <input type="text" class="form-control" id="numero_assurance" name="numero_assurance" maxlength="50">
                </div>
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg">Créer le compte Patient</button>
                </div>
            </form>
        </div>
    </div>
    </div>
    <script>
    document.getElementById('assurance_medicale').addEventListener('change', function() {
        const autreContainer = document.getElementById('autre_assurance_container');
        autreContainer.style.display = (this.value === 'autre') ? 'block' : 'none';
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>