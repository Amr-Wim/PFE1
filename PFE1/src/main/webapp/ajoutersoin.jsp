<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ajouter un Soin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
      :root {
      --main-blue: #1e3a5f;
      --accent-red: #7e0021;
      --bg-light: #e0f2fe;
      --text-dark: #0f172a;
      --header-font: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    body {
      background-color: var(--bg-light);
      font-family: var(--header-font);
      margin: 0;
      padding: 0;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }
  

        .btn-retour {
            font-weight: bold;
            color: var(--accent-red);
        }

      header {
      background-color: #1e3a5f;
      padding: 5px 60px;
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

    .title {
      font-size: 30px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 1.5px;
    }

    main {
      flex: 1;
      padding: 50px 20px;
    }

    .soins-title {
      color: var(--accent-red);
      font-weight: bold;
      margin-bottom: 30px;
     
    }

    .search-bar {
      max-width: 500px;
      margin: 0 auto 40px;
    }

    .card {
      border: none;
      border-radius: 16px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
      background-color: #ffffff;
    }

    .table thead th {
      color: var(--accent-red);
      font-weight: 700;
      background-color: #f8fafc;
      border-bottom: 2px solid var(--accent-red);
      text-transform: uppercase;
      font-size: 15px;
    }

    .table td {
      vertical-align: middle;
    }

    .table-striped tbody tr:nth-of-type(odd) {
      background-color: #f1f5f9;
    }

    .btn-primary {
      background-color: var(--main-blue);
      border: none;
      font-weight: 500;
      letter-spacing: 0.5px;
      transition: background-color 0.2s ease-in-out;
    }

    .btn-primary:hover {
      background-color: #16314f;
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
      margin-top: auto;
    }
       .form-container {
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
      font-size: 26px;
      font-weight: bold;
      margin-bottom: 30px;
      color: #7e0021;
    }

    label {
      font-weight: 600;
      color: #1e3a5f;
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


  <div class="form-container">
  
<%
    String patientId = request.getParameter("idPatient");
    if (patientId == null) {
        patientId = "";
    }
%>


    <div class="form-title">Ajouter un Soin</div>
    
    <%-- Juste après le <div class="form-title"> --%>
<c:if test="${not empty sessionScope.errorMessage}">
    <div class="alert alert-danger">${sessionScope.errorMessage}</div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.successMessage}">
    <div class="alert alert-success">${sessionScope.successMessage}</div>
    <c:remove var="successMessage" scope="session"/>
</c:if>


    <form action="AjouterSoinServlet" method="post">
      <div class="mb-3">
     
      
        <label for="idPatient" class="form-label">Numéro de Patient</label>
         <input type="text" class="form-control"  name="idPatient" required>
     
      </div>
      <div class="mb-3">
    <label class="form-label" for="type">Type du Soin</label>
    <select class="form-control" id="type" name="type" required>
        <option value="">-- Sélectionner --</option>
        <option value="pansement">Pansement</option>
        <option value="injection">Injection</option>
        <option value="aide">Aide à la toilette</option>
         <option value="prélèvement">Prélèvement sanguin</option>
          <option value="Pose">Pose d’une perfusion intraveineuse</option>
    </select>
</div>
     
      <div class="mb-3">
        <label for="description" class="form-label">Description du Soin</label>
        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
      </div>
      <div class="mb-3">
        <label for="date" class="form-label">Date du Soin</label>
        <input type="date" class="form-control" id="date_soin" name="date_soin" required>
      </div>
     
      <div class="d-grid">
        <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
      </div>
      <% String message = (String) request.getAttribute("message"); %>
<% if (message != null) { %>
  <div class="alert alert-info text-center" role="alert">
    <%= message %>
  </div>
<% } %>
      
    </form>
  </div>

  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
