package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.Etudiant;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import ca.uqam.repriseexamen.service.EtudiantService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/etudiants")
@AllArgsConstructor
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private DemandeRepriseExamenService demandeRepriseExamenService;

    @RequestMapping(value = "/{idEtudiant}", method = RequestMethod.GET)
    public Optional<Etudiant> getEtudiant(@PathVariable Long idEtudiant){
        return etudiantService.getEtudiant(idEtudiant);
    }

    @RequestMapping(value = "/{idEtudiant}/historique", method = RequestMethod.GET)
    public List<LigneHistoriqueEtudiantDTO> getHistorique(@PathVariable Long idEtudiant, @RequestParam String role) {
        if(!role.equals("commis"))
            throw new IllegalArgumentException();

        return demandeRepriseExamenService.getHistoriqueEtudiant(idEtudiant);
    }
}
