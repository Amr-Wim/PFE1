<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter Observation - CarePath</title>
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        :root {
            --main-blue: #1e3a5f;
            --accent-red: #7e0021;
            --bg-light: #e0f2fe;
            --text-dark: #0f172a;
        }

        body {
            background-color: var(--bg-light);
        }
        .title {
            font-size: 30px;
            font-weight: 800;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            margin-left: 15px;
        }
     
        

      header {
      background-color: #1e3a5f;
      padding: 18px 60px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 4px solid #7e0021;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
      color: white;
    }

    .logo-title {
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .logo-title img {
      height: 48px;
       width: auto;
    }

      

.button-group {
  display: flex;
  gap: 8px; /* très proche */
}

.btn-retour {
  font-weight: bold;
  color: var(--accent-red);
  padding: 6px 14px;
  border-radius: 10px;
  white-space: nowrap;
}

        .container-form {
             background-color: white;
  padding: 40px;
  border-radius: 20px;

  width: 50%;
max-width: 900px;
   /* anciennement 600px */
  margin: 50px auto;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  border-top: 6px solid #7e0021;
}
        
      .form-title {
    text-align: center;
    font-size: 35px;
    font-weight: bold;
    margin-bottom: 30px;
    color: #7e0021; /* couleur grenat */
}
      


        .form-label {
            font-weight: 600;
            color: #1e3a5f;
        }

        .btn-submit {
            background-color: var(--main-blue);
            color: white;
            font-weight: bold;
            width: 100%;
        }

        .btn-submit:hover {
            background-color: #122b47;
        }

        footer {
            background-color: var(--main-blue);
            color: white;
            text-align: center;
            padding: 18px 30px;
            font-size: 14px;
            border-top: 4px solid var(--accent-red);
            font-weight: 500;
        }
        .gap-2 > * {
    margin-left: 7px;
}
    </style>
</head>
<body>

  <header style="background-color: #1e3a5f; border-bottom: 4px solid var(--accent-red);">
  <div class="container-fluid px-4">
    <div class="d-flex align-items-center justify-content-between py-2">
      <!-- Logo + titre -->
      <div class="d-flex align-items-center gap-2">
        <img src="${pageContext.request.contextPath}/image/image2.png" alt="Logo CarePath" style="height: 40px;">
        <span class="fs-4 fw-bold text-white text-uppercase" style="letter-spacing: 1px;">CarePath</span>
      </div>
      
      <!-- Menu de navigation pour l'infirmier -->
      <nav class="d-flex align-items-center gap-3">
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/infirmier_dashboard.jsp">
            <i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span>
         </a>
         
         <!-- LIEN AJOUTÉ ICI -->
         <a class="nav-link text-white d-flex align-items-center gap-1" href="FichePatient.jsp">
            <i class="fas fa-id-card me-1"></i><span>Fiche Patient</span>
         </a>
         
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/RechercherObservationServlet">
            <i class="fas fa-eye me-1"></i><span>Observations</span>
         </a>
         
         <a class="nav-link text-white d-flex align-items-center gap-1" href="${pageContext.request.contextPath}/AfficherSoinsServlet">
            <i class="fas fa-hand-holding-medical me-1"></i><span>Soins</span>
         </a>
      </nav>
      
      <!-- Déconnexion -->
      <form action="${pageContext.request.contextPath}/Logout" method="post" class="m-0">
        <button type="submit" class="btn btn-danger btn-sm d-flex align-items-center gap-1">
          <i class="fas fa-sign-out-alt"></i><span>Déconnexion</span>
        </button>
      </form>
    </div>
  </div>
</header>

   
    <div class="container-form">
        <h2 class="form-title">Ajouter une Observation</h2>

        <%-- AFFICHAGE DES MESSAGES AVEC JSTL --%>
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success">${sessionScope.successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger">${sessionScope.errorMessage}</div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <form action="${pageContext.request.contextPath}/AjouterObservationServlet" method="post">
            <div class="form-group mb-3">
                <label class="form-label" for="idPatient">Numéro de Patient</label>
                <%-- On pré-remplit le champ si un idPatient est passé dans l'URL (après une erreur par exemple) --%>
                <input type="text" class="form-control" id="idPatient" name="idPatient" value="${param.idPatient}" required>
            </div>
            <div class="form-group mb-3">
                <label class="form-label" for="temperature">Température (°C)</label>
                <input type="text" class="form-control" id="temperature" name="temperature" required>
            </div>
            <div class="form-group mb-3">
                <label class="form-label" for="tension">Tension (ex: 120/80)</label>
                <input type="text" class="form-control" id="tension" name="tension" required>
            </div>
            <div class="form-group mb-3">
                <label class="form-label" for="frequence">Fréquence cardiaque (bpm)</label>
                <input type="text" class="form-control" id="frequence" name="frequence" required>
            </div>
            <div class="form-group mb-3">
                <label class="form-label" for="observation">Observation</label>
                <textarea class="form-control" id="observation" name="observation" rows="4" required></textarea>
            </div>
            <div class="form-group mb-3">
                <label class="form-label" for="dateObservation">Date de l'Observation</label>
                <input type="date" class="form-control" id="dateObservation" name="dateObservation" 
                       value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required>
            </div>
            <button type="submit" class="btn btn-submit mt-3">Ajouter</button>
        </form>
    </div>

    <!-- Footer -->
    <footer>
        &copy; 2025 - CarePath. Tous droits réservés.
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>