package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.dto.RoleDTO;
import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = RoleDTO.class)
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNomEquals(String nom);
}
