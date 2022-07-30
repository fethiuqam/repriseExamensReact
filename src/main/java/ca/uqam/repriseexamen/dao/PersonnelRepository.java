package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Personnel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "utilisateurs")
public interface PersonnelRepository extends CrudRepository<Personnel, Long> {
}
