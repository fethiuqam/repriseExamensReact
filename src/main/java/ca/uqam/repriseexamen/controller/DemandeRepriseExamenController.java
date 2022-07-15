package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeDecision;
import ca.uqam.repriseexamen.model.TypeMessage;
import ca.uqam.repriseexamen.model.TypeStatut;
import ca.uqam.repriseexamen.model.Utilisateur;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import ca.uqam.repriseexamen.service.JustificationService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@AllArgsConstructor
public class DemandeRepriseExamenController {

    @Autowired
    private DemandeRepriseExamenService demandeRepriseExamenService;

    @Autowired
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
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("{\"error\":\"acces non autorisé.\"}");
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
                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body("{\"error\":\"acces non autorisé.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
        if(demande != null)
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
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(@RequestPart DemandeRepriseExamen nouvelleDemande,
                                                              @RequestPart(value = "files", required = false) MultipartFile[] fichiers) {

        DemandeRepriseExamen dre = demandeRepriseExamenService.soumettreDemandeRepriseExamen(nouvelleDemande);
        if (fichiers != null) {
            Arrays.stream(fichiers).forEach(fichier -> {
                try {
                    justificationService.ajouterJustification(dre, fichier);
                } catch (IOException e) {
                    ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
                }
            });
        }
        return dre;
    }

    @PatchMapping(path = "/{id}/accepter-commis")
    public ResponseEntity<?> accepterDemandeParCommis(@PathVariable Long id,
                                                      @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, null,
                TypeDecision.ACCEPTEE_COMMIS);
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.EN_TRAITEMENT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/rejeter-commis")
    public ResponseEntity<?> rejeterDemandeParCommis(@PathVariable Long id, @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, null,
                TypeDecision.REJETEE_COMMIS);
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.EN_TRAITEMENT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/accepter-directeur")
    public ResponseEntity<?> accepterDemandeParDirecteur(@PathVariable Long id, @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, TypeDecision.ACCEPTEE_COMMIS,
                TypeDecision.ACCEPTEE_DIRECTEUR);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/rejeter-directeur")
    public ResponseEntity<?> rejeterDemandeParDirecteur(@PathVariable Long id, @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, TypeDecision.REJETEE_COMMIS,
                TypeDecision.REJETEE_DIRECTEUR);
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.REJETEE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/accepter-enseignant")
    public ResponseEntity<?> accepterDemandeParEnseignant(@PathVariable Long id, @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, TypeDecision.ACCEPTEE_DIRECTEUR,
                TypeDecision.ACCEPTEE_ENSEIGNANT);
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.ACCEPTEE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/rejeter-enseignant")
    public ResponseEntity<?> rejeterDemandeParEnseignant(@PathVariable Long id, @RequestBody JsonNode patch) {
        demandeRepriseExamenService.ajouterDemandeDecision(id, patch, TypeDecision.ACCEPTEE_DIRECTEUR,
                TypeDecision.REJETEE_ENSEIGNANT);
        demandeRepriseExamenService.updateStatutDemande(id, TypeStatut.REJETEE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/annuler-rejet-commis")
    public ResponseEntity<?> annulerRejetDemandeParCommis(@PathVariable Long id) {
        demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJETEE_COMMIS);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/annuler-rejet-directeur")
    public ResponseEntity<?> annulerRejetDemandeParDirecteur(@PathVariable Long id) {
        demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJETEE_DIRECTEUR);
        demandeRepriseExamenService.annulerRejetStatut(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}/annuler-rejet-enseignant")
    public ResponseEntity<?> annulerRejetDemandeParEnseignant(@PathVariable Long id) {
        demandeRepriseExamenService.supprimerDemandeDecision(id, TypeDecision.REJETEE_ENSEIGNANT);
        demandeRepriseExamenService.annulerRejetStatut(id);
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
}