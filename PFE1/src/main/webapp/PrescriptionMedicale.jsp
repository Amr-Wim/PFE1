<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Prescription Médicale</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" integrity="sha512-8x6yQ1EoE8Is0Q9vZff4Kn8Z4Ofy+HEsf3F9EQoTlf9oZ2KvDdBbSz+ukOfT6+f88l+c1E9KgyGlTYKhwD1F2Q==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <style>
    body {
      margin: 0;
      padding: 0;
      background: linear-gradient(135deg, #dbeafe, #e0f2fe);
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      color: #1e3a5f;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    header img {
      height: 50px;
    }

    main {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 40px 20px;
    }

    label {
      display: block;
      margin-bottom: 5px;
      font-weight: 700;
      font-size: 15px;
    }

    label::after {
      content: " :";
    }

    input, select {
      width: 100%;
      padding: 10px 12px;
      border-radius: 8px;
      border: 1px solid #ccc;
      margin-bottom: 20px;
      font-size: 14px;
      background-color: #f8fafc;
    }

    .medicaments {
      margin-bottom: 20px;
    }

    .btn-add {
      background-color: #7e0021;
      color: white;
      padding: 8px 16px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      margin-bottom: 20px;
    }

    .btn-add:hover {
      background-color: #5e0019;
    }

    .btn-submit {
      width: 100%;
      padding: 12px;
      background-color: #1e3a5f;
      color: white;
      font-weight: bold;
      font-size: 16px;
      border: none;
      border-radius: 10px;
      cursor: pointer;
    }

    .btn-submit:hover {
      background-color: #16324b;
    }

    footer {
      background-color: #1e3a5f;
      color: #ffffff;
      text-align: center;
      padding: 16px 30px;
      font-size: 14px;
      border-top: 4px solid #7e0021;
      box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.15);
      font-weight: 500;
      letter-spacing: 0.5px;
    }

    header {
      background-color: #1e3a5f;
      padding: 20px 60px;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      border-bottom: 4px solid #7e0021;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
      color: white;
      position: relative;
      z-index: 10;
    }

    .logo-title {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .logo-title img {
      height: 48px;
      width: auto;
      filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.4));
    }

    .title {
      font-size: 30px;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: 2px;
      color: #ffffff;
      background: none;
      text-shadow: 0 2px 4px rgba(126, 0, 33, 0.6);
    }
      .menu-links {
      display: flex;
      gap: 8px;
      margin-left: auto;
    }

    .menu-links a {
      color:#7e0021 ;
      font-weight: bold;
      background-color: #ffffff;
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
      transition: background-color 0.3s ease;
    }

    .menu-links a:hover {
      background-color: #f1f5f9;
    }
     .form-container {
      background-color: #fff;
      border-radius: 16px;
      padding: 40px;
      max-width: 700px;
      margin: 0 auto;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
      border-top: 6px solid #7e0021;
    }
    .form-container h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #7e0021; /* grenat */
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

      <!-- Me -->
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
        <a class="nav-link text-white d-flex align-items-center gap-1" href="diagnostic.jsp">
          <i class="fas fa-microscope"></i><span>Diagnostics</span>
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

  <!-- Formulaire -->
 <main>
 
 <%-- En haut de la page, après les taglibs --%>
