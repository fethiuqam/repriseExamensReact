package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.Statut;
import ca.uqam.repriseexamen.model.TypeStatut;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
                .filter(dre -> dre.getStatutCourant().equals(TypeStatut.ACCEPTEE_DIRECTEUR))
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
    public void patchDemandeRepriseExamen(Long id, JsonNode patch) throws ChangeSetPersister.NotFoundException {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        String operation = patch.get("op").asText();

        switch (operation) {
            case "add" -> addPatch(demande, patch);
            case "remove" -> removePatch(demande, patch);
            default -> throw new IllegalArgumentException("Operation invalide.");
        }
    }

    private void removePatch(DemandeRepriseExamen demande, JsonNode patch) {
        String path = patch.get("path").asText();
        switch (path) {
            case "/listeStatut" -> supprimerStatut(patch, demande);
            default -> throw new IllegalArgumentException("Path invalide.");
        }
    }

    private void supprimerStatut(JsonNode patch, DemandeRepriseExamen demande) {
        String statutString = patch.get("value").get("typeStatut").asText();
        TypeStatut typeStatut = TypeStatut.valueOf(statutString);
        switch (typeStatut) {
            case REJETEE_COMMIS -> validerDecision("COMMIS", typeStatut, demande);
            case REJETEE_DIRECTEUR -> validerDecision("DIRECTEUR", typeStatut, demande);
            case REJETEE_ENSEIGNANT -> validerDecision("ENSEIGNANT", typeStatut, demande);
            default -> throw new IllegalArgumentException("Statut invalide.");
        }
        Optional<Statut> statut = demande.getListeStatut().stream()
                .filter(s -> s.getTypeStatut() == typeStatut)
                .findFirst();
        if(statut.isEmpty()){
            throw new IllegalArgumentException("Statut non existant");
        }
        demande.getListeStatut().remove(statut.get());
        demandeRepriseExamenRepository.save(demande);
    }

    private void addPatch(DemandeRepriseExamen demande, JsonNode patch) {
        String path = patch.get("path").asText();
        switch (path) {
            case "/listeStatut" -> ajouterStatut(patch, demande);
            default -> throw new IllegalArgumentException("Path invalide.");
        }
    }

    private void ajouterStatut(JsonNode patch, DemandeRepriseExamen demande) {
        String statutString = patch.get("value").get("typeStatut").asText();
        TypeStatut typeStatut = TypeStatut.valueOf(statutString);

        switch (typeStatut) {
            case ACCEPTEE_COMMIS, REJETEE_COMMIS -> validerDecision("COMMIS", typeStatut, demande);
            case ACCEPTEE_DIRECTEUR, REJETEE_DIRECTEUR -> validerDecision("DIRECTEUR", typeStatut, demande);
            case ACCEPTEE_ENSEIGNANT, REJETEE_ENSEIGNANT -> validerDecision("ENSEIGNANT", typeStatut, demande);
            default -> throw new IllegalArgumentException("Statut invalide.");
        }

        String details = patch.get("value").get("details").asText();

        Statut statut = Statut.builder()
                .typeStatut(typeStatut)
                .dateHeure(LocalDateTime.now())
                .demandeRepriseExamen(demande)
                .build();

        if (!details.isEmpty()){
            statut.setDetails(details);
        }

        demande.getListeStatut().add(statut);
        demandeRepriseExamenRepository.save(demande);
    }

    private void validerDecision(String role ,TypeStatut typeStatut, DemandeRepriseExamen demande) {
        // verifier le role
        if (demande.getListeStatut().stream().anyMatch(s -> s.getTypeStatut().toString().contains(role) )){
            throw new IllegalArgumentException("Statut incompatible avec la demande");
        }
    }

}
