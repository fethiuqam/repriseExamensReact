package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.*;
import ca.uqam.repriseexamen.service.RetakeExamRequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RetakeExamRequestRestControllerUnitTest {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @MockBean
    private RetakeExamRequestService service;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldReturnRetakeExamRequestListWithStatusOk()
            throws Exception {
        String name = "marc";
        Student student = Student.builder()
                    .name(name)
                    .permanentCode("ABCD12345678")
                    .email(name + "@courrier.uqam.ca")
                    .phone("1111111111")
                    .build();
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
        RetakeExamRequest dre = RetakeExamRequest.builder()
                .absenceStartDate(LocalDate.of(2022,2,2))
                .absenceEndDate(LocalDate.of(2022,2,10))
                .owner(student)
                .listJustificative(justificatives)
                .reason(Reason.MEDICAL)
                .statusList(statusList)
                .absenceDetails("Intervention chirurgicale programmée")
                .build();
        List<RetakeExamRequest> listDre = Arrays.asList(dre);
        when(service.getAllRetakeExamRequest()).thenReturn(listDre);
        this.mockMvc.perform(get("/api/demandes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].reason", is(dre.getReason().toString())))
                .andExpect(jsonPath("$[0].owner.name", is(dre.getOwner().getName())));
    }

    @Test
    public void shouldReturnStatusNotFound()
            throws Exception {
        this.mockMvc.perform(get("/api/demande")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}