<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CarePath - Mes Informations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #1e3a5f;
            --secondary-color: #7e0021;
            --light-bg: #f2f7fb;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--light-bg);
            color: #333;
            margin: 0;
            padding: 0;
        }
        
        .container {
            max-width: 900px;
            margin: 30px auto;
            background-color: white;
            border-radius: 15px;
            box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }
        
        header {
            background-color: var(--primary-color);
            padding: 20px 60px;
            border-bottom: 4px solid var(--secondary-color);
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            color: white;
        }
        
        .nav-link {
            transition: all 0.3s ease;
            padding: 8px 12px;
            border-radius: 5px;
        }
        
        .nav-link:hover {
            background-color: rgba(255,255,255,0.2);
            transform: translateY(-2px);
        }
        
        .info-card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            padding: 25px;
            margin-bottom: 30px;
        }
        
        .info-label {
            font-weight: 600;
            color: var(--primary-color);
            min-width: 180px;
            display: inline-block;
        }
        
        .info-value {
            color: #555;
        }
        
        .btn-back {
            border-radius: 8px;
            padding: 8px 20px;
            transition: all 0.3s ease;
        }
        
        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        
        footer {
            background-color: var(--primary-color);
            color: white;
            padding: 20px 0;
            text-align: center;
            margin-top: 40px;
        }
    </style>
</head>
<body>

    <!-- Header -->
    <header>
        <div class="container-fluid d-flex align-items-center justify-content-between">
            <!-- Logo et titre -->
            <div class="d-flex align-items-center gap-3">
                <img src="image/image2.png" alt="Logo CarePath" style="height: 50px;">
                <span class="fs-3 fw-bold text-uppercase" style="text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);">
                    CarePath
                </span>
            </div>
            
           <div class="d-flex gap-3">
    <a class="nav-link text-white" href="patient_dashboard.jsp">
        <i class="fas fa-tachometer-alt me-1"></i> Tableau de bord
    </a>
    <a class="nav-link text-white" href="fiche_patient.jsp">
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
                
                <!-- Bouton Déconnexion -->
                <form action="Logout" method="post" class="m-0">
                    <button type="submit" class="btn btn-danger btn-sm">
                        <i class="fas fa-sign-out-alt me-1"></i> Déconnexion
                    </button>
                </form>
            </nav>
        </div>
    </header>

     <div class="container mt-4">
        <div class="card p-4">
            <h2 class="mb-4"><i class="fas fa-user-circle me-2"></i>Mes Informations Personnelles</h2>
            
            <div class="row">
                <div class="col-md-6">
                    <p><span class="info-label"><i class="fas fa-id-card me-2"></i>Nom :</span> 
                       <c:out value="${patient.nom}"/></p>
                    <p><span class="info-label"><i class="fas fa-id-card me-2"></i>Prénom :</span> 
                       <c:out value="${patient.prenom}"/></p>
                    <p><span class="info-label"><i class="fas fa-birthday-cake me-2"></i>Date de naissance :</span> 
                       <fmt:formatDate value="${patient.dateNaissance}" pattern="dd/MM/yyyy"/></p>
                </div>
                <div class="col-md-6">
                    <p><span class="info-label"><i class="fas fa-envelope me-2"></i>Email :</span> 
                       <c:out value="${patient.email}"/></p>
                    <p><span class="info-label"><i class="fas fa-map-marker-alt me-2"></i>Adresse :</span> 
                       <c:out value="${patient.adresse}"/></p>
                </div>
            </div>
            
            <div class="text-center mt-4">
                <a href="patient_dashboard.jsp" class="btn btn-primary">
                    <i class="fas fa-arrow-left me-2"></i> Retour au tableau de bord
                </a>
            </div>
        </div>
    </div>

<footer>
    &copy; 2025 - CarePath. Tous droits réservés.
</footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>