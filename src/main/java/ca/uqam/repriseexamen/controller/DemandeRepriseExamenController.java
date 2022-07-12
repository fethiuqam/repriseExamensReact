package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import ca.uqam.repriseexamen.service.JustificationService;
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
    public List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant
            (@RequestParam(required = false) Long id, @RequestParam(required = true) String type) {
        switch (type){
            case "personnel":
                return demandeRepriseExamenService.getAllDemandeRepriseExamenPersonnel();

            case "enseignant":
                if (id != null)
                    return demandeRepriseExamenService.getAllDemandeRepriseExamenEnseignant(id);

            case "etudiant":
                if(id != null)
                    return demandeRepriseExamenService.getAllDemandeRepriseExamenEtudiant(id);

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Route pour soumettre une demande de reprise d'examen
     * 
     * @param nouvelleDemande body de la demande à soumettre
     * @param fichiers un ou plusieurs fichiers
     * @return DemandeRepriseExamen la demande soumise
     */
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(@RequestPart DemandeRepriseExamen nouvelleDemande,
                                                              @RequestPart("files") MultipartFile[] fichiers) {

        DemandeRepriseExamen dre = demandeRepriseExamenService.soumettreDemandeRepriseExamen(nouvelleDemande);
        Arrays.asList(fichiers).stream().forEach(fichier -> {
            try {
                justificationService.ajouterJustification(dre, fichier);
            } catch (IOException e) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
            }
        });
        return dre;
    }

}
