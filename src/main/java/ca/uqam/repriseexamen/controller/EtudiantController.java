package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/etudiants")
@AllArgsConstructor
public class EtudiantController {

    private DemandeRepriseExamenService demandeRepriseExamenService;

    @GetMapping("{id}/demandes")
    public List<LigneDREEtudiantDTO> getAllDemandeRepriseExamen(@PathVariable long id) {
        return demandeRepriseExamenService.getAllDemandeRepriseExamenEtudiant(id);
    }

}
