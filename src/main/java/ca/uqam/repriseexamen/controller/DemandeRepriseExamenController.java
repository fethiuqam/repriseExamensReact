package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.*;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import ca.uqam.repriseexamen.service.JustificationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/demandes")
@AllArgsConstructor
public class DemandeRepriseExamenController {

    private DemandeRepriseExamenService demandeRepriseExamenService;
    private JustificationService justificationService;
    private UtilisateurAuthentifieService authentifieService;

    /**
     * Route pour récupérer les demandes de reprises d'examen en fonction du type
     * de l'utilisateur
     *
     * @return LigneDREDTO
     */
    @GetMapping("")
    public ResponseEntity<?> getAllDemandeRepriseExamen() throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        List<LigneDREDTO> demandes;

        switch (authentifie.getType()) {
            case "personnel":
                demandes = demandeRepriseExamenService.getAllDemandeRepriseExamenPersonnel();
                break;
            case "enseignant":
                demandes = demandeRepriseExamenService.getAllDemandeRepriseExamenEnseignant(authentifie.getId());
                break;
            case "etudiant":
                demandes = demandeRepriseExamenService.getAllDemandeRepriseExamenEtudiant(authentifie.getId());
                break;
            default:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDemandeRepriseExamen(@PathVariable Long id) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        LigneDREDTO demande;

        try {
            switch (authentifie.getType()) {
                case "personnel":
                    demande = demandeRepriseExamenService.getDemandeRepriseExamenPersonnelById(id);
                    break;
                case "enseignant":
                    demande = demandeRepriseExamenService.getDemandeRepriseExamenEnseignantById(id, authentifie.getId());
                    break;
                case "etudiant":
                    demande = demandeRepriseExamenService.getDemandeRepriseExamenEtudiantById(id, authentifie.getId());
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (demande != null)
            return new ResponseEntity<>(demande, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Route pour soumettre une demande de reprise d'examen
     *
     * @param nouvelleDemande body de la demande à soumettre
     * @param fichiers        un ou plusieurs fichiers
     * @return DemandeRepriseExamen la demande soumise
     */
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public DemandeRepriseExamen[] soumettreDemandeRepriseExamen(@RequestPart DemandeRepriseExamen[] nouvellesDemandes,
                                                              @RequestPart(value = "files", required = false) MultipartFile[] fichiers) {

        DemandeRepriseExamen[] dres = demandeRepriseExamenService.soumettreDemandesRepriseExamen(nouvellesDemandes);
        if (fichiers != null) {
            Arrays.stream(fichiers).forEach(fichier -> {
                try {
                    justificationService.ajouterJustification(dres, fichier);
                } catch (IOException e) {
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
                }
            });
        }

        return dres;
    }

    @PatchMapping(path = "/{id}/annuler-etudiant")
    public ResponseEntity<?> annulerDemandeParEtudiant(@PathVariable Long id) {
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.ANNULEE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/accepter")
    public ResponseEntity<?> accepterDemande(@PathVariable Long id, @RequestBody JsonNode patch) throws Exception {
        Utilisateur authentifie = authentifieService.GetAuthentifie();

        // utilisateur est un commis logiciel
        if (authentifie.getType().equals("personnel") && authentifie.getPermissions().contains(Permission.JugerCommis)) {
            demandeRepriseExamenService.ajouterDemandeDecision(id, patch, Set.of(TypeDecision.AUCUNE),
                    TypeDecision.ACCEPTATION_RECOMMANDEE);
            demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.EN_TRAITEMENT);

            // Utilisateur est un directeur
        } else if (authentifie.getType().equals("personnel")
                && authentifie.getPermissions().contains(Permission.JugerDirecteur)) {
            demandeRepriseExamenService.ajouterDemandeDecision(id, patch,
                    Set.of(TypeDecision.AUCUNE, TypeDecision.ACCEPTATION_RECOMMANDEE, TypeDecision.REJET_RECOMMANDE),
                    TypeDecision.ACCEPTEE_DIRECTEUR);
            demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.EN_TRAITEMENT);

            // Utilisateur est un enseignant
        } else if (authentifie.getType().equals("enseignant")) {
            demandeRepriseExamenService.ajouterDemandeDecision(id, patch, Set.of(TypeDecision.ACCEPTEE_DIRECTEUR),
                    TypeDecision.ACCEPTEE_ENSEIGNANT);
            demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.ACCEPTEE);

            // utilisateur non autorise
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/rejeter")
    public ResponseEntity<?> rejeterDemande(@PathVariable Long id, @RequestBody JsonNode patch) throws Exception {

        if (patch.has("details") && patch.get("details").asText().length() > 2) {

            Utilisateur authentifie = authentifieService.GetAuthentifie();

            // Utilisateur est un commis
            if (authentifie.getType().equals("personnel") && authentifie.getPermissions().contains(Permission.JugerCommis)) {
                demandeRepriseExamenService.ajouterDemandeDecision(id, patch, Set.of(TypeDecision.AUCUNE),
                        TypeDecision.REJET_RECOMMANDE);
                demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.EN_TRAITEMENT);

                // Utilisateur est un directeur
            } else if (authentifie.getType().equals("personnel")
                    && authentifie.getPermissions().contains(Permission.JugerDirecteur)) {
                demandeRepriseExamenService.ajouterDemandeDecision(id, patch,
                        Set.of(TypeDecision.AUCUNE, TypeDecision.ACCEPTATION_RECOMMANDEE, TypeDecision.REJET_RECOMMANDE),
                        TypeDecision.REJETEE_DIRECTEUR);
                demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.REJETEE);

                // Utilisateur est un enseignant
            } else if (authentifie.getType().equals("enseignant")) {
                demandeRepriseExamenService.ajouterDemandeDecision(id, patch, Set.of(TypeDecision.ACCEPTEE_DIRECTEUR),
                        TypeDecision.REJETEE_ENSEIGNANT);
                demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.REJETEE);

                // Utilisateur non autorise
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // le patch ne contient pas l attribut details ou le texte est de longueur < 3
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PatchMapping(path = "/{id}/annuler-rejet")
    public ResponseEntity<?> annulerRejetDemande(@PathVariable Long id, @RequestBody JsonNode patch) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();

        // Utilisateur est un commis
        if (authentifie.getType().equals("personnel") && authentifie.getPermissions().contains(Permission.JugerCommis)) {
            demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJET_RECOMMANDE);

            // Utilisateur est un directeur
        } else if (authentifie.getType().equals("personnel")
                && authentifie.getPermissions().contains(Permission.JugerDirecteur)) {
            demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJETEE_DIRECTEUR);

            // Utilisateur est un enseignant
        } else if (authentifie.getType().equals("enseignant")) {
            demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJETEE_ENSEIGNANT);

            // Utilisateur non autorise
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        demandeRepriseExamenService.deleteStatutDemande(id, TypeStatut.REJETEE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/{id}/messages")
    public ResponseEntity<?> envoyerMessage(@PathVariable Long id, @RequestBody JsonNode json) throws Exception {
        Utilisateur authentifie = authentifieService.GetAuthentifie();

        switch (authentifie.getType()){
            case "personnel":
                return demandeRepriseExamenService.envoyerMessage(id, TypeMessage.DEMANDE_COMMIS, json);
            case "etudiant":
                return demandeRepriseExamenService.envoyerMessage(id, TypeMessage.REPONSE_ETUDIANT, json);
            default:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping(path = "/{id}/retourner")
    public ResponseEntity<?> retournerDemande(@PathVariable Long id, @RequestBody JsonNode patch) throws Exception {

        if (patch.has("details") && patch.get("details").asText().length() > 2) {

            Utilisateur authentifie = authentifieService.GetAuthentifie();

            // utilisateur est du personnel avec la permission RetournerDemande
            if (authentifie.getType().equals("personnel") && authentifie.getPermissions()
                    .contains(Permission.RetournerDemande)) {
                demandeRepriseExamenService.ajouterDemandeDecision(id, patch, Set.of(TypeDecision.AUCUNE),
                        TypeDecision.RETOURNEE);
                demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.RETOURNEE);

                // utilisateur non autoirise
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // le patch ne contient pas l attribut details ou le texte est de longueur < 3
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping(path = "/{id}/archiver")
    public ResponseEntity<?> archiverDemande(@PathVariable Long id) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();

        // utilisateur est du personnel avec la permission ArchiverDemande
        if (authentifie.getType().equals("personnel") && authentifie.getPermissions()
                .contains(Permission.ArchiverDemande)) {
            Optional<DemandeRepriseExamen> optionalDemande = demandeRepriseExamenService.findDemandeRepriseExamen(id);
            if(optionalDemande.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            TypeStatut statutCourant = optionalDemande.get().getStatutCourant();
            Set<TypeStatut> statutsArchivables = Set.of(TypeStatut.ANNULEE, TypeStatut.REJETEE, TypeStatut.ABSENCE,
                    TypeStatut.COMPLETEE, TypeStatut.RETOURNEE);
            if(statutsArchivables.contains(statutCourant)){
                demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.ARCHIVEE);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
            // utilisateur non autoirise
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}