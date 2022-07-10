package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.*;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "demandes", path = "demandes")
public interface DemandeRepriseExamenRepository extends JpaRepository<DemandeRepriseExamen, Long> {

    Optional<DemandeRepriseExamen> findDemandeRepriseExamenById(Long id);

    List<LigneDRECommisDTO> findLigneDRECommisDTOBy();
    List<LigneDREEnseignantDTO> findLigneDREEnseignantDTOByCoursGroupeEnseignantId( Long id);
    List<LigneDREEtudiantDTO> findLigneDREEtudiantDTOByEtudiantId(Long id);

}
