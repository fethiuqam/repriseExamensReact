package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.RepriseDTO;
import ca.uqam.repriseexamen.model.Reprise;
import ca.uqam.repriseexamen.model.Utilisateur;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.RepriseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/reprises")
@AllArgsConstructor
@Validated
public class RepriseController {

    private RepriseService repriseService;
    private UtilisateurAuthentifieService authentifieService;

    @PostMapping("")
    public ResponseEntity<Reprise> planifierReprise(@Valid @RequestBody RepriseDTO repriseDTO) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        if (!authentifie.getType().equals("personnel")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Reprise reprisePlanifiee = repriseService.planifierReprise(repriseDTO);
        return new ResponseEntity<>(reprisePlanifiee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reprise> modifierPlanification (@Valid @RequestBody RepriseDTO repriseDTO,
                                                          @PathVariable Long id) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        if (!authentifie.getType().equals("personnel")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Reprise repriseModifiee = repriseService.modifierPlanification(repriseDTO, id);
        return new ResponseEntity<>(repriseModifiee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> annulerPlanification(@PathVariable Long id) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        if (!authentifie.getType().equals("personnel")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        repriseService.annulerPlanification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
