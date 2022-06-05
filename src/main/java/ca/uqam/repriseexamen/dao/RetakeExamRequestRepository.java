package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.RetakeExamRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetakeExamRequestRepository extends JpaRepository<RetakeExamRequest, Long> {
}
