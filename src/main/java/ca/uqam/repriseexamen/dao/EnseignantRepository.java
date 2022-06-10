package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}
