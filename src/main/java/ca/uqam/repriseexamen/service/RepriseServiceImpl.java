package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.CoursGroupeRepository;
import ca.uqam.repriseexamen.dao.RepriseRepository;
import ca.uqam.repriseexamen.dto.RepriseDTO;
import ca.uqam.repriseexamen.model.CoursGroupe;
import ca.uqam.repriseexamen.model.Reprise;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RepriseServiceImpl implements RepriseService {

    private CoursGroupeRepository coursGroupeRepository;
    private RepriseRepository repriseRepository;
    private DemandeRepriseExamenService demandeService;

    @Override
    public Reprise planifierReprise(RepriseDTO repriseDTO) {

        Optional<CoursGroupe> optionalCoursGroupe = coursGroupeRepository.findById(repriseDTO.getIdCoursGroupe());
        if (optionalCoursGroupe.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        CoursGroupe coursGroupe = optionalCoursGroupe.get();

        Reprise reprise = Reprise.builder()
                .dateHeure(repriseDTO.getDateHeure())
                .dureeMinutes(repriseDTO.getDureeMinutes())
                .local(repriseDTO.getLocal())
                .surveillant(repriseDTO.getSurveillant())
                .coursGroupe(coursGroupe)
                .build();
        Reprise repriseCree = repriseRepository.save(reprise);

        coursGroupe.setReprise(repriseCree);
        coursGroupeRepository.save(coursGroupe);

        // mise a jour du statut des demandes concernees en PLANIFIEE
        coursGroupe.getDemandes().stream()
                .filter(dre -> dre.getStatutCourant().equals(TypeStatut.ACCEPTEE))
                .forEach(dre -> demandeService.updateStatutDemande(dre.getId(), TypeStatut.PLANIFIEE));

        return repriseCree;
    }

    @Override
    public Reprise modifierPlanification(RepriseDTO repriseDTO, Long id) {
        Optional<Reprise> optionalReprise = repriseRepository.findById(id);
        if(optionalReprise.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Reprise reprise = optionalReprise.get();
        reprise.setDateHeure(repriseDTO.getDateHeure());
        reprise.setDureeMinutes(repriseDTO.getDureeMinutes());
        reprise.setLocal(repriseDTO.getLocal());
        reprise.setSurveillant(repriseDTO.getSurveillant());
        return repriseRepository.save(reprise);
    }

    @Override
    public void annulerPlanification(Long id) {
        Optional<Reprise> optionalReprise = repriseRepository.findById(id);
        if(optionalReprise.isEmpty()){
            throw new ResourceNotFoundException();
        }
        CoursGroupe coursGroupe = optionalReprise.get().getCoursGroupe();
        coursGroupe.setReprise(null);

        // suppression du statut PLANIFIEE pour les demandes concernees
        coursGroupe.getDemandes().stream()
                .filter(dre -> dre.getStatutCourant().equals(TypeStatut.PLANIFIEE))
                .forEach(dre -> demandeService.deleteStatutDemande(dre.getId(), TypeStatut.PLANIFIEE));

        repriseRepository.deleteById(id);
    }

}
