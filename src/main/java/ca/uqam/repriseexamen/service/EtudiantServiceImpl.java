package ca.uqam.repriseexamen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.uqam.repriseexamen.dao.EtudiantRepository;
import ca.uqam.repriseexamen.model.Etudiant;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    /**
     * Getter d'étudiant par le Repository
     * @param idEtudiant
     * @return
     */
    public Optional<Etudiant> getEtudiant(Long idEtudiant) {
        return etudiantRepository.findById(idEtudiant);
    }
}
