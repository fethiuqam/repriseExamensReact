package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
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

    private DemandeRepriseExamenRepository DemandeRepriseExamenRepository;
    private DemandeRepriseExamenMapper demandeRepriseExamenMapper;
    @Override
    public List<LigneDRECommisDTO> getAllDemandeRepriseExamen() {
        List<DemandeRepriseExamen> listeDRE = DemandeRepriseExamenRepository.findAll();
        return listeDRE.stream()
                .map(dre -> demandeRepriseExamenMapper.ligneDRECommisMapper(dre))
                .filter(dreDTO -> !dreDTO.getStatutCourant().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }
}
