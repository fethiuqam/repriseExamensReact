package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.CoursGroupeDTO;
import ca.uqam.repriseexamen.model.Utilisateur;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.CoursGroupeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/coursGroupes")
@AllArgsConstructor
public class CoursGroupeController {

    private CoursGroupeService coursGroupeService;
    private UtilisateurAuthentifieService authentifieService;

    @RequestMapping(value = "/planification", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCoursGroupesNonPlanifies() throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        if (!authentifie.getType().equals("personnel")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CoursGroupeDTO> coursGroupesNonPlanifies;
        coursGroupesNonPlanifies = coursGroupeService.getAllCoursGroupesNonPlanifies();
        return new ResponseEntity<>(coursGroupesNonPlanifies, HttpStatus.OK);
    }

}
