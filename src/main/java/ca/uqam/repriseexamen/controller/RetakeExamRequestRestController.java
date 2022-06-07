package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.RetakeExamRequest;
import ca.uqam.repriseexamen.service.RetakeExamRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/demandes")
@AllArgsConstructor
public class RetakeExamRequestRestController {

    private RetakeExamRequestService retakeExamRequestService;

    @GetMapping("")
    public List<RetakeExamRequest> getAllRetakeExamRequest(){
        return retakeExamRequestService.getAllRetakeExamRequest();
    }

}
