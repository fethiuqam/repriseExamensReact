package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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


    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<DemandeRepriseExamen> updateDemandeRepriseExamen(@PathVariable Long id,
                                                                           @RequestBody JsonPatch patch) {
        try {
            demandeRepriseExamenService.patchDemandeRepriseExamen(id, patch);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}