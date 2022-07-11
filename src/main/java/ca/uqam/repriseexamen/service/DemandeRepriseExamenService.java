package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeDecision;
import ca.uqam.repriseexamen.model.TypeStatut;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenCommis();
    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id);
    List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id);

    DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre);

    Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id);

    void ajouterDemandeDecision(Long id, JsonNode patch, TypeDecision typeDecisionCourant, TypeDecision typeDecisionAjoute);

    void supprimerDemandeDecision(Long id, TypeDecision typeDecisionCourante);

    void updateStatutDemande(Long id, TypeStatut typeStatut);

    void annulerRejetStatut(Long id);
}
