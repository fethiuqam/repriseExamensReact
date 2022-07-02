package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.Etudiant;
import ca.uqam.repriseexamen.service.EtudiantService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@RestController
@RequestMapping("api/etudiants")
@AllArgsConstructor
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @RequestMapping(value = "/{idEtudiant}", method = RequestMethod.GET)
    public Optional<Etudiant> getEtudiant(@PathVariable Long idEtudiant){
        return etudiantService.getEtudiant(idEtudiant);
    }
}
