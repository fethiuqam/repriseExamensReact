package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.service.JustificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@AllArgsConstructor
public class JustificationController {

    @Autowired
    private JustificationService justificationService;

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

}
