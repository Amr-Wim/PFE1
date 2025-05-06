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
        <div class="d-flex gap-3">
            <a class="nav-link active text-white" href="admin_dashboard.jsp">Tableau de bord</a>
            <a class="nav-link text-white" href="creerUtilisateur.jsp">Créer utilisateur</a>
            <a class="nav-link text-white" href="Affectation">Affectation des chambres</a>
            <a class="nav-link text-white" href="planification_sortie.jsp">Planification des sorties</a>
             <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
    </div>
</header>

    <div class="container mt-5">
    <h2 > Création d’un compte utilisateur</h2>
    
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger text-center mt-3">
        <%= request.getAttribute("errorMessage") %>
    </div>
<% } %>
<% if (request.getAttribute("successMessage") != null) { %>
<div class="alert alert-success text-center mt-3">
    <%= request.getAttribute("successMessage") %>
</div>
<% } %>
    

    <div class="card shadow-sm">
        <div class="card">
    <div class="card-header bg-logo">
        Formulaire de création de compte
    </div>
        <div class="card-body">
        
<body>
<div class="container">
    <div class="form-container">
        <h2 class="form-title">Création de compte</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <form method="POST" action="${pageContext.request.contextPath}/CreerUser">
            <!-- Informations de base -->
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
            
            <div class="mb-3">
                <label for="email" class="form-label">Email *</label>
                <input type="email" class="form-control" id="email" name="email" required>
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
            
            <div class="mb-3">
                <label for="role" class="form-label">Rôle *</label>
                <select class="form-select" id="role" name="role" required>
                    <option value="">-- Sélectionner un rôle --</option>
                    <option value="patient">Patient</option>
                    <option value="medecin">Médecin</option>
                    <option value="infirmier">Infirmier</option>
                    <option value="admin">Administrateur</option>
                </select>
            </div>
            
            <!-- Champs spécifiques selon le rôle -->
            <div id="champ-medecin" class="role-specific" style="display: none;">
                <h5>Informations Médecin</h5>
                <div class="mb-3">
                    <label for="specialite" class="form-label">Spécialité *</label>
                    <select class="form-select" id="specialite" name="specialite">
                        <option value="">-- Sélectionner une spécialité --</option>
                        <option value="Cardiologie">Cardiologie</option>
                        <option value="Pédiatrie">Pédiatrie</option>
                        <option value="Gynécologie">Gynécologie</option>
                        <option value="Chirurgie">Chirurgie</option>
                        <option value="Neurologie">Neurologie</option>
                        <option value="Dermatologie">Dermatologie</option>
                    </select>
                </div>
            </div>
            
            <div id="champ-patient" class="role-specific" style="display: none;">
                <h5>Informations Patient</h5>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="date_naissance" class="form-label">Date de naissance *</label>
                        <input type="date" class="form-control" id="date_naissance" name="date_naissance">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="adresse" class="form-label">Adresse *</label>
                    <textarea class="form-control" id="adresse" name="adresse" rows="3"></textarea>
                </div>
            </div>
            
            <div id="champ-infirmier" class="role-specific" style="display: none;">
                <h5>Informations Infirmier</h5>
                <div class="mb-3">
                    <label for="service" class="form-label">Service *</label>
                    <select class="form-select" id="service" name="service">
                        <option value="">-- Sélectionner un service --</option>
                        <option value="Urgences">Urgences</option>
                        <option value="Pédiatrie">Pédiatrie</option>
                        <option value="Réanimation">Réanimation</option>
                        <option value="Oncologie">Oncologie</option>
                        <option value="Bloc opératoire">Bloc opératoire</option>
                    </select>
                </div>
            </div>
            
            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary btn-lg">Créer le compte</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Gestion de l'affichage des champs spécifiques
    document.getElementById('role').addEventListener('change', function() {
        // Masquer tous les champs spécifiques
        document.querySelectorAll('.role-specific').forEach(el => {
            el.style.display = 'none';
            // Rendre les champs non obligatoires temporairement
            el.querySelectorAll('input, select, textarea').forEach(field => {
                field.required = false;
            });
        });
        
        // Afficher les champs correspondants au rôle sélectionné
        const role = this.value;
        if (role === 'medecin') {
            document.getElementById('champ-medecin').style.display = 'block';
            document.getElementById('specialite').required = true;
        } else if (role === 'patient') {
            document.getElementById('champ-patient').style.display = 'block';
            document.getElementById('date_naissance').required = true;
            document.getElementById('adresse').required = true;
        } else if (role === 'infirmier') {
            document.getElementById('champ-infirmier').style.display = 'block';
            document.getElementById('service').required = true;
        }
    });
    
    // Validation avant soumission
    document.querySelector('form').addEventListener('submit', function(e) {
        const role = document.getElementById('role').value;
        let valid = true;
        
        // Validation des champs spécifiques
        if (role === 'medecin' && !document.getElementById('specialite').value) {
            alert('Veuillez sélectionner une spécialité');
            valid = false;
        } 
        // Ajouter des validations similaires pour patient et infirmier
        
        if (!valid) {
            e.preventDefault();
        }
    });
</script>
</body>
</html>