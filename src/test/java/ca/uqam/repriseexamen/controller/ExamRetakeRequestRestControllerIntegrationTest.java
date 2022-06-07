package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.ExamRetakeRequestRepository;
import ca.uqam.repriseexamen.dao.StudentRepository;
import ca.uqam.repriseexamen.model.*;
import ca.uqam.repriseexamen.service.ExamRetakeRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExamRetakeRequestRestControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRetakeRequestRepository examRetakeRequestRepository;

    @Autowired
    private ExamRetakeRequestService service;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        Stream.of("Marc", "Richard", "Jean").forEach(name -> {
            Student student = Student.builder()
                    .name(name)
                    .permanentCode("ABCD12345678")
                    .email(name + "@courrier.uqam.ca")
                    .phone("1111111111")
                    .build();
            studentRepository.save(student);
        });
        studentRepository.findAll().forEach(student -> {
            Justificative justificative = Justificative.builder()
                    .description("justificatif 1")
                    .url("/files/file1")
                    .build();
            List<Justificative> justificatives = Arrays.asList(justificative);
            Status status = Status.builder()
                    .typeStatus(TypeStatus.SUBMITTED)
                    .dateTime(LocalDateTime.now())
                    .build();
            List<Status> statusList = Arrays.asList(status);
            ExamRetakeRequest examRR = ExamRetakeRequest.builder()
                    .absenceStartDate(LocalDate.of(2022, 2, 2))
                    .absenceEndDate(LocalDate.of(2022, 2, 10))
                    .owner(student)
                    .listJustificative(justificatives)
                    .reason(Reason.MEDICAL)
                    .statusList(statusList)
                    .absenceDetails("Intervention chirurgicale programmée")
                    .build();
            examRetakeRequestRepository.save(examRR);
        });
    }

    @Test
    public void shouldReturnRetakeExamRequestListWithStatusOk()
            throws Exception {

        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[0].reason", is("MEDICAL")))
                .andExpect(jsonPath("$[0].owner.name", is("Marc")))
                .andExpect(jsonPath("$[1].owner.name", is("Richard")))
                .andExpect(jsonPath("$[2].owner.name", is("Jean")));
    }

    @Test
    public void shouldReturnStatusNotFound()
            throws Exception {

        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}