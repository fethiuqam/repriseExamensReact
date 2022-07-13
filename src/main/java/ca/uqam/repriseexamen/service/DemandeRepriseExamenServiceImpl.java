package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDREPersonnelDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    public static final String DECISION_COURANTE_NON_COMPATIBLE = "La décision courante ne correspond pas à l'action demandée.";
    private DemandeRepriseExamenRepository demandeRepriseExamenRepository;

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenPersonnel() {
        List<LigneDREPersonnelDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDREPersonnelDTOBy();

        return listeLigneDRE.stream()
                .filter(dre -> !dre.getStatut().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long id) {
        List<LigneDREEnseignantDTO> listeLigneDRE = demandeRepriseExamenRepository
                .findLigneDREEnseignantDTOByCoursGroupeEnseignantId(id);

        return listeLigneDRE.stream()
                .filter(dre -> dre.getDecision() != null
                        && (dre.getDecision().equals(TypeDecision.ACCEPTEE_DIRECTEUR)
                        || dre.getDecision().equals(TypeDecision.ACCEPTEE_ENSEIGNANT)
                        || dre.getDecision().equals(TypeDecision.REJETEE_ENSEIGNANT))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id) {
        List<LigneDREEtudiantDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDREEtudiantDTOByEtudiantId(id);
        return new ArrayList<>(listeLigneDRE);
    }



    @Override
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre) {
        Statut statutSoumission = Statut.builder()
                .dateHeure(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .typeStatut(TypeStatut.SOUMISE)
                .demandeRepriseExamen(dre)
                .build();

        List<Justification> listeJustifications = new ArrayList<>();
        dre.setListeJustification(listeJustifications);

        ArrayList<Statut> statuts = new ArrayList<>();
        statuts.add(statutSoumission);

        dre.setListeStatut(statuts);

        return demandeRepriseExamenRepository.save(dre);
    }

    @Override
    public Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id) {
        return demandeRepriseExamenRepository.findDemandeRepriseExamenById(id);
    }

    @Override
    public void ajouterDemandeDecision(Long id, JsonNode patch, TypeDecision typeDecisionCourant, TypeDecision typeDecisionAjoute) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (demande.getDecisionCourante() == typeDecisionCourant) {
            Decision decision = creerDecision(typeDecisionAjoute, demande, patch);
            demande.getListeDecision().add(decision);
            demandeRepriseExamenRepository.save(demande);
        } else {
            throw new NotAcceptableStatusException(DECISION_COURANTE_NON_COMPATIBLE);
        }
    }

    @Override
    public void supprimerDemandeDecision(Long id, TypeDecision typeDecisionCourante) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (demande.getDecisionCourante() == typeDecisionCourante) {
            Optional<Decision> decision = demande.getListeDecision().stream()
                    .filter(s -> s.getTypeDecision() == typeDecisionCourante)
                    .findFirst();
            demande.getListeDecision().remove(decision.orElse(null));
            demandeRepriseExamenRepository.save(demande);
        } else {
            throw new NotAcceptableStatusException(DECISION_COURANTE_NON_COMPATIBLE);
        }
    }

    private Decision creerDecision(TypeDecision typeDecision, DemandeRepriseExamen demande, JsonNode patch) {
        Decision decision = Decision.builder()
                .typeDecision(typeDecision)
                .dateHeure(LocalDateTime.now())
                .demandeRepriseExamen(demande)
                .build();
        if (patch.has("details")) {
            decision.setDetails(patch.get("details").asText());
        }
        return decision;
    }

    @Override
    public void updateStatutDemande(Long id, TypeStatut typeStatut) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        Optional<Statut> statutDejaPresent = demande.getListeStatut().stream()
                .filter(s -> s.getTypeStatut() == typeStatut)
                .findFirst();
        if(statutDejaPresent.isEmpty()){
            Statut statut = Statut.builder()
                    .typeStatut(typeStatut)
                    .dateHeure(LocalDateTime.now())
                    .demandeRepriseExamen(demande)
                    .build();
            demande.getListeStatut().add(statut);
            demandeRepriseExamenRepository.save(demande);
        }
    }

    @Override
    public void annulerRejetStatut(Long id) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);

        Optional<Statut> statutRejet = demande.getListeStatut().stream()
                .filter(s -> s.getTypeStatut() == TypeStatut.REJETEE)
                .findFirst();
        demande.getListeStatut().remove(statutRejet.orElse(null));
        demandeRepriseExamenRepository.save(demande);
    }

}
