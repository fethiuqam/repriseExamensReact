package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.dto.LigneDREPersonnelDTO;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
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

    List<LigneDREPersonnelDTO> findLigneDREPersonnelDTOBy();

    List<LigneDREEnseignantDTO> findLigneDREEnseignantDTOByCoursGroupeEnseignantId(Long id);

    List<LigneDREEtudiantDTO> findLigneDREEtudiantDTOByEtudiantId(Long id);

    List<LigneHistoriqueEtudiantDTO> findLigneHistoriqueEtudiantDTOByEtudiantId(Long id);

    Optional<LigneDREPersonnelDTO> findLigneDREPersonnelDTOById(long id);

    Optional<LigneDREEnseignantDTO> findLigneDREEnseignantDTOById(long id);

    Optional<LigneDREEtudiantDTO> findLigneDREEtudiantDTOById(long id);

}
