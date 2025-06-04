package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Hospitalisation;
import model.Medecin;
import model.Patient;
import service.HospitalisationService;
import dao.PatientDAO;
import exception.AucunLitDisponibleException; // Assure-toi que ce chemin d'import est correct

import java.io.IOException;
import java.sql.Date; // java.sql.Date
import java.sql.SQLException;
import java.time.LocalDate;
// import java.time.temporal.ChronoUnit; // Pas directement utilisé ici, mais LocalDate.plus... l'utilise en interne
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/creerHospitalisation")
public class CreerHospitalisationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("CreerHospitalisationServlet: Entrée dans doPost.");

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("CreerHospitalisationServlet: Session est NULL. Redirection vers login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        if (session.getAttribute("medecin") == null) {
            System.out.println("CreerHospitalisationServlet: Attribut 'medecin' est NULL dans la session. Redirection vers login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        System.out.println("CreerHospitalisationServlet: Session et médecin valides. Poursuite du traitement.");

        Medecin medecin = (Medecin) session.getAttribute("medecin");
        PatientDAO patientDAO = new PatientDAO(); // Peut être instancié une seule fois

        try {
            // 1. Récupérer et valider l'ID du patient
            String idPatientStr = request.getParameter("idPatient");
            if (idPatientStr == null || idPatientStr.isEmpty()) {
                request.setAttribute("error", "Veuillez sélectionner un patient.");
                rechargerPageAvecErreur(request, response, patientDAO);
                return;
            }
            int idPatient = Integer.parseInt(idPatientStr); // Peut lever NumberFormatException

            // 2. Récupérer l'objet Patient complet
            Patient patient = patientDAO.getPatientById(idPatient);
            if (patient == null) {
                request.setAttribute("error", "Patient sélectionné (ID: " + idPatient + ") non trouvé.");
                rechargerPageAvecErreur(request, response, patientDAO);
                return;
            }

            // 3. Récupérer les autres informations du formulaire
            String nomHopital = request.getParameter("nomHopital");
            String service = request.getParameter("service"); // Nom du service du médecin
            String dureeSelectionnee = request.getParameter("duree"); // Valeur du <select>
            String motif = request.getParameter("motif");
            String diagnosticInitial = request.getParameter("diagnosticInitial");
            String etat = "En cours"; // Valeur par défaut

            // 4. Valider la durée
            if (dureeSelectionnee == null || dureeSelectionnee.trim().isEmpty()) {
                request.setAttribute("error", "Veuillez sélectionner une durée prévue pour l'hospitalisation.");
                rechargerPageAvecErreur(request, response, patientDAO);
                return;
            }

            // 5. Calculer la date de sortie prévue
            LocalDate dateAdmissionPourCalcul = LocalDate.now();
            LocalDate dateSortiePrevueCalculated;
            try {
                dateSortiePrevueCalculated = calculerDateSortie(dateAdmissionPourCalcul, dureeSelectionnee);
                System.out.println("CreerHospitalisationServlet: Durée='" + dureeSelectionnee + 
                                   "', Date Admission (calcul)='" + dateAdmissionPourCalcul + 
                                   "', Date Sortie Prévue (calculée)='" + dateSortiePrevueCalculated + "'");
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Format de durée invalide: " + e.getMessage());
                rechargerPageAvecErreur(request, response, patientDAO);
                return;
            }

            // 6. Créer l'objet Hospitalisation
            Hospitalisation hosp = new Hospitalisation();
            hosp.setIdPatient(idPatient);
            hosp.setNomHopital(nomHopital);
            hosp.setService(service);
            hosp.setDuree(dureeSelectionnee); // Stocke la chaîne de durée originale (ex: "7 jours")
            hosp.setEtat(etat);
            hosp.setMotif(motif);
            hosp.setDiagnosticInitial(diagnosticInitial);
            hosp.setIdMedecin(medecin.getId());
            // La date d'admission sera mise par CURDATE() dans la BDD par défaut
            // hosp.setDateAdmission(java.sql.Date.valueOf(dateAdmissionPourCalcul)); // Optionnel

            if (dateSortiePrevueCalculated != null) {
                hosp.setDateSortiePrevue(java.sql.Date.valueOf(dateSortiePrevueCalculated));
            } else {
                hosp.setDateSortiePrevue(null); // Au cas où, mais calculerDateSortie devrait lever une exception avant
            }
            // litId sera setté par HospitalisationService

            // 7. Appeler le service pour créer l'hospitalisation et attribuer le lit
            HospitalisationService hospService = new HospitalisationService();
            boolean success = hospService.creerHospitalisationAvecLit(hosp, patient);

            if (success) {
                String successMessage = "Hospitalisation créée avec succès.";
                if (hosp.getLitId() != null) {
                    successMessage += " Lit ID: " + hosp.getLitId() + " attribué.";
                }
                if (hosp.getDateSortiePrevue() != null) {
                    successMessage += " Sortie prévue le: " + hosp.getDateSortiePrevue().toString() + ".";
                }
                // Rediriger vers la page du formulaire (ou une autre page de confirmation) avec un message de succès
                response.sendRedirect(request.getContextPath() + "/nouvelleHospitalisationForm?success=" + java.net.URLEncoder.encode(successMessage, "UTF-8"));
            } else {
                // Ce cas est atteint si creerHospitalisationAvecLit retourne false sans lever d'exception
                // (par exemple, échec d'insertion dans le DAO non rattrapé par une exception)
                request.setAttribute("error", "Échec de la création de l'hospitalisation (raison indéterminée).");
                rechargerPageAvecErreur(request, response, patientDAO);
            }

        } catch (AucunLitDisponibleException e) {
            request.setAttribute("error", e.getMessage());
            rechargerPageAvecErreur(request, response, patientDAO);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format numérique invalide pour l'ID du patient.");
            rechargerPageAvecErreur(request, response, patientDAO);
        } catch (SQLException e) {
            request.setAttribute("error", "Erreur de base de données: " + e.getMessage());
            e.printStackTrace(); // Important pour le débogage serveur
            rechargerPageAvecErreur(request, response, patientDAO);
        } catch (IllegalArgumentException e) { // Attrape les erreurs de validation (durée, sexe du patient dans le service)
            request.setAttribute("error", "Données d'entrée invalides: " + e.getMessage());
            e.printStackTrace();
            rechargerPageAvecErreur(request, response, patientDAO);
        } catch (Exception e) { // Catch-all pour les autres erreurs inattendues
            request.setAttribute("error", "Erreur technique inattendue: " + e.getMessage());
            e.printStackTrace();
            rechargerPageAvecErreur(request, response, patientDAO);
        }
    }

    /**
     * Calcule la date de sortie prévue en ajoutant une durée à une date de début.
     * La durée doit correspondre aux valeurs du select (ex: "7 jours", "2 semaines", "1 mois").
     */
    private LocalDate calculerDateSortie(LocalDate dateDebut, String dureeSelectionnee) throws IllegalArgumentException {
        if (dureeSelectionnee == null || dureeSelectionnee.trim().isEmpty()) {
            throw new IllegalArgumentException("La durée sélectionnée ne peut pas être vide.");
        }

        String dureeNormalisee = dureeSelectionnee.trim().toLowerCase();
        // Regex adaptée aux formats attendus du select: "X jour(s)", "Y semaine(s)", "Z moi(s)"
        Pattern pattern = Pattern.compile("^(\\d+)\\s+(jour|jours|semaine|semaines|moi|mois)$");
        Matcher matcher = pattern.matcher(dureeNormalisee);

        if (matcher.matches()) {
            int quantite = Integer.parseInt(matcher.group(1));
            String unite = matcher.group(2);

            if (quantite <= 0) {
                throw new IllegalArgumentException("La quantité pour la durée doit être positive.");
            }

            if (unite.startsWith("jour")) {
                return dateDebut.plusDays(quantite);
            } else if (unite.startsWith("semaine")) {
                return dateDebut.plusWeeks(quantite);
            } else if (unite.startsWith("moi")) {
                return dateDebut.plusMonths(quantite);
            } else {
                // Ne devrait pas être atteint si les valeurs du select sont correctes et la regex aussi
                throw new IllegalArgumentException("Unité de durée non reconnue dans la valeur sélectionnée: " + unite);
            }
        } else {
            // Si les valeurs du select ne correspondent pas STRICTEMENT à la regex
            throw new IllegalArgumentException(
                "Format de durée non reconnu à partir de la sélection: '" + dureeSelectionnee + "'. " +
                "Vérifiez les 'value' des options du select (ex: '7 jours', '2 semaines', '1 mois')."
            );
        }
    }

    // Méthode utilitaire pour réafficher le formulaire avec un message d'erreur
    private void rechargerPageAvecErreur(HttpServletRequest request, HttpServletResponse response, PatientDAO patientDAO)
            throws ServletException, IOException {
        try {
            // Recharger la liste des patients pour la JSP
            request.setAttribute("patientsList", patientDAO.getAllActivePatients());
        } catch (SQLException sqlEx) {
            System.err.println("Erreur lors du rechargement de la liste des patients dans rechargerPageAvecErreur: " + sqlEx.getMessage());
            sqlEx.printStackTrace();
            // Ajouter un message d'erreur supplémentaire si le rechargement échoue
            String currentError = (String) request.getAttribute("error");
            request.setAttribute("error", currentError + " (Attention: La liste des patients n'a pas pu être rechargée.)");
        }
        // Assurez-vous que ce chemin est correct pour votre JSP
        request.getRequestDispatcher("nouvelleHospitalisation.jsp").forward(request, response);
    }
}