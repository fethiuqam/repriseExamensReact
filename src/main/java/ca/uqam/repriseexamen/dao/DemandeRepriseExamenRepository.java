package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.*;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "demandes", path = "demandes")
public interface DemandeRepriseExamenRepository extends JpaRepository<DemandeRepriseExamen, Long> {

    List<LigneDREPersonnelDTO> findLigneDREPersonnelDTOBy();
    List<LigneDREEnseignantDTO> findLigneDREEnseignantDTOBy();
    List<LigneDREEtudiantDTO> findLigneDREEtudiantDTOBy();
    List<LigneHistoriqueEtudiantDTO> findLigneHistoriqueEtudiantDTOBy();

}
