<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Login - Hospital Management</title>
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
     </div>
     </header>

<div class="container mt-5">
    <h2 class="text-center mb-4">Se connecter </h2>
    <br>
     <br>

    <div class="row justify-content-center">
        <div class="col-md-6">
            <form action="Login" method="post" class="form-signin">
    
    <input type="text" name="login" placeholder="Nom d'utilisateur" required class="form-control mb-3">
    <input type="password" name="mot_de_passe" placeholder="Mot de passe" required class="form-control mb-3">
    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
</form>
<% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger mt-4 text-center">
        <%= request.getAttribute("errorMessage") %>
    </div>
<% } %>


        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>
</html>
