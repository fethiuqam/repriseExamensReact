package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "utilisateurs")
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
