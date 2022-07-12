package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;

import java.util.List;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenPersonnel();
    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id);
    List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id);
    List<LigneHistoriqueEtudiantDTO> getHistoriqueEtudiant(long id);

    DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre);

}
