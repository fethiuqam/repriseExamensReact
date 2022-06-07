package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.ExamRetakeRequestRepository;
import ca.uqam.repriseexamen.model.ExamRetakeRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ExamRetakeRequestServiceImpl implements ExamRetakeRequestService {

    private ExamRetakeRequestRepository examRetakeRequestRepository;
    @Override
    public List<ExamRetakeRequest> getAllExamRetakeRequest() {
        return examRetakeRequestRepository.findAll();
    }
}