<c:if test="${not empty sessionScope.successMessage}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="fas fa-check-circle me-2"></i>
        ${sessionScope.successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <%-- On supprime l'attribut pour qu'il ne s'affiche qu'une fois --%>
    <c:remove var="successMessage" scope="session" />
</c:if>

<%-- En haut du <div class="form-container"> --%>
<c:if test="${not empty sessionScope.errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fas fa-exclamation-triangle me-2"></i>
        ${sessionScope.errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="errorMessage" scope="session" />
</c:if>
    <div class="form-container">
        <h2 class="form-title"><i class="fas fa-pills me-2"></i>Nouvelle Prescription</h2>

        <form id="prescriptionForm" method="post" action="${pageContext.request.contextPath}/PrescriptionServlet">
            <!-- Patient et Date -->
            <div class="row">
                <div class="col-md-8 mb-3">
                    <label for="idPatient" class="form-label">Patient *</label>
                    <select class="form-select" id="idPatient" name="idPatient" required>
                        <option value="">-- Sélectionner un patient --</option>
                        <c:forEach var="patient" items="${patients}"><option value="${patient.id}">${patient.nom} ${patient.prenom}</option></c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="datePrescription" class="form-label">Date</label>
                    <input type="date" id="datePrescription" name="datePrescription" class="form-control" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" readonly>
                </div>
            </div>

            <hr>
            <h5 class="mb-3">Ajouter un médicament</h5>

            <!-- Section pour ajouter les médicaments -->
            <div class="card bg-light p-3 mb-3">
                <div class="row g-2 align-items-end">
                    <div class="col-md-6"><label class="form-label">Type</label><select id="typeMedicamentSelect" class="form-select"><option value="">-- Type --</option><c:forEach var="type" items="${typesMedicaments}"><option value="${type.id}">${type.nom}</option></c:forEach></select></div>
                    <div class="col-md-6"><label class="form-label">Médicament</label><select id="medicamentSelect" class="form-select" disabled><option>-- Choisir type --</option></select></div>
<select id="dosageSelect" class="form-select">
    <c:forEach var="d" items="${dosages}">
        <option value="${d.id}">${d.valeur}</option> <!-- Mettre d.id dans value -->
    </c:forEach>
</select>
                    <div class="col-md-4"><label class="form-label">Posologie</label><select id="posologieSelect" class="form-select"><c:forEach var="p" items="${posologies}"><option value="${p.libelle}">${p.libelle}</option></c:forEach></select></div>
                    <div class="col-md-4"><label class="form-label">Durée</label><select id="dureeSelect" class="form-select"><c:forEach var="d" items="${durees}"><option value="${d.libelle}">${d.libelle}</option></c:forEach></select></div>
                </div>
                <button type="button" id="btnAjouter" class="btn btn-success mt-3"><i class="fas fa-plus"></i> Ajouter à la prescription</button>
            </div>

            <!-- Liste des médicaments ajoutés -->
            <div class="mb-3">
                <label class="form-label">Médicaments prescrits :</label>
                <div id="liste-medicaments-ajoutes" class="list-group">
                    <p id="aucun-medicament" class="text-muted text-center p-3">Aucun médicament ajouté.</p>
                </div>
            </div>

            <!-- Champ caché pour envoyer les données au format JSON -->
            <input type="hidden" id="prescriptionData" name="prescriptionData" value="[]">

            <button type="submit" class="btn btn-primary w-100 mt-3 py-2 fw-bold"><i class="fas fa-check-circle me-2"></i>Valider la Prescription</button>
        </form>
    </div>
</main>
  <footer>
    &copy; 2025 - CarePath. Tous droits réservés.
  </footer>
  
  
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function () {
    // ... (toutes les déclarations de const restent les mêmes) ...
    const typeSelect = document.getElementById('typeMedicamentSelect');
    const medicamentSelect = document.getElementById('medicamentSelect');
    const dosageSelect = document.getElementById('dosageSelect');
    const posologieSelect = document.getElementById('posologieSelect');
    const dureeSelect = document.getElementById('dureeSelect');
    const btnAjouter = document.getElementById('btnAjouter');
    const listeAjoutes = document.getElementById('liste-medicaments-ajoutes');
    const aucunMedicamentMsg = document.getElementById('aucun-medicament');
    const form = document.getElementById('prescriptionForm');

    let prescriptionItems = [];

    // Logique AJAX (ne change pas)
    typeSelect.addEventListener('change', function() {
        const typeId = this.value;
        medicamentSelect.innerHTML = '<option>Chargement...</option>';
        medicamentSelect.disabled = true;

        if (typeId) {
            const url = '${pageContext.request.contextPath}/PrescriptionServlet?action=getMedicaments&typeId=' + typeId;
            fetch(url)
                .then(response => {
                    if (!response.ok) throw new Error('Erreur réseau');
                    return response.json();
                })
                .then(data => {
                    // On vide complètement la liste déroulante
                    medicamentSelect.innerHTML = ''; 

                    // On ajoute l'option par défaut
                    let defaultOption = document.createElement('option');
                    defaultOption.value = "";
                    defaultOption.textContent = "-- Choisir médicament --";
                    defaultOption.disabled = true;
                    defaultOption.selected = true;
                    medicamentSelect.appendChild(defaultOption);

                    if (data && data.length > 0) {
                        // On construit les options dans une boucle de manière fiable
                        data.forEach(med => {
                            let option = document.createElement('option');
                            option.value = med.id;       // L'ID du médicament
                            option.textContent = med.nom; // Le nom affiché
                            option.dataset.nom = med.nom; // On stocke aussi le nom dans un data-attribut
                            medicamentSelect.appendChild(option);
                        });
                        medicamentSelect.disabled = false;
                    } else {
                        // Cas où aucun médicament n'est trouvé
                        defaultOption.textContent = "-- Aucun médicament --";
                        defaultOption.disabled = true;
                    }
                })
                .catch(err => {
                    console.error("Erreur AJAX:", err);
                    medicamentSelect.innerHTML = '<option>Erreur de chargement</option>';
                });
        } else {
            medicamentSelect.innerHTML = '<option>-- Choisir type --</option>';
        }
    });

    // Logique du bouton "Ajouter"
    btnAjouter.addEventListener('click', function() {
        const medSelected = medicamentSelect.options[medicamentSelect.selectedIndex];
        // Récupérer aussi les options sélectionnées des autres listes
        const dosageSelected = dosageSelect.options[dosageSelect.selectedIndex];
        const posologieSelected = posologieSelect.options[posologieSelect.selectedIndex];
        const dureeSelected = dureeSelect.options[dureeSelect.selectedIndex];

        if (!medSelected || !medSelected.value) { alert('Veuillez sélectionner un médicament.'); return; }

        const newItem = {
            medicament: { id: medSelected.value, nom: medSelected.dataset.nom },
            dosage: { id: dosageSelected.value, nom: dosageSelected.text },
            posologie: { id: posologieSelected.value, nom: posologieSelected.text },
            duree: { id: dureeSelected.value, nom: dureeSelected.text }
        };

        prescriptionItems.push(newItem);
        renderListe();
    });

    // Fonction pour afficher la liste ET créer les inputs cachés
    function renderListe() {
        listeAjoutes.innerHTML = '';
        if (prescriptionItems.length === 0) {
            listeAjoutes.appendChild(aucunMedicamentMsg);
        } else {
            prescriptionItems.forEach((item, index) => {
                const div = document.createElement('div');
                div.className = 'list-group-item';
                div.innerHTML = `
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <strong class="text-primary">${item.medicament.nom}</strong> (${item.dosage.nom})<br>
                            <small class="text-muted">${item.posologie.nom} - pendant ${item.duree.nom}</small>
                        </div>
                        <button type="button" class="btn btn-outline-danger btn-sm" data-index="${index}"><i class="fas fa-trash-alt"></i></button>
                    </div>
                    <!-- *** LA PARTIE LA PLUS IMPORTANTE : LES CHAMPS CACHÉS *** -->
                    <input type="hidden" name="medicamentId" value="${item.medicament.id}">
                    <input type="hidden" name="dosageId" value="${item.dosage.id}">
                    <input type="hidden" name="posologieId" value="${item.posologie.id}">
                    <input type="hidden" name="dureeId" value="${item.duree.id}">
                `;
                listeAjoutes.appendChild(div);
            });
        }
    }
    
    // Gérer la suppression
    listeAjoutes.addEventListener('click', function(e) {
        const btn = e.target.closest('.btn-outline-danger');
        if (btn) {
            prescriptionItems.splice(btn.dataset.index, 1);
            renderListe();
        }
    });

    // Validation avant soumission
    form.addEventListener('submit', function(e) {
        if (prescriptionItems.length === 0) {
            alert("Veuillez ajouter au moins un médicament.");
            e.preventDefault();
        }
    });
});
</script>

  <!-- Bootstrap JS (optionnel) -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
 
  
</body>
</html>
