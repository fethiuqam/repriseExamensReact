package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.dtomapper.DemandeRepriseExamenMapper;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    private DemandeRepriseExamenRepository demandeRepriseExamenRepository;
    private DemandeRepriseExamenMapper demandeRepriseExamenMapper;

    @Override
    public List<LigneDRECommisDTO> getAllDemandeRepriseExamen() {
        List<LigneDRECommisDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDRECommisDTOBy();

        return listeLigneDRE.stream()
                .filter(dreDTO -> !dreDTO.getStatutCourant().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREEtudiantDTO> getAllDemandeRepriseExamenEtudiant(long id) {
        List<DemandeRepriseExamen> listeDRE = demandeRepriseExamenRepository.findDemandeRepriseExamenBy().stream()
                .filter(dre -> dre.getEtudiant().getId() == id).toList();

        return listeDRE.stream()
                .map(dre -> demandeRepriseExamenMapper.ligneDREEtudiantMapper(dre))
                .collect(Collectors.toList());
    }

}
