package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Justification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface JustificationRepository extends JpaRepository<Justification, Long> {
}