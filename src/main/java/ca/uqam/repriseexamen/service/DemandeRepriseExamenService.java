package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import java.util.List;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenCommis();
    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id);
    List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id);

}
