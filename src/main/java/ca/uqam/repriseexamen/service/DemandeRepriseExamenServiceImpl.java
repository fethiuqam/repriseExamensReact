package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    private DemandeRepriseExamenRepository DemandeRepriseExamenRepository;
    @Override
    public List<DemandeRepriseExamen> getAllDemandeRepriseExamen() {
        return DemandeRepriseExamenRepository.findAll();
    }
}
