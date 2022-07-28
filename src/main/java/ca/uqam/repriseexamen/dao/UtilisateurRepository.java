package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByCodeMsEquals(String codeMs);
}
