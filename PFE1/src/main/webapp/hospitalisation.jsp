<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #e0f2fe;
        }
        
        body {
            background-color: var(--bg-light);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        
        .medical-section {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            margin: 30px auto;
        }
        
        .section-title {
            text-align: center;
            color: var(--accent-red);
            margin-bottom: 30px;
            font-weight: bold;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        
        .info-item {
            margin-bottom: 15px;
        }
        
        .info-label {
            font-weight: 600;
            color: var(--main-blue);
            display: inline-block;
            min-width: 200px;
        }
        
        .info-value {
            color: #1e3a5f;
        }
        
        .hospitalisation-alert {
            border-left: 5px solid;
            border-radius: 8px;
            padding: 15px;
            margin-top: 20px;
        }
        
        .btn-back {
            background-color: var(--main-blue);
            color: white;
            border: none;
            padding: 8px 20px;
            border-radius: 8px;
            margin-top: 20px;
            transition: all 0.3s ease;
        }
        
        .btn-back:hover {
            background-color: #16314f;
            transform: translateY(-2px);
        }
         header {
      background-color: var(--main-blue);
      padding: 20px 60px;
      display: flex;
      align-items: center;
      border-bottom: 4px solid var(--accent-red);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
      color: white;
    }
     footer {
      background-color: var(--main-blue);
      color: #ffffff;
      text-align: center;
      padding: 18px 30px;
      font-size: 14px;
      border-top: 4px solid var(--accent-red);
      font-weight: 500;
      letter-spacing: 0.5px;
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

 <main>
        <div class="medical-section">
            <h2 class="section-title">
                <i class="fas fa-procedures me-2"></i>
                Informations d'Hospitalisation
            </h2>
            
            <c:choose>
                <c:when test="${not empty hospitalisation}">
                    <div class="info-grid">
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-hospital me-2"></i>Hôpital :</span>
                            <span class="info-value">${hospitalisation.nomHopital}</span>
                        </div>
                        
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-clinic-medical me-2"></i>Service :</span>
                            <span class="info-value">${hospitalisation.service}</span>
                        </div>
                        
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-bed me-2"></i>Chambre :</span>
                            <span class="info-value">${hospitalisation.chambre}</span>
                        </div>
                        
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-user-md me-2"></i>Médecin :</span>
                            <span class="info-value">Dr. ${hospitalisation.medecin.nom}</span>
                        </div>
                        
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-calendar-check me-2"></i>Date admission :</span>
                            <span class="info-value">
                                <fmt:formatDate value="${hospitalisation.dateAdmission}" pattern="dd/MM/yyyy"/>
                            </span>
                        </div>
                        
                        <div class="info-item">
                            <span class="info-label"><i class="fas fa-clock me-2"></i>Durée prévue :</span>
                            <span class="info-value">${hospitalisation.duree}</span>
                        </div>
                    </div>
                    
                    <div class="hospitalisation-alert alert-${hospitalisation.etat == 'Critique' ? 'danger' : 'info'}">
                        <h4><i class="fas fa-info-circle me-2"></i>État du patient</h4>
                        <p><strong>État :</strong> ${hospitalisation.etat}</p>
                        <p><strong>Motif :</strong> ${hospitalisation.motif}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle me-2"></i>
                        Aucune hospitalisation en cours
                    </div>
                </c:otherwise>
            </c:choose>
            
            <div class="text-center">
                <a href="patient_dashboard.jsp" class="btn btn-back">
                    <i class="fas fa-arrow-left me-2"></i> Retour au tableau de bord
                </a>
            </div>
        </div>
    </main>



<footer>
    &copy; 2025 - CarePath. Tous droits réservés.
</footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>