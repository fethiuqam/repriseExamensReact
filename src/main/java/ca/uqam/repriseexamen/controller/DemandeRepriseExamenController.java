package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeDecision;
import ca.uqam.repriseexamen.model.TypeStatut;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@AllArgsConstructor
public class DemandeRepriseExamenController {

    @Autowired
    private DemandeRepriseExamenService demandeRepriseExamenService;

    /**
     * Route pour récupérer les demandes de reprises d'examen en fonction du role
     * de l'utilisateur
     *
     * @return LigneDREDTO
     */
    @GetMapping("")
    public List<LigneDREDTO> getAllDemandeRepriseExamen
    (@RequestParam(required = false) Long id, @RequestParam String role) {
        switch (role) {
            case "commis":
                return demandeRepriseExamenService.getAllDemandeRepriseExamenCommis();

            case "enseignant":
                if (id != null)
                    return demandeRepriseExamenService.getAllDemandeRepriseExamenEnseignant(id);

            case "etudiant":
                if (id != null)
                    return demandeRepriseExamenService.getAllDemandeRepriseExamenEtudiant(id);

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Route pour soumettre une demande de reprise d'examen
     *
     * @param nouvelleDemande body de la demande à soumettre
     * @return DemandeRepriseExamen la demande soumise
     */
    @PostMapping("")
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(@RequestBody DemandeRepriseExamen nouvelleDemande) {
        return demandeRepriseExamenService.soumettreDemandeRepriseExamen(nouvelleDemande);
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
}