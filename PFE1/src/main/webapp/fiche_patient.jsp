<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
<style>
body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f2f7fb;
    color: #333;
    margin: 0;
    padding: 0;
}

.container {
    max-width: 900px;
    margin: 50px auto;
    background-color: white;
    border-radius: 15px;
    box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.1);
    padding: 30px;
}

h2 {
    color: #0d6efd;
    font-weight: 600;
}

.card {
    border: none;
    border-radius: 12px;
}

.card span {
    font-weight: 500;
}

.btn-outline-primary {
    border-radius: 8px;
    font-weight: 500;
    transition: all 0.3s ease-in-out;
}

.btn-outline-primary:hover {
    background-color: #0d6efd;
    color: white;
    transform: scale(1.03);
}

.row {
    margin-bottom: 15px;
}
</style>
</head>
<body>

 <!-- Header -->
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
            <a class="nav-link active text-white" href="patient_dashboard.jsp">Tableau de bord</a>
            <a class="nav-link text-white" href="fiche_patient.jsp">Informations personnelles</a>
            <a class="nav-link text-white" href="rendezvous.jsp">Rendez vous</a>
            <a class="nav-link text-white" href="parcours_patient.jsp">Parcours</a>
                        <a class="nav-link text-white" href="bon_sortie.jsp">Bon sortie</a>
                                    <a class="nav-link text-white" href="resultats_analyses.jsp">Résultats d'éxamens</a>
        </div>
 </div>
   </header>
 



<div class="container mt-5">
    <div class="card shadow-lg p-4">
        <h2 class="text-primary mb-4">Mes Informations Personnelles</h2>
        <div class="row mb-3">
            <div class="col-md-6">
                <strong>Nom :</strong> <span>El Amimri</span>
            </div>
            <div class="col-md-6">
                <strong>Prénom :</strong> <span>Wiame</span>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-md-6">
                <strong>Date de naissance :</strong> <span>2001-08-14</span>
            </div>
            <div class="col-md-6">
                <strong>Sexe :</strong> <span>Féminin</span>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-md-6">
                <strong>Email :</strong> <span>wiame@gmail.com</span>
            </div>
            <div class="col-md-6">
                <strong>Téléphone :</strong> <span>+212 6 12 34 56 78</span>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-md-12">
                <strong>Adresse :</strong> <span>Rue des Lilas, Fès, Maroc</span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 text-end">
       
            </div>
        </div>
    </div>
    
    <div class="text-center mt-4">
            <a href="patient_dashboard.jsp" class="btn btn-secondary">⬅️ Retour au tableau de bord</a>
        </div>
</div>

<footer>
     &copy; 2025 - CarePath. Tous droits réservés.
   </footer>
      
</body>
</html>