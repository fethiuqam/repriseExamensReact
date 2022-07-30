package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.CoursGroupeDTO;
import ca.uqam.repriseexamen.model.CoursGroupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursGroupeRepository extends JpaRepository<CoursGroupe, Long> {

    List<CoursGroupeDTO> findCoursGroupeDTOBy();


}
