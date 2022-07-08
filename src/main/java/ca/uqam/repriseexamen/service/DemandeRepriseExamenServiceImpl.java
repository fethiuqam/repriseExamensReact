package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.Statut;
import ca.uqam.repriseexamen.model.TypeStatut;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    private DemandeRepriseExamenRepository demandeRepriseExamenRepository;

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenCommis() {
        List<LigneDRECommisDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDRECommisDTOBy();

        return listeLigneDRE.stream()
                .filter(dre -> !dre.getStatutCourant().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id) {
        List<LigneDREEnseignantDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDREEnseignantDTOBy();

        return listeLigneDRE.stream()
                .filter(dre -> dre.getEnseignantId() == id)
                .filter(dre -> dre.getStatutCourant().equals(TypeStatut.ACCEPTEE))
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id) {
        List<LigneDREEtudiantDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDREEtudiantDTOBy();

        return listeLigneDRE.stream()
                .filter(dre -> dre.getEtudiantId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre) {
        Statut statutSoumission = Statut.builder().dateHeure(LocalDateTime.now()).typeStatut(TypeStatut.SOUMISE).build();

        ArrayList<Statut> statuts = new ArrayList<>();
        statuts.add(statutSoumission);

        dre.setListeStatut(statuts);
        dre.setDateSoumission(LocalDate.now());

        return demandeRepriseExamenRepository.save(dre);
    }

    @Override
    public Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id) {
        return demandeRepriseExamenRepository.findDemandeRepriseExamenById(id);
    }

    @Override
    public void patchDemandeRepriseExamen(Long id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(RuntimeException::new);
        DemandeRepriseExamen demandePatched = applyPatchToDemande(patch, demande);
        demandeRepriseExamenRepository.save(demandePatched);
    }

    private DemandeRepriseExamen applyPatchToDemande(JsonPatch patch, DemandeRepriseExamen targetDemande)
            throws JsonPatchException, JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        JsonNode patched = patch.apply(mapper.convertValue(targetDemande, JsonNode.class));
        return mapper.treeToValue(patched, DemandeRepriseExamen.class);
    }

}
