<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Diagnostic</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-8x6yQ1EoE8Is0Q9vZff4Kn8Z4Ofy+HEsf3F9EQoTlf9oZ2KvDdBbSz+ukOfT6+f88l+c1E9KgyGlTYKhwD1F2Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body {
      background: linear-gradient(135deg, #dbeafe, #e0f2fe);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      color: #1e3a5f;
      min-height: 100vh;
      margin: 0;
      display: flex;
      flex-direction: column;
    }

    header {
      background-color: #1e3a5f;
      padding: 20px 60px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 4px solid #7e0021;
      color: white;
    }

    .logo-title {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .logo-title img {
      height: 48px;
    }

    .title {
      font-size: 30px;
      font-weight: 800;
      color: white;
      text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);
      letter-spacing: 2px;
    }

    .menu-links {
      display: flex;
      gap: 10px;
    }

    .menu-links a {
      background-color: #ffffff;
      color: #7e0021;
      font-weight: bold;
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
    }

    .menu-links a:hover {
      background-color: #f1f5f9;
    }

    main {
      flex: 1;
      padding: 40px 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .form-container {
      background-color: #fff;
      border-radius: 16px;
      padding: 30px;
      max-width: 600px;
      width: 85%;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
      border-top: 6px solid #7e0021;
    }

    h2 {
      text-align: center;
      color: #7e0021;
      margin-bottom: 25px;
    }

    .form-label {
      font-weight: 600;
      color: #1e3a5f;
    }

    .btn-primary {
      background-color: #1e3a5f;
      border: none;
      font-weight: 500;
      letter-spacing: 0.5px;
      transition: background-color 0.2s ease-in-out;
    }

    .btn-primary:hover {
      background-color: #16314f;
    }

    footer {
      background-color: #1e3a5f;
      color: #ffffff;
      text-align: center;
      padding: 16px 30px;
      font-size: 14px;
      border-top: 4px solid #7e0021;
    }
  </style>
</head>
<body>

  <!-- Header -->
 <header style="background-color: #1e3a5f;">
  <div class="container-fluid px-4">
    <div class="d-flex align-items-center justify-content-between py-2">

      <!-- Logo + titre -->
      <div class="d-flex align-items-center gap-2">
        <img src="image/image2.png" alt="Logo CarePath" style="height: 40px;">
        <span class="fs-4 fw-bold text-white text-uppercase" style="letter-spacing: 1px;">
          CarePath
        </span>
      </div>

      <!-- Menu -->
      <nav class="d-flex align-items-center gap-2">
       <a class="nav-link text-white d-flex align-items-center gap-1" href="medecin_dashboard.jsp">
          <i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span>
        </a>
         <a class="nav-link text-white d-flex align-items-center gap-1" href="nouvelleHospitalisation.jsp">
          <i class="fas fa-hospital-user"></i><span>Nouvelle Hospitalisation</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="historiqueMedicale.jsp">
          <i class="fas fa-notes-medical"></i><span>Historique Médicale</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="plannifierExamens.jsp">
          <i class="fas fa-calendar-check"></i><span>Plannifier Examens</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="PrescriptionMedicale.jsp">
          <i class="fas fa-pills"></i><span>Préscriptions</span>
        </a>
      </nav>

      <!-- Déconnexion -->
      <form action="Logout" method="post" class="m-0">
        <button type="submit" class="btn btn-danger btn-sm d-flex align-items-center gap-1">
          <i class="fas fa-sign-out-alt"></i><span>Déconnexion</span>
        </button>
      </form>
    </div>
  </div>
</header>
  <!-- Formulaire Diagnostic -->
  <main>
    <div class="form-container">
      <h2>Diagnostic</h2>
      <form action="DiagnosticServlet" method="post">
        <div class="mb-3">
          <label for="idPatient" class="form-label">Numéro du Patient</label>
          <input type="text" class="form-control" id="idPatient" name="idPatient" required>
        </div>

        <div class="mb-3">
          <label for="symptomes" class="form-label">Symptômes</label>
          <textarea class="form-control" id="symptomes" name="symptomes" rows="3" required></textarea>
        </div>

        <div class="mb-3">
          <label for="antecedents" class="form-label">Antécédents Médicaux</label>
          <textarea class="form-control" id="antecedents" name="antecedents" rows="3"></textarea>
        </div>

        <div class="mb-3">
          <label for="observations" class="form-label">Observations Cliniques</label>
          <textarea class="form-control" id="observations" name="observations" rows="3"></textarea>
        </div>

        <div class="mb-3">
          <label for="diagnostic" class="form-label">Diagnostic Final</label>
          <textarea class="form-control" id="diagnostic" name="diagnostic" rows="3" required></textarea>
        </div>

        <button type="submit" class="btn btn-primary w-100">Valider le Diagnostic</button>
      </form>
    </div>
  </main>

  <!-- Footer -->
  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>

</body>
</html>
