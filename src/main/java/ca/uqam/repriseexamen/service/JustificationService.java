package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface JustificationService {
    String nettoyerNomFichier(MultipartFile fichier, DemandeRepriseExamen dre);
    DemandeRepriseExamen ajouterJustification(DemandeRepriseExamen dre, MultipartFile file) throws IOException;
    Resource telecharger(String idJustification) throws MalformedURLException;
    void supprimerJustification(Long id);
}
