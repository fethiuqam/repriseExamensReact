package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.*;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepriseExamenRepository extends JpaRepository<DemandeRepriseExamen, Long> {

    List<LigneDRECommisDTO> findLigneDRECommisDTOBy();
    List<LigneDREEnseignantDTO> findLigneDREEnseignantDTOBy();
    List<LigneDREEtudiantDTO> findLigneDREEtudiantDTOBy();

}
