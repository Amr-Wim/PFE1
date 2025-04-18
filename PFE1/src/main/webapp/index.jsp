<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - Gestion du Parcours Patient</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        body {
            background: linear-gradient(to right, #6a11cb, #2575fc); /* Gradient */
            font-family: 'Arial', sans-serif;
            color: #fff;
        }

        .role-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        }

        .role-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
        }

        .card-body {
            text-align: center;
        }

        .card-title {
            font-size: 1.5rem;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .btn {
            width: 100%;
            padding: 10px;
            font-size: 1.1rem;
            text-transform: uppercase;
            border-radius: 8px;
        }

        .container {
            margin-top: 10%;
        }

        h1 {
            font-size: 3rem;
            font-weight: bold;
            margin-bottom: 30px;
        }

        p {
            font-size: 1.2rem;
            margin-bottom: 50px;
        }

        .row {
            display: flex;
            justify-content: center;
            gap: 30px;
        }

        .col-md-3 {
            display: flex;
            justify-content: center;
        }
    </style>
</head>
<body>
 <!-- Header -->
   <header>
     <div class="logo-title">

   <span class="title">CarePath</span>
 </div>
   </header>
   
<div class="container text-center">
    <h1>🏥 Plateforme de Gestion du Parcours Patient</h1>
    <p>Veuillez choisir votre rôle pour accéder à l’espace correspondant :</p>

    <div class="row">
        <div class="col-md-3 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">👤 Patient</h5>
                    <a href="patient_dashboard.jsp" class="btn btn-outline-light">Accéder</a>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">🩺 Médecin</h5>
                    <a href="medecin/medecin_dashboard.jsp" class="btn btn-outline-light">Accéder</a>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">💉 Infirmier</h5>
                    <a href="infirmier/infirmier_dashboard.jsp" class="btn btn-outline-light">Accéder</a>
                </div>
            </div>
        </div>
        <div class="col-md-3 mb-4">
            <div class="card role-card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">🛠️ Admin</h5>
                    <a href="admin_dashboard.jsp" class="btn btn-outline-light">Accéder</a>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</body>
</html>
