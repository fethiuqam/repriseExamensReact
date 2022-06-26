package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import java.util.List;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenCommis();
    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignat(long id);
    List<LigneDREEtudiantDTO> getAllDemandeRepriseExamenEtudiant(long id);

}
