package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.RetakeExamRequestRepository;
import ca.uqam.repriseexamen.model.RetakeExamRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RetakeExamRequestServiceImpl implements RetakeExamRequestService{

    private RetakeExamRequestRepository retakeExamRequestRepository;
    @Override
    public List<RetakeExamRequest> getAllRetakeExamRequest() {
        return retakeExamRequestRepository.findAll();
    }
}
