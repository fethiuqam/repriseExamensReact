package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeDecision;
import ca.uqam.repriseexamen.model.TypeMessage;
import ca.uqam.repriseexamen.model.TypeStatut;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenPersonnel();

    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long idEnseignant);

    List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long idEtudiant);

    LigneDREDTO getDemandeRepriseExamenPersonnelById(long id);

    LigneDREDTO getDemandeRepriseExamenEnseignantById(long id, long idEnseignant);

    LigneDREDTO getDemandeRepriseExamenEtudiantById(long id, long idEtudiant);

    DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre);

    Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id);

    void ajouterDemandeDecision(Long id, JsonNode patch, Set<TypeDecision> decisionsCourantes, TypeDecision typeDecisionAjoute);

    void supprimerDemandeDecision(Long id, TypeDecision typeDecisionCourante);

    void updateStatutDemande(Long id, TypeStatut typeStatut);

    void annulerRejetStatut(Long id);

    ResponseEntity<?> envoyerMessage(Long demandeId, TypeMessage type, JsonNode json) throws Exception;
}
