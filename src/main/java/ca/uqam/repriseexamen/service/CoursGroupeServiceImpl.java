package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.CoursGroupeRepository;
import ca.uqam.repriseexamen.dto.CoursGroupeDTO;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CoursGroupeServiceImpl implements CoursGroupeService {

    CoursGroupeRepository coursGroupeRepository;

    @Override
    public List<CoursGroupeDTO> getAllCoursGroupesNonPlanifies() {

        return coursGroupeRepository.findCoursGroupeDTOBy().stream()
                .filter(crGrp ->
                        crGrp.getDemandes().stream()
                                .anyMatch(dre -> dre.getStatutCourant().equals(TypeStatut.ACCEPTEE)
                                        || dre.getStatutCourant().equals(TypeStatut.PLANIFIEE)))
                .collect(Collectors.toList());
    }

}
