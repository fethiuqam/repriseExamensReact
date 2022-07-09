package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DemandeRepriseExamenService {

    List<LigneDREDTO> getAllDemandeRepriseExamenCommis();
    List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id);
    List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id);

    DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre);

    Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id);

    void patchDemandeRepriseExamen(Long id, JsonNode patch) throws Exception;
}
