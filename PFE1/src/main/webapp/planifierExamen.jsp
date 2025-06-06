<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Ajout de la taglib fmt --%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Demander des Examens</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    :root { 
      --main-blue: #1e3a5f; --accent-red: #7e0021; --bg-light: #e0f2fe;
    }
    body { background-color: var(--bg-light); font-family: 'Segoe UI', sans-serif; display: flex; flex-direction: column; min-height: 100vh; }
    header { background-color: var(--main-blue); padding: 15px 30px; display: flex; align-items: center; border-bottom: 3px solid var(--accent-red); color: white; }
    .logo-title { display: flex; align-items: center; gap: 10px; }
    .logo-title img { height: 40px; }
    .title { font-size: 24px; font-weight: bold; }
    .menu-links { display: flex; gap: 5px; margin-left: auto; }
    .menu-links a, .menu-links button { color: var(--accent-red); font-weight: bold; background-color: #fff; padding: 6px 12px; border-radius: 6px; text-decoration: none; transition: background-color 0.2s; white-space: nowrap; font-size: 0.9rem; border: none; }
    .menu-links a:hover, .menu-links button:hover { background-color: #f0f0f0; }
    main { flex: 1; padding: 30px 15px; }
    .form-container { background-color: #fff; border-radius: 12px; padding: 30px; max-width: 750px; margin: auto; box-shadow: 0 6px 18px rgba(0,0,0,0.1); border-top: 4px solid var(--accent-red); }
    h2 { color: var(--accent-red); font-weight: bold; text-align: center; margin-bottom: 25px; font-size: 1.8rem; }
    .form-label { color: var(--main-blue); font-weight: 600; }
    footer { background-color: var(--main-blue); color: white; text-align: center; padding: 15px; font-size: 0.9rem; border-top: 3px solid var(--accent-red); }
    .btn-primary { background-color: var(--main-blue); border-color: var(--main-blue); }
    .btn-primary:hover { background-color: #162a42; border-color: #162a42; }
    .btn-outline-danger { font-size: 0.8em; padding: 0.2em 0.5em;}

   
#selectedExamensList .list-group-item {
    padding: 0.75rem 1.25rem;
    border: 1px solid rgba(0,0,0,.125);
    margin-bottom: 5px;
    border-radius: 4px;
}

.remove-examen-btn {
    padding: 0.25rem 0.5rem;
    font-size: 0.8rem;
}

#selectedExamensList {
    max-height: 200px;
    overflow-y: auto;
    margin-top: 10px;
    border-radius: 0.25rem;
}

#selectedExamensList .list-group-item {
    padding: 0.75rem 1.25rem;
    border: 1px solid rgba(0,0,0,.125);
    margin-bottom: 5px;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

#selectedExamensList .list-group-item div {
    flex-grow: 1;
}

#selectedExamensList .btn-outline-danger {
    padding: 0.25rem 0.5rem;
    font-size: 0.8rem;
    margin-left: 10px;
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
      <nav class="d-flex align-items-center gap-3">
       <a class="nav-link text-white d-flex align-items-center gap-1" href="medecin_dashboard.jsp">
          <i class="fas fa-tachometer-alt me-1"></i><span>Tableau de bord</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="nouvelleHospitalisation.jsp">
          <i class="fas fa-hospital-user"></i><span>Nouvelle Hospitalisation</span>
        </a>
       
        <a class="nav-link text-white d-flex align-items-center gap-1" href="plannifierExamens.jsp">
          <i class="fas fa-calendar-check"></i><span>Plannifier Examens</span>
        </a>
        <a class="nav-link text-white d-flex align-items-center gap-1" href="diagnostic.jsp">
          <i class="fas fa-microscope"></i><span>Diagnostics</span>
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

<main>
    <div class="form-container">
      <h2>Demander des Examens Médicaux</h2>

      <c:if test="${not empty requestScope.error}">
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <i class="fas fa-exclamation-triangle me-2"></i>${requestScope.error}
              <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>
      <c:if test="${not empty requestScope.successMessage}">
          <div class="alert alert-success alert-dismissible fade show" role="alert">
              <i class="fas fa-check-circle me-2"></i>${requestScope.successMessage}
               <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>

      <form id="demandeExamenForm" method="post" action="${pageContext.request.contextPath}/PlanifierExamen1">
        
        <div class="mb-3">
          <label for="idPatient" class="form-label">Patient *</label>
          <select class="form-select" id="idPatient" name="idPatient" required>
            <option value="">-- Sélectionner un patient --</option>
            <c:forEach var="patient" items="${patients}">
                <option value="${patient.id}" ${patient.id == param.idPatient ? 'selected' : ''}>${patient.nom} ${patient.prenom} (ID: ${patient.id})</option>
            </c:forEach>
          </select>
        </div>

        <hr>
        <h5 class="mb-3">Sélection des examens</h5>

        <div class="row g-3 mb-3">
            <div class="col-md-5">
                <label for="typeExamenSelect" class="form-label">Type d'examen</label>
                <select class="form-select" id="typeExamenSelect">
                    <option value="">-- Sélectionner un type --</option>
                    <c:forEach var="typeEx" items="${typesExamens}">
                        <option value="${typeEx.id}">${typeEx.nomType}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-5">
                <label for="examensDisponiblesSelect" class="form-label">Examens disponibles</label>
                <select class="form-select" id="examensDisponiblesSelect" disabled>
                    <option value="">-- Sélectionner un type d'abord --</option>
                </select>
            </div>
            <div class="col-md-2 d-flex align-items-end">
                <button type="button" class="btn btn-success w-100" id="btnAjouterExamen"><i class="fas fa-plus"></i> Ajouter</button>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Examens demandés :</label>
            <ul class="list-group" id="selectedExamensList">
                <li class="list-group-item text-muted" id="aucunExamenSelectionneMsg">Aucun examen sélectionné pour le moment.</li>
            </ul>
        </div>
        
        <input type="hidden" id="selectedExamsInput" name="selectedExams" value="">

        <hr>
        <div class="mb-3">
          <label for="notesMedecin" class="form-label">Notes / Indications Générales</label>
          <textarea class="form-control" id="notesMedecin" name="notesMedecin" rows="3">${param.notesMedecin}</textarea>
        </div>

        <button type="submit" class="btn btn-primary w-100 mt-3">Enregistrer les Demandes</button>
      </form>
    </div>
  </main>

  <footer>
    © <jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" pattern="yyyy" /> - CarePath. Tous droits réservés.
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script>
document.addEventListener('DOMContentLoaded', function () {
    const typeExamenSelect = document.getElementById('typeExamenSelect');
    const examensDisponiblesSelect = document.getElementById('examensDisponiblesSelect');
    const btnAjouterExamen = document.getElementById('btnAjouterExamen');
    const selectedExamensList = document.getElementById('selectedExamensList');
    const aucunExamenSelectionneMsg = document.getElementById('aucunExamenSelectionneMsg');
    const selectedExamsInput = document.getElementById('selectedExamsInput');
    const demandeExamenForm = document.getElementById('demandeExamenForm');

    let examensDemandes = [];

    // Style dynamique pour la liste des examens
    selectedExamensList.style.maxHeight = '200px';
    selectedExamensList.style.overflowY = 'auto';
    selectedExamensList.style.marginTop = '10px';

    typeExamenSelect.addEventListener('change', function () {
        const typeId = this.value;
        examensDisponiblesSelect.innerHTML = '<option value="">Chargement...</option>';
        examensDisponiblesSelect.disabled = true;

        if (typeId) {
            fetch('${pageContext.request.contextPath}/PlanifierExamen1?action=getExamsByType&typeId=' + typeId)
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => { 
                            throw new Error(err.error || 'Erreur réseau: ' + response.statusText); 
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    examensDisponiblesSelect.innerHTML = '<option value="">-- Sélectionner un examen --</option>';
                    
                    if (data && data.length > 0) {
                        data.forEach(examen => {
                            const option = document.createElement('option');
                            option.value = examen.id;
                            
                            let infoText = examen.nomExamen;
                            if (examen.doitEtreAJeun) { 
                                infoText += " (À jeun)";
                            }
                            if (examen.dureePreparationResultatsHeures != null && examen.dureePreparationResultatsHeures > 0) {
                                infoText += " [Résultats en ~" + examen.dureePreparationResultatsHeures + "h]";
                            }
                            
                            option.textContent = infoText;
                            examensDisponiblesSelect.appendChild(option);
                        });
                        examensDisponiblesSelect.disabled = false;
                    } else {
                        examensDisponiblesSelect.innerHTML = '<option value="">-- Aucun examen pour ce type --</option>';
                        examensDisponiblesSelect.disabled = true;
                    }
                })
                .catch(error => {
                    console.error('Erreur chargement examens:', error);
                    examensDisponiblesSelect.innerHTML = '<option value="">Erreur de chargement</option>';
                    examensDisponiblesSelect.disabled = true;
                    
                    // Affichage temporaire de l'erreur
                    const errorAlert = document.createElement('div');
                    errorAlert.className = 'alert alert-danger mt-2';
                    errorAlert.textContent = 'Erreur lors du chargement des examens';
                    examensDisponiblesSelect.insertAdjacentElement('afterend', errorAlert);
                    setTimeout(() => errorAlert.remove(), 5000);
                });
        } else {
            examensDisponiblesSelect.innerHTML = '<option value="">-- Sélectionner un type d\'abord --</option>';
            examensDisponiblesSelect.disabled = true;
        }
    });

    btnAjouterExamen.addEventListener('click', function () {
        const selectedOption = examensDisponiblesSelect.options[examensDisponiblesSelect.selectedIndex];
        if (selectedOption && selectedOption.value !== "") {
            const examenId = selectedOption.value;
            const examenNom = selectedOption.textContent; 
            const typeNom = typeExamenSelect.options[typeExamenSelect.selectedIndex].text;

            if (!examensDemandes.find(ex => ex.id === examenId)) {
                examensDemandes.push({ 
                    id: examenId, 
                    nom: examenNom, 
                    typeNom: typeNom 
                });
                mettreAJourListeExamensDemandes();
            } else {
                alert("Cet examen a déjà été ajouté.");
            }
        } else {
            alert("Veuillez sélectionner un examen spécifique à ajouter.");
        }
    });

    function mettreAJourListeExamensDemandes() {
        selectedExamensList.innerHTML = ''; 
        const idsPourSoumission = [];

        if (examensDemandes.length === 0) {
            if(aucunExamenSelectionneMsg) {
               selectedExamensList.appendChild(aucunExamenSelectionneMsg);
               aucunExamenSelectionneMsg.style.display = 'list-item';
            }
        } else {
            if(aucunExamenSelectionneMsg) aucunExamenSelectionneMsg.style.display = 'none';
            
            examensDemandes.forEach((examen, index) => {
                const listItem = document.createElement('li');
                listItem.className = 'list-group-item d-flex justify-content-between align-items-center';
                
                // Partie texte avec type et nom de l'examen
                const textDiv = document.createElement('div');
                textDiv.innerHTML = `<strong>${examen.typeNom}:</strong> ${examen.nom}`;
                
                // Bouton de suppression
                const deleteBtn = document.createElement('button');
                deleteBtn.type = 'button';
                deleteBtn.className = 'btn btn-outline-danger btn-sm';
                deleteBtn.innerHTML = '<i class="fas fa-times"></i>';
                deleteBtn.setAttribute('data-index', index);
                deleteBtn.setAttribute('aria-label', 'Supprimer cet examen');
                
                // Assemblage des éléments
                listItem.appendChild(textDiv);
                listItem.appendChild(deleteBtn);
                selectedExamensList.appendChild(listItem);
                
                idsPourSoumission.push(examen.id);
            });
        }
        selectedExamsInput.value = idsPourSoumission.join(',');
        console.log("Examens sélectionnés:", examensDemandes); // Debug
    }

    selectedExamensList.addEventListener('click', function (e) {
        const removeButton = e.target.closest('.btn-outline-danger');
        if (removeButton) {
            const indexToRemove = parseInt(removeButton.getAttribute('data-index'));
            examensDemandes.splice(indexToRemove, 1); 
            mettreAJourListeExamensDemandes();
        }
    });
    
    // Initialisation
    mettreAJourListeExamensDemandes();

    if (demandeExamenForm) {
        demandeExamenForm.addEventListener('submit', function(event){
            if(examensDemandes.length === 0){
                alert("Veuillez ajouter au moins un examen à la demande.");
                event.preventDefault(); 
            }
        });
    }
});
</script>
</body>
</html>