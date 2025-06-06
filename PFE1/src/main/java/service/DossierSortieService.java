package service;

import dao.DemandeExamenDAO; // Pour lister les examens demandés
import dao.DiagnosticDAO;   // Si tu décides d'utiliser la table diagnostic
import dao.HospitalisationDAO;
import dao.MedecinDAO;      // Pour les détails du médecin
import dao.PatientDAO;      // Pour les détails du patient
//import dao.PrescriptionDAO; // Si tu l'intègres plus tard pour l'ordonnance
import dao.DossierSortieDAO; // Nouveau DAO pour la table dossier_sortie

import model.DemandeExamen;
import model.Diagnostic;
import model.Hospitalisation;
import model.Medecin;
import model.Patient;
//import model.Prescription; // Si utilisé
import model.DossierSortie;  // Nouveau modèle

// Importe ta bibliothèque PDF choisie (exemple avec Apache PDFBox)
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts; // Pour PDFBox 2.x

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date; // java.util.Date pour formater

public class DossierSortieService {

    private HospitalisationDAO hospitalisationDAO;
    private PatientDAO patientDAO;
    private MedecinDAO medecinDAO; // Assure-toi d'avoir un MedecinDAO qui peut récupérer par ID avec nom/prénom/spécialité
    private DemandeExamenDAO demandeExamenDAO;
    // private PrescriptionDAO prescriptionDAO; // À décommenter plus tard
    private DossierSortieDAO dossierSortieDAO;

    // Configure le chemin de base où les PDF seront stockés
    // IMPORTANT: Ce chemin doit être accessible en écriture par ton application Tomcat.
    // Il est préférable de le configurer via un fichier de propriétés ou une variable d'environnement.
    private static final String BASE_PDF_STORAGE_PATH = "C:/PFEPDFs/dossiers_sortie/"; // EXEMPLE - À ADAPTER ABSOLUMENT

    public DossierSortieService() {
        hospitalisationDAO = new HospitalisationDAO();
        patientDAO = new PatientDAO();
        medecinDAO = new MedecinDAO();
        demandeExamenDAO = new DemandeExamenDAO();
        // prescriptionDAO = new PrescriptionDAO();
        dossierSortieDAO = new DossierSortieDAO();

        // Créer le répertoire de stockage s'il n'existe pas
        File storageDir = new File(BASE_PDF_STORAGE_PATH);
        if (!storageDir.exists()) {
            if (storageDir.mkdirs()) {
                System.out.println("DossierSortieService: Répertoire de stockage PDF créé: " + BASE_PDF_STORAGE_PATH);
            } else {
                System.err.println("DossierSortieService: ERREUR CRITIQUE - Impossible de créer le répertoire de stockage PDF: " + BASE_PDF_STORAGE_PATH);
                // Tu pourrais lever une exception ici pour empêcher le démarrage si le stockage est vital
            }
        }
    }

    public DossierSortie genererEtEnregistrerDossierPDF(int hospitalisationId, int genererParUtilisateurId) throws SQLException, IOException, Exception {
        System.out.println("DossierSortieService: Début de la génération du PDF pour hospitalisation ID: " + hospitalisationId);

        // 1. Récupérer toutes les données nécessaires
        // Utilise getByIdComplet si tu as une telle méthode qui fait toutes les jointures,
        // sinon, getById puis charge les détails du patient et médecin séparément.
        Hospitalisation hosp = hospitalisationDAO.getById(hospitalisationId); // Assure-toi que cette méthode charge bien les nouveaux champs texte
        if (hosp == null) {
            throw new Exception("Hospitalisation non trouvée pour ID: " + hospitalisationId);
        }

        Patient patient = patientDAO.getPatientById(hosp.getIdPatient()); // getPatientById devrait charger nom, prenom, dateN, adresse, sexe, cin
        if (patient == null) {
            throw new Exception("Patient non trouvé pour ID: " + hosp.getIdPatient());
        }

        Medecin medecin = medecinDAO.getMedecinWithDetailsById(hosp.getIdMedecin()); // Méthode à créer/adapter dans MedecinDAO
                                                                         // pour avoir nom, prenom, specialite
        if (medecin == null) {
            System.err.println("Avertissement: Médecin non trouvé pour ID: " + hosp.getIdMedecin() + ". Le nom du médecin sera manquant dans le PDF.");
            // Créer un objet médecin vide ou avec des infos par défaut pour éviter NullPointerException
            medecin = new Medecin();
            medecin.setNom("N/A");
            medecin.setPrenom("");
            medecin.setSpecialite("N/A");
        }


        List<DemandeExamen> demandesExamens = demandeExamenDAO.getDemandesParHospitalisation(hospitalisationId); // Méthode à créer dans DemandeExamenDAO
        // List<Prescription> ordonnancesSortie = prescriptionDAO.getOrdonnanceDeSortie(hospitalisationId); // À décommenter plus tard

        // Formatter pour les dates
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHeure = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // 2. Générer le PDF avec PDFBox
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float yPosition = 750; // Position Y de départ (haut de la page)
                float margin = 50;
                float leading = 14.5f; // Interligne

                // --- En-tête Hôpital ---
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(hosp.getNomHopital() != null ? hosp.getNomHopital().toUpperCase() : "HÔPITAL NON SPÉCIFIÉ");
                contentStream.endText();
                yPosition -= leading * 2;

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("DOSSIER DE SORTIE PATIENT");
                contentStream.endText();
                yPosition -= leading * 2.5f;

                // --- Informations Patient ---
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 11);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Patient:");
                contentStream.endText();
                yPosition -= leading;

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 10, yPosition);
                contentStream.showText("Nom & Prénom: " + (patient.getPrenom() != null ? patient.getPrenom() : "") + " " + (patient.getNom() != null ? patient.getNom().toUpperCase() : ""));
                contentStream.newLineAtOffset(0, -leading);
                contentStream.showText("Date de Naissance: " + (patient.getDateNaissance() != null ? sdf.format(patient.getDateNaissance()) : "N/A"));
                contentStream.newLineAtOffset(0, -leading);
                contentStream.showText("Sexe: " + (patient.getSexe() != null ? patient.getSexe() : "N/A"));
                contentStream.newLineAtOffset(0, -leading);
                contentStream.showText("Adresse: " + (patient.getAdresse() != null ? patient.getAdresse() : "N/A"));
                contentStream.newLineAtOffset(0, -leading);
                contentStream.showText("CIN: " + (patient.getCin() != null ? patient.getCin() : "N/A"));
                contentStream.endText();
                yPosition -= leading * 6;

                // --- Informations Hospitalisation ---
                yPosition = addSectionTitle(contentStream, document, page, "Informations sur l'Hospitalisation", margin, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Service d'hospitalisation: " + (hosp.getService() != null ? hosp.getService() : "N/A"), margin + 10, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Médecin traitant: Dr. " + (medecin.getPrenom() != null ? medecin.getPrenom() : "") + " " + (medecin.getNom() != null ? medecin.getNom().toUpperCase() : "") + (medecin.getSpecialite()!=null ? " ("+medecin.getSpecialite()+")" : ""), margin + 10, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Date d'admission: " + (hosp.getDateAdmission() != null ? sdf.format(hosp.getDateAdmission()) : "N/A"), margin + 10, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Date de sortie: " + (hosp.getDateSortieReelle() != null ? sdfHeure.format(hosp.getDateSortieReelle()) : "N/A"), margin + 10, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Motif d'admission: " + (hosp.getMotif() != null ? hosp.getMotif() : "N/A"), margin + 10, yPosition, leading);
                yPosition = addTextLine(contentStream, document, page, "Diagnostic initial: " + (hosp.getDiagnosticInitial() != null ? hosp.getDiagnosticInitial() : "N/A"), margin + 10, yPosition, leading);
                if (hosp.getLitId() != null) {
                     yPosition = addTextLine(contentStream, document, page, "Lit: " + (hosp.getNumeroLit()!=null ? hosp.getNumeroLit() : "N/A") + " / Chambre: " + (hosp.getNumeroChambre()!=null?hosp.getNumeroChambre():"N/A") + " (Service: " + (hosp.getNomServiceChambre()!=null?hosp.getNomServiceChambre():"N/A") + ")", margin + 10, yPosition, leading);
                }
                yPosition -= leading;


                // --- Diagnostics de Sortie ---
                yPosition = addSectionTitle(contentStream, document, page, "Diagnostics de Sortie", margin, yPosition, leading);
                yPosition = addMultiLineText(contentStream, document, page, hosp.getDiagnosticsSortie(), margin + 10, yPosition, leading, 550);
                yPosition -= leading;

                // --- Compte-Rendu d'Hospitalisation (CRH) ---
                yPosition = addSectionTitle(contentStream, document, page, "Compte-Rendu d'Hospitalisation", margin, yPosition, leading);
                yPosition = addMultiLineText(contentStream, document, page, hosp.getCompteRenduHospitalisation(), margin + 10, yPosition, leading, 550);
                yPosition -= leading;
                
                // --- Ordonnance de Sortie (Simplifié pour l'instant, sera le contenu TEXT) ---
                // if (ordonnancesSortie != null && !ordonnancesSortie.isEmpty()) {
                //     yPosition = addSectionTitle(contentStream, document, page, "Ordonnance de Sortie", margin, yPosition, leading);
                //     for (Prescription p : ordonnancesSortie) {
                //         yPosition = addMultiLineText(contentStream, document, page, p.getContenu(), margin + 10, yPosition, leading, 550);
                //     }
                //     yPosition -= leading;
                // }

                // --- Examens Demandés ---
                if (demandesExamens != null && !demandesExamens.isEmpty()) {
                    yPosition = addSectionTitle(contentStream, document, page, "Examens Demandés durant l'hospitalisation", margin, yPosition, leading);
                    for (DemandeExamen de : demandesExamens) {
                        // Il faudrait joindre le nom de l'examen depuis la table examen
                        // Pour l'instant, on affiche l'ID et les notes.
                        String nomExamen = de.getExamen() != null ? de.getExamen().getNomExamen() : "Examen ID " + de.getIdExamen(); // Supposant que DemandeExamen a getExamen()
                        yPosition = addTextLine(contentStream, document, page, "- " + sdf.format(de.getDateDemande()) + ": " + nomExamen + (de.getNotesMedecin()!=null ? " (Notes: "+de.getNotesMedecin()+")" : ""), margin + 10, yPosition, leading);
                    }
                    yPosition -= leading;
                }

                // --- Instructions de Sortie ---
                yPosition = addSectionTitle(contentStream, document, page, "Instructions de Sortie", margin, yPosition, leading);
                yPosition = addMultiLineText(contentStream, document, page, hosp.getInstructionsSortie(), margin + 10, yPosition, leading, 550);
                yPosition -= leading;

                // --- Rendez-vous de Suivi ---
                yPosition = addSectionTitle(contentStream, document, page, "Rendez-vous de Suivi", margin, yPosition, leading);
                yPosition = addMultiLineText(contentStream, document, page, hosp.getInformationsRendezVousSuivi(), margin + 10, yPosition, leading, 550);

                // Ajouter un pied de page (Date de génération, etc.)
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 8);
                contentStream.newLineAtOffset(margin, margin / 2);
                contentStream.showText("Document généré le: " + sdfHeure.format(new Date()) + " par le système CarePath.");
                contentStream.endText();
            } // contentStream est fermé ici

            // 3. Sauvegarder le PDF
            String patientNameSanitized = (patient.getNom() + "_" + patient.getPrenom()).replaceAll("[^a-zA-Z0-9_]", "");
            String nomFichier = "DossierSortie_" + patientNameSanitized + "_H" + hospitalisationId + ".pdf";
            String cheminComplet = BASE_PDF_STORAGE_PATH + nomFichier;

            File pdfFile = new File(cheminComplet);
            document.save(pdfFile);
            System.out.println("DossierSortieService: PDF généré et sauvegardé: " + cheminComplet);

            // 4. Enregistrer les métadonnées dans la table dossier_sortie
            DossierSortie ds = new DossierSortie();
            ds.setIdHospitalisation(hospitalisationId);
            ds.setNomFichier(nomFichier);
            ds.setCheminFichier(cheminComplet); // Ou un chemin relatif si BASE_PDF_STORAGE_PATH est connu du serveur de téléchargement
            ds.setTailleFichierOctets((int) pdfFile.length());
            ds.setGenererParUtilisateurId(genererParUtilisateurId);
            // date_generation est DEFAULT CURRENT_TIMESTAMP

            boolean metaSaved = dossierSortieDAO.inserer(ds); // Méthode à créer dans DossierSortieDAO
            if(!metaSaved) {
                 System.err.println("DossierSortieService: ERREUR - Impossible de sauvegarder les métadonnées du dossier de sortie pour hosp ID " + hospitalisationId);
                 // Que faire ? Le PDF est créé mais pas tracé. Peut-être le supprimer ? Ou juste loguer.
            } else {
                 System.out.println("DossierSortieService: Métadonnées du dossier de sortie sauvegardées, ID: " + ds.getIdDossierSortie());
            }
            return ds; // Retourner l'objet DossierSortie avec son ID et chemin

        } // PDDocument est fermé ici
    }


    // ----- Helper methods pour PDFBox -----
    private float addSectionTitle(PDPageContentStream contentStream, PDDocument document, PDPage page, String title, float x, float y, float leading) throws IOException {
        if (y < 100) { // Gestion de nouvelle page simple
            contentStream.close();
            page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            y = 750;
        }
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(title);
        contentStream.endText();
        return y - leading * 1.5f;
    }

    private float addTextLine(PDPageContentStream contentStream, PDDocument document, PDPage page, String text, float x, float y, float leading) throws IOException {
         if (y < 70) { // Gestion de nouvelle page simple
            contentStream.close();
            page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            y = 750;
        }
        if (text != null) {
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
        return y - leading;
    }
    
    // Méthode pour ajouter du texte multiligne avec gestion simple des sauts de page
    private float addMultiLineText(PDPageContentStream contentStream, PDDocument document, PDPage page, String text, float x, float y, float leading, float maxWidth) throws IOException {
        if (text == null || text.trim().isEmpty()) {
            return addTextLine(contentStream, document, page, "N/A", x, y, leading);
        }

        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\n"); // Gérer les sauts de ligne explicites

        PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        float fontSize = 10;

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();
            for (String word : words) {
                float width = font.getStringWidth(currentLine.toString() + word + " ") / 1000 * fontSize;
                if (width > maxWidth && currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word + " ");
                } else {
                    currentLine.append(word).append(" ");
                }
            }
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString().trim());
            }
        }
        
        contentStream.setFont(font, fontSize);
        for (String line : lines) {
            if (y < 70) { // Check for new page
                contentStream.endText(); // Finir le texte actuel avant de fermer
                contentStream.close();
                page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page); // Nouveau contentStream pour la nouvelle page
                y = 750; // Réinitialiser y
                contentStream.setFont(font, fontSize); // Réappliquer la police
            }
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(line);
            contentStream.endText();
            y -= leading;
        }
        return y;
    }
}