package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/demandes")
@AllArgsConstructor
public class DemandeRepriseExamenController {

    private DemandeRepriseExamenService demandeRepriseExamenService;

    @GetMapping("")
    public List<LigneDRECommisDTO> getAllDemandeRepriseExamen(){
        return demandeRepriseExamenService.getAllDemandeRepriseExamen();
    }

}
