package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.JustificationRepository;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.Justification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
@AllArgsConstructor
public class JustificationServiceImpl implements JustificationService{

    private final Path cheminStockageFichier;
    @Autowired
    private DemandeRepriseExamenRepository demandeRepriseExamenRepository;
    @Autowired
    private JustificationRepository justificationRepository;

    @Autowired
    public JustificationServiceImpl(Environment env) throws IOException {
        this.cheminStockageFichier = Paths.get(env.getProperty("app.file.upload-dir")).toAbsolutePath().normalize();
        Files.createDirectories(this.cheminStockageFichier);
    }


    /**
     * Nettoyer nom fichier
     * @param fichier
     * @return
     */
    @Override
    public String nettoyerNomFichier(MultipartFile fichier, DemandeRepriseExamen dre){

        int index = fichier.getOriginalFilename().lastIndexOf('.');
        String fichierDate = fichier.getOriginalFilename().
                substring(0, fichier.getOriginalFilename().lastIndexOf('.')) + "_" +
                java.time.LocalDate.now();

        String nomFichier = dre.getId() + "_" + fichierDate.replaceAll("[-_ ]", "_") +
                "." + fichier.getOriginalFilename().substring(index + 1);

        return nomFichier;
    }


    /**
     * Ajouter fichier et justification et les relier avec la dre
     * @param dre
     * @param fichier
     * @throws IOException
     */
    @Override
    public DemandeRepriseExamen ajouterJustification(DemandeRepriseExamen dre, MultipartFile fichier) throws IOException {

        String nomFichier = nettoyerNomFichier(fichier, dre);
        Path targetLocation = this.cheminStockageFichier.resolve(nomFichier);
        Files.copy(fichier.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING );

        Justification justification = justificationRepository.save(
                Justification.builder().demandeRepriseExamen(dre).nomFichier(nomFichier).build());

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/justifications/")
                .path(Long.toString(justification.getId()))
                .path("/preview")
                .toUriString();
        justification.setUrl(url);
        justificationRepository.save(justification);

        dre.getListeJustification().add(justification);
        return demandeRepriseExamenRepository.save(dre);
    }


    /**
     * Methode pour telecharger/preview un fichier a partir du url
     *
     * @param idJustification
     * @return resource (fichier)
     * @throws MalformedURLException
     */
    public Resource telecharger(String idJustification) throws MalformedURLException {
        String nomFichier = justificationRepository.findById(Long.parseLong(idJustification)).get().getNomFichier();
        Path file = this.cheminStockageFichier.resolve(nomFichier);
        System.out.println(nomFichier);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Erreur lors de la lecture du fichier!");
        }
    }

    @Override
    public void supprimerJustification(Long id) {
        if (justificationRepository.existsById(id)) {
           justificationRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("La justification à supprimer n'existe pas.");
        }
    }


}
