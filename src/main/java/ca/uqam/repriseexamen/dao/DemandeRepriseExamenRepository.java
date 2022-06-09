package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepriseExamenRepository extends JpaRepository<DemandeRepriseExamen, Long> {
}
