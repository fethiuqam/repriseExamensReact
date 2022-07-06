package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.Statut;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
        Statut statutSoumission = Statut.builder()
                .dateHeure(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .typeStatut(TypeStatut.SOUMISE)
                .demandeRepriseExamen(dre)
                .build();

        ArrayList<Statut> statuts = new ArrayList<>();
        statuts.add(statutSoumission);

        dre.setListeStatut(statuts);
        dre.setDateSoumission(LocalDate.now());

        return demandeRepriseExamenRepository.save(dre);
    }
}
