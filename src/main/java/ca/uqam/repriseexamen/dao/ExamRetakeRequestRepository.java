package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.ExamRetakeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRetakeRequestRepository extends JpaRepository<ExamRetakeRequest, Long> {
}
