package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Cours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "cours", path = "cours")
public interface CoursRepository extends CrudRepository<Cours, Long> {
}

