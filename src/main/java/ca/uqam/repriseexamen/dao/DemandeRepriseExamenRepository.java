package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.*;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepriseExamenRepository extends JpaRepository<DemandeRepriseExamen, Long> {

    List<LigneDRECommisDTO> findLigneDRECommisDTOBy();
    List<LigneDREEnseignantDTO> findLigneDREEnseignantDTOBy();
    List<LigneDREEtudiantDTO> findLigneDREEtudiantDTOBy();

}
