package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.ExamRetakeRequest;
import ca.uqam.repriseexamen.service.ExamRetakeRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/demandes")
@AllArgsConstructor
public class ExamRetakeRequestRestController {

    private ExamRetakeRequestService examRetakeRequestService;

    @GetMapping("")
    public List<ExamRetakeRequest> getAllRetakeExamRequest(){
        return examRetakeRequestService.getAllExamRetakeRequest();
    }

}
