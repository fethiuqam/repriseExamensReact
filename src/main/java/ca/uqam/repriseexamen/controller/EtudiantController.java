package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.Utilisateur;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.EtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController
@AllArgsConstructor
public class EtudiantController {

    private EtudiantService etudiantService;
    private UtilisateurAuthentifieService authentifieService;

    @RequestMapping(value = "etudiants/{idEtudiant}", method = RequestMethod.GET)
    public ResponseEntity<?> getEtudiant(@PathVariable Long idEtudiant){
        return new ResponseEntity<>(etudiantService.getEtudiant(idEtudiant), HttpStatus.OK);
    }

    @RequestMapping(value = "etudiants/{idEtudiant}/historique", method = RequestMethod.GET)
    public ResponseEntity<?> getHistorique(@PathVariable Long idEtudiant) throws Exception {

        Utilisateur authentifie = authentifieService.GetAuthentifie();
        if(!authentifie.getType().equals("personnel"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(etudiantService.getHistoriqueEtudiant(idEtudiant), HttpStatus.OK);
    }
}
