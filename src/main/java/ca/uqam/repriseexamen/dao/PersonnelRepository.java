package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Personnel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelRepository extends CrudRepository<Personnel,Long>{
}
