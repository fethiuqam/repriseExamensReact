package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.ExamRetakeRequestRepository;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamRetakeRequestServiceImplUnitTest {

    @Autowired
    private ExamRetakeRequestService service;

    @MockBean
    private ExamRetakeRequestRepository repository;

    private List<ExamRetakeRequest> errList;

    @Before
    public void setUp() {
        this.errList = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        Stream.of("Marc", "Richard", "Jean").forEach(name -> {
            Student student = Student.builder()
                    .name(name)
                    .permanentCode("ABCD12345678")
                    .email(name + "@courrier.uqam.ca")
                    .phone("1111111111")
                    .build();
            students.add(student);
        });
        students.forEach(student -> {
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
            this.errList.add(examRR);
        });
    }

    @Test
    public void shouldReturnExamRetakeRequestList() {
        when(repository.findAll()).thenReturn(this.errList);
        List<ExamRetakeRequest> result = service.getAllExamRetakeRequest();
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .allMatch(exanRR -> exanRR.getReason().equals(Reason.MEDICAL));
    }

}