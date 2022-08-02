package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import ca.uqam.repriseexamen.service.JustificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@AllArgsConstructor
public class JustificationController {

    @Autowired
    private JustificationService justificationService;

    @Autowired
    private DemandeRepriseExamenService demandeRepriseExamenService;

    /**
     * Route pour faire le preview d'un fichier (justification) a partir du url
     *
     * @param idJustification
     * @return ResponseEntity reponse serveur pour faire le preview
     * @throws IOException
     */
    @RequestMapping("api/justifications/{idJustification}/preview")
    @ResponseBody
    public ResponseEntity<Resource> previewFichier(@PathVariable String idJustification) throws IOException {
        Resource fichier = justificationService.telecharger(idJustification);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"{0}\"", fichier.getFilename()))
                .contentLength(fichier.contentLength())
                .contentType(MediaType.parseMediaType(Files.probeContentType(fichier.getFile().toPath())))
                .body(fichier);
    }

    /**
     * Route pour ajouter une nouvelle piece justificative
     * pour une DRE existante
     *
     * @param id identifiant de la DRE
     * @return ResponseEntity reponse sans contenu de reussite
     * @throws IOException
     */
    @PostMapping(value = "api/justifications", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> ajouterJustification(@RequestPart(value="id") Long id, @RequestPart(value = "file") MultipartFile fichier) {

        DemandeRepriseExamen[] demandeTrouvee = new DemandeRepriseExamen[1];
        demandeTrouvee[0] = demandeRepriseExamenService.findDemandeRepriseExamen(id).orElseThrow(ResourceNotFoundException::new);
     
        if (fichier != null) {
            try {
                justificationService.ajouterJustification(demandeTrouvee, fichier);
            } catch (IOException e) {
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED);
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Route pour supprimer une pièce justificative
     * pour une DRE existante
     *
     * @param id identifiant de la justification
     * @return ResponseEntity reponse sans contenu de reussite
     * @throws IOException
     */
    @DeleteMapping(value = "api/justifications/{id}")
    public ResponseEntity<?> supprimerJustification(@PathVariable Long id) {
        justificationService.supprimerJustification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
