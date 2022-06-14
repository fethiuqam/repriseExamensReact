package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;

import java.util.List;

public interface DemandeRepriseExamenService {
    List<LigneDRECommisDTO> getAllDemandeRepriseExamen();
    List<LigneDREEtudiantDTO> getAllDemandeRepriseExamenEtudiant(long id);
}
