package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.UtilisateurDTO;
import ca.uqam.repriseexamen.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = UtilisateurDTO.class)
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByCodeMsEquals(String codeMs);
}
