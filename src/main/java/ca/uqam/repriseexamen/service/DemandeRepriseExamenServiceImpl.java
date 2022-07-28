package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.dto.LigneDREPersonnelDTO;
import ca.uqam.repriseexamen.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    public static final String DECISION_COURANTE_NON_COMPATIBLE = "La décision courante ne correspond pas à l'action demandée.";
    private DemandeRepriseExamenRepository demandeRepriseExamenRepository;

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenPersonnel() {
        List<LigneDREPersonnelDTO> listeLigneDRE = demandeRepriseExamenRepository
                .findLigneDREPersonnelDTOBy();

        return listeLigneDRE.stream()
                .filter(dre -> !dre.getStatutCourant().equals(TypeStatut.ENREGISTREE))
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEnseignant(long idEnseignant) {
        List<LigneDREEnseignantDTO> listeLigneDRE = demandeRepriseExamenRepository
                .findLigneDREEnseignantDTOByCoursGroupeEnseignantId(idEnseignant);

        return listeLigneDRE.stream()
                .filter(dre -> dre.getDecisionCourante() != null
                        && (dre.getDecisionCourante().getTypeDecision().equals(TypeDecision.ACCEPTEE_DIRECTEUR)
                        || dre.getDecisionCourante().getTypeDecision().equals(TypeDecision.ACCEPTEE_ENSEIGNANT)
                        || dre.getDecisionCourante().getTypeDecision().equals(TypeDecision.REJETEE_ENSEIGNANT))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long idEtudiant) {
        List<LigneDREEtudiantDTO> listeLigneDRE = demandeRepriseExamenRepository
                .findLigneDREEtudiantDTOByEtudiantId(idEtudiant);
        return new ArrayList<>(listeLigneDRE);
    }

    @Override
    public LigneDREDTO getDemandeRepriseExamenPersonnelById(long id) {
        Optional<LigneDREPersonnelDTO> demande = demandeRepriseExamenRepository.findLigneDREPersonnelDTOById(id);
        return demande.orElse(null);
    }

    @Override
    public LigneDREDTO getDemandeRepriseExamenEnseignantById(long id, long idEnseignant) {
        Optional<LigneDREEnseignantDTO> demande = demandeRepriseExamenRepository
                .findLigneDREEnseignantDTOById(id);
        if (demande.isPresent() && idEnseignant != demande.get().getIdEnseignant()) {
            throw new RuntimeException("Acces non autorisé");
        }
        return demande.orElse(null);
    }

    @Override
    public LigneDREDTO getDemandeRepriseExamenEtudiantById(long id, long idEtudiant) {
        Optional<LigneDREEtudiantDTO> demande = demandeRepriseExamenRepository
                .findLigneDREEtudiantDTOById(id);
        if (demande.isPresent() && idEtudiant != demande.get().getIdEtudiant()) {
            throw new RuntimeException("Acces non autorisé");
        }
        return demande.orElse(null);
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
    public void ajouterDemandeDecision(Long id, JsonNode patch, Set<TypeDecision> decisionsCourantes, TypeDecision typeDecisionAjoute) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (decisionsCourantes.contains(demande.getDecisionCourante().getTypeDecision())) {
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
        if (demande.getDecisionCourante().getTypeDecision() == typeDecisionCourante) {
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
        if (statutDejaPresent.isEmpty()) {
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

    @Override
    public ResponseEntity<?> envoyerMessage(Long demandeId, TypeMessage typeMessage, JsonNode json) throws Exception {
        if(json.has("message")) {
            DemandeRepriseExamen demande = findDemandeRepriseExamen(demandeId)
                    .orElseThrow(ResourceNotFoundException::new);

            // Un étudiant ne devrait pas pouvoir envoyer un message si un commis ne demande pas d'informations supplémentaire.
            if(typeMessage == TypeMessage.REPONSE_ETUDIANT && demande.getListeMessage().isEmpty())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            Message message = Message.builder()
                .typeMessage(typeMessage)
                .contenu(json.get("message").asText())
                .dateHeure(LocalDateTime.now())
                .demandeRepriseExamen(demande)
                .build();

            demande.getListeMessage().add(message);
            demandeRepriseExamenRepository.save(demande);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
