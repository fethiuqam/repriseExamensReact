package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.EtudiantRepository;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.Etudiant;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
     * @param idEtudiant id de l'etudiant
     * @return Etudiant associe au id donné
     */
    public Optional<Etudiant> getEtudiant(Long idEtudiant) {
        return etudiantRepository.findById(idEtudiant);
    }

    @Override
    public List<LigneHistoriqueEtudiantDTO> getHistoriqueEtudiant(long id) {
        List<LigneHistoriqueEtudiantDTO> listeLigneHistorique = demandeRepository
                .findLigneHistoriqueEtudiantDTOByEtudiantId(id);

        return listeLigneHistorique.stream()
                .filter(dre -> !dre.getStatutCourant().equals(TypeStatut.ENREGISTREE))
                .filter(dre -> dre.getDateHeureSoumission().isAfter(LocalDateTime.now().minusYears(3)))
                .collect(Collectors.toList());
    }
}
