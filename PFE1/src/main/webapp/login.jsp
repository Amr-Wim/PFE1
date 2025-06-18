<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - CarePath</title>
    
    <!-- Dépendances -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <!-- Style personnalisé -->
    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #f4f7f9;
        }

        html, body {
            height: 100%;
        }

        body {
             background-color: #e0f2fe;
            background-image: linear-gradient(135deg, #eaf2f8, #d6e8f6);
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-card {
            width: 100%;
            max-width: 450px;
            background-color: white;
            border-radius: 1rem;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
            overflow: hidden; /* Pour que le header ne dépasse pas */
            border: 1px solid #dee2e6;
        }

        .login-header {
            background-color: var(--main-blue);
            padding: 2rem;
            text-align: center;
        }
        .login-header img {
            height: 50px;
            margin-bottom: 1rem;
        }
        .login-header h2 {
            color: white;
            font-weight: 700;
            margin: 0;
        }

        .login-body {
            padding: 2.5rem;
        }

        .input-group-text {
            background-color: transparent;
            border-right: 0;
            color: #adb5bd;
        }
        .form-control {
            border-left: 0;
            padding-left: 0;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: var(--main-blue);
        }
        
        .btn-login {
            background-color: var(--accent-red);
            border-color: var(--accent-red);
            color: white;
            font-weight: 600;
            padding: 0.75rem;
            border-radius: 0.5rem;
            transition: all 0.3s ease;
        }
        .btn-login:hover {
            background-color: #6a001c;
            border-color: #6a001c;
        }
        
    </style>
</head>
<body>

    <div class="login-card">
        <div class="login-header">
            <img src="${pageContext.request.contextPath}/image/image2.png" alt="Logo CarePath">
            <h2>Bienvenue sur CarePath</h2>
        </div>
        
        <div class="login-body">
            <p class="text-center text-muted mb-4">Veuillez vous connecter pour accéder à votre espace.</p>

            <form action="${pageContext.request.contextPath}/Login" method="post">
                
                <!-- Champ Login -->
                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                    <input type="text" name="login" class="form-control" placeholder="Nom d'utilisateur" required>
                </div>
                
                <!-- Champ Mot de passe -->
                <div class="input-group mb-4">
                     <span class="input-group-text"><i class="fas fa-lock"></i></span>
                    <input type="password" name="mot_de_passe" class="form-control" placeholder="Mot de passe" required>
                </div>
                
                <!-- Bouton de soumission -->
                <div class="d-grid">
                    <button type="submit" class="btn btn-login">
                       <i class="fas fa-sign-in-alt me-2"></i>Se connecter
                    </button>
                </div>
            </form>

            <%-- Affichage des messages d'erreur avec JSTL pour la propreté --%>
            <c:if test="${not empty requestScope.errorMessage}">
                <div class="alert alert-danger mt-4 text-center p-2">
                    ${requestScope.errorMessage}
                </div>
            </c:if>
             <c:if test="${not empty param.error && param.error == 'accesRefuse'}">
                <div class="alert alert-warning mt-4 text-center p-2">
                    Accès refusé. Veuillez vous connecter.
                </div>
            </c:if>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>