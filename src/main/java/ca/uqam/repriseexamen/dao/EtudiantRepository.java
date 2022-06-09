package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
