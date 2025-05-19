<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
     <style>
.bg-logo {
    background-color:  #1e3a5f; /* Bleu nuit du logo */
    color: #fdf5e6;            /* Texte crème */
}
.bg-logo1 {
    background-color:   #7e0021; /* Bleu nuit du logo */
    color: #fdf5e6;            /* Texte crème */
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
        <!-- BOUTON DECONNEXION -->
            <form action="Logout" method="post" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Déconnexion</button>
            </form>
        </div>
 </div>
   </header>
 

<div class="container mt-5">
    <h2 >📅 Mes Rendez-vous</h2>

    <!-- Liste des rendez-vous -->
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-logo">
            Historique et rendez-vous à venir
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>2025-04-20</strong> - Consultation avec Dr. Saïd
                </div>
                <div>
                    <span class="badge bg-info text-dark me-2">À venir</span>
                    <button class="btn btn-sm btn-danger">Annuler</button>
                </div>
            </li>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>2025-03-15</strong> - Examen radiologique
                </div>
                <div>
                    <span class="badge bg-secondary me-2">Passé</span>
                </div>
            </li>
        </ul>
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