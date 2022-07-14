package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.EtudiantRepository;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.Etudiant;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private EtudiantRepository etudiantRepository;

    private DemandeRepriseExamenRepository demandeRepository;

    /**
     * Getter d'étudiant par le Repository
     * @param idEtudiant
     * @return
     */
    public Optional<Etudiant> getEtudiant(Long idEtudiant) {
        return etudiantRepository.findById(idEtudiant);
    }

    @Override
    public List<LigneHistoriqueEtudiantDTO> getHistoriqueEtudiant(long id) {
        List<LigneHistoriqueEtudiantDTO> listeLigneHistorique = demandeRepository
                .findLigneHistoriqueEtudiantDTOByEtudiantId(id);

        return listeLigneHistorique.stream()
                .filter(dre -> !dre.getStatut().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }
}
