package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;

import java.util.List;

public interface DemandeRepriseExamenService {
    List<LigneDRECommisDTO> getAllDemandeRepriseExamen();
}
