package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByNomEquals(String nom);
}
