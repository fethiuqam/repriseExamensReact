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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class DemandeRepriseExamenServiceImpl implements DemandeRepriseExamenService {

    public static final String STATUT_COURANT_NON_COMPATIBLE = "Le statut courant ne correspond pas à l'action demandée.";
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
        List<LigneDREEnseignantDTO> listeLigneDRE = demandeRepriseExamenRepository
                .findLigneDREEnseignantDTOByCoursGroupeEnseignantId(id);

        return listeLigneDRE.stream()
                .filter(dre -> dre.getStatutCourant().equals(TypeStatut.ACCEPTEE_DIRECTEUR)
                        || dre.getStatutCourant().equals(TypeStatut.ACCEPTEE_ENSEIGNANT)
                        || dre.getStatutCourant().equals(TypeStatut.REJETEE_ENSEIGNANT)
                        || dre.getStatutCourant().equals(TypeStatut.PLANIFIEE)
                        || dre.getStatutCourant().equals(TypeStatut.COMPLETEE)
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneDREDTO> getAllDemandeRepriseExamenEtudiant(long id) {
        List<LigneDREEtudiantDTO> listeLigneDRE = demandeRepriseExamenRepository.findLigneDREEtudiantDTOByEtudiantId(id);
        return listeLigneDRE.stream().collect(Collectors.toList());
    }

    @Override
    public DemandeRepriseExamen soumettreDemandeRepriseExamen(DemandeRepriseExamen dre) {
        Statut statutSoumission = Statut.builder()
                .dateHeure(LocalDateTime.now())
                .typeStatut(TypeStatut.SOUMISE)
                .demandeRepriseExamen(dre)
                .build();

        List<Statut> statuts = new ArrayList<>();
        statuts.add(statutSoumission);
        dre.setListeStatut(statuts);

        return demandeRepriseExamenRepository.save(dre);
    }

    @Override
    public Optional<DemandeRepriseExamen> findDemandeRepriseExamen(Long id) {
        return demandeRepriseExamenRepository.findDemandeRepriseExamenById(id);
    }

    @Override
    public void ajouterStatutADemande(Long id, JsonNode patch, TypeStatut typeStatutCourant, TypeStatut typeStatutAjoute) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (demande.getStatutCourant() == typeStatutCourant) {
            Statut statut = creerStatut(typeStatutAjoute, demande, patch);
            demande.getListeStatut().add(statut);
            demandeRepriseExamenRepository.save(demande);
        } else {
            throw new IllegalTransactionStateException(STATUT_COURANT_NON_COMPATIBLE);
        }
    }

    @Override
    public void supprimerStatutDeDemande(Long id, TypeStatut typeStatutCourant) {
        DemandeRepriseExamen demande = findDemandeRepriseExamen(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (demande.getStatutCourant() == typeStatutCourant) {
            Optional<Statut> statut = demande.getListeStatut().stream()
                    .filter(s -> s.getTypeStatut() == typeStatutCourant)
                    .findFirst();
            demande.getListeStatut().remove(statut.get());
            demandeRepriseExamenRepository.save(demande);
        } else {
            throw new IllegalTransactionStateException(STATUT_COURANT_NON_COMPATIBLE);
        }
    }

    private Statut creerStatut(TypeStatut typeStatut, DemandeRepriseExamen demande, JsonNode patch) {
        Statut statut = Statut.builder()
                .typeStatut(typeStatut)
                .dateHeure(LocalDateTime.now())
                .demandeRepriseExamen(demande)
                .build();
        if (patch.has("details")) {
            statut.setDetails(patch.get("details").asText());
        }
        return statut;
    }

}
