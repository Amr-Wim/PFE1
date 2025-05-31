<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Demander des Examens</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    :root { /* Styles CSS comme avant */
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

    /* Styles pour la liste des examens sélectionnés */
    #selectedExamensList .list-group-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.5rem 1rem;
    }
    #examensDisponiblesSelect {
        min-height: 100px; /* Pour qu'il soit visible même sans options */
    }
  </style>
</head>
<body>
<header>
  <div class="logo-title">
    <img src="${pageContext.request.contextPath}/image/image2.png" alt="Logo">
    <span class="title">CAREPATH</span>
  </div>
  <div class="menu-links">
    <a href="${pageContext.request.contextPath}/medecin_dashboard.jsp"><i class="fas fa-tachometer-alt"></i> Tableau de Bord</a>
    <a href="javascript:history.back()"><i class="fas fa-arrow-left"></i> Retour</a>
     <form action="${pageContext.request.contextPath}/Logout" method="post" style="margin: 0;">
        <button type="submit"><i class="fas fa-sign-out-alt"></i> Déconnexion</button>
     </form>
  </div>
</header>

<main>
    <div class="form-container">
      <h2>Demander des Examens Médicaux</h2>

      <c:if test="${not empty requestScope.error}">
          <div class="alert alert-danger alert-dismissible fade show" role="alert">
              ${requestScope.error}
              <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>
      <c:if test="${not empty requestScope.successMessage}">
          <div class="alert alert-success alert-dismissible fade show" role="alert">
              ${requestScope.successMessage}
               <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
          </div>
      </c:if>

      <form id="demandeExamenForm" method="post" action="${pageContext.request.contextPath}/PlanifierExamen"> <%-- L'action pointe vers votre servlet --%>
        
        <div class="mb-3">
          <label for="idPatient" class="form-label">Patient *</label>
          <select class="form-select" id="idPatient" name="idPatient" required>
            <option value="">-- Sélectionner un patient --</option>
            <c:forEach var="patient" items="${patients}">
                <option value="${patient.id}">${patient.nom} ${patient.prenom} (ID: ${patient.id})</option>
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
                <select class="form-select" id="examensDisponiblesSelect">
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
        
        <%-- Champ caché pour stocker les IDs des examens sélectionnés pour la soumission --%>
        <input type="hidden" id="selectedExamsInput" name="selectedExams" value="">

        <hr>
        <div class="mb-3">
          <label for="notesMedecin" class="form-label">Notes / Indications Générales</label>
          <textarea class="form-control" id="notesMedecin" name="notesMedecin" rows="3"></textarea>
        </div>

        <button type="submit" class="btn btn-primary w-100 mt-3">Enregistrer les Demandes</button>
      </form>
    </div>
  </main>

  <footer>
    © <jsp:useBean id="now" class="java.util.Date" /><jsp:setProperty name="now" property="time" value="${now.time}" /> <fmt:formatDate value="${now}" pattern="yyyy" /> - CarePath. Tous droits réservés.
    <%-- Pour utiliser fmt:formatDate, il faut la taglib: <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
    <%-- Solution plus simple sans fmt: --%>
    <script>document.write(new Date().getFullYear());</script> - CarePath. Tous droits réservés.
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

        let examensDemandes = []; // Tableau pour stocker les objets examens {id, nom, typeNom}

        // Charger les examens spécifiques quand un type est sélectionné
        typeExamenSelect.addEventListener('change', function () {
            const typeId = this.value;
            examensDisponiblesSelect.innerHTML = '<option value="">Chargement...</option>';
            examensDisponiblesSelect.disabled = true;

            if (typeId) {
                fetch('${pageContext.request.contextPath}/PlanifierExamen?action=getExamsByType&typeId=' + typeId)
                    .then(response => {
                        if (!response.ok) throw new Error('Erreur réseau: ' + response.statusText);
                        return response.json();
                    })
                    .then(data => {
                        examensDisponiblesSelect.innerHTML = '<option value="">-- Sélectionner un examen --</option>';
                        if (data && data.length > 0) {
                            data.forEach(examen => {
                                const option = document.createElement('option');
                                option.value = examen.id; // L'ID de l'examen
                                let infoText = examen.nomExamen; // nomExamen de l'objet Examen.java
                                // Assurez-vous que votre objet Examen retourné par le servlet contient bien ces champs
                                if (examen.doitEtreAJeun) { // `doitEtreAJeun` doit être un champ booléen dans l'objet Examen
                                    infoText += " (À jeun)";
                                }
                                // `dureePreparationResultatsHeures` doit être un champ dans l'objet Examen
                                if (examen.dureePreparationResultatsHeures != null && examen.dureePreparationResultatsHeures > 0) {
                                    infoText += " [Résultats en ~" + examen.dureePreparationResultatsHeures + "h]";
                                } else if (examen.dureePreparationResultatsHeures == null) {
                                    // infoText += " [Durée résultats non spécifiée]"; // Optionnel
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
                    });
            } else {
                examensDisponiblesSelect.innerHTML = '<option value="">-- Sélectionner un type d''abord --</option>';
                examensDisponiblesSelect.disabled = true;
            }
        });

        // Ajouter un examen à la liste des examens demandés
        btnAjouterExamen.addEventListener('click', function () {
            const selectedOption = examensDisponiblesSelect.options[examensDisponiblesSelect.selectedIndex];
            if (selectedOption && selectedOption.value !== "") {
                const examenId = selectedOption.value;
                const examenNom = selectedOption.textContent; // Le texte complet de l'option
                const typeNom = typeExamenSelect.options[typeExamenSelect.selectedIndex].text;

                // Vérifier si l'examen n'est pas déjà dans la liste
                if (!examensDemandes.find(ex => ex.id === examenId)) {
                    examensDemandes.push({ id: examenId, nom: examenNom, typeNom: typeNom });
                    mettreAJourListeExamensDemandes();
                } else {
                    alert("Cet examen a déjà été ajouté.");
                }
            } else {
                alert("Veuillez sélectionner un examen spécifique à ajouter.");
            }
        });

        // Mettre à jour l'affichage de la liste des examens demandés
        function mettreAJourListeExamensDemandes() {
            selectedExamensList.innerHTML = ''; // Vider la liste
            const idsPourSoumission = [];

            if (examensDemandes.length === 0) {
                selectedExamensList.appendChild(aucunExamenSelectionneMsg);
                aucunExamenSelectionneMsg.style.display = 'list-item';
            } else {
                aucunExamenSelectionneMsg.style.display = 'none';
                examensDemandes.forEach((examen, index) => {
                    const listItem = document.createElement('li');
                    listItem.className = 'list-group-item';
                    listItem.innerHTML = `
                        <span><strong>${examen.typeNom}:</strong> ${examen.nom}</span>
                        <button type="button" class="btn btn-outline-danger btn-sm float-end remove-examen-btn" data-index="${index}">
                            <i class="fas fa-times"></i>
                        </button>
                    `;
                    selectedExamensList.appendChild(listItem);
                    idsPourSoumission.push(examen.id);
                });
            }
            selectedExamsInput.value = idsPourSoumission.join(','); // Mettre à jour le champ caché
        }

        // Gérer la suppression d'un examen de la liste des demandés
        selectedExamensList.addEventListener('click', function (e) {
            if (e.target.classList.contains('remove-examen-btn') || e.target.closest('.remove-examen-btn')) {
                const button = e.target.closest('.remove-examen-btn');
                const indexToRemove = parseInt(button.dataset.index);
                examensDemandes.splice(indexToRemove, 1); // Supprimer l'élément du tableau
                mettreAJourListeExamensDemandes(); // Réafficher la liste
            }
        });
        
        // Mettre à jour affichage initial
        mettreAJourListeExamensDemandes();

        // Validation avant soumission
        document.getElementById('demandeExamenForm').addEventListener('submit', function(event){
            if(examensDemandes.length === 0){
                alert("Veuillez ajouter au moins un examen à la demande.");
                event.preventDefault(); // Empêcher la soumission du formulaire
            }
            // Le champ selectedExamsInput est déjà mis à jour par mettreAJourListeExamensDemandes()
        });

    });
  </script>
</body>
</html>