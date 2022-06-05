package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RetakeExamRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate absenceStartDate;
    private LocalDate absenceEndDate;
    private Reason reason;
    private String absenceDetails;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Status> statusList;
    @ManyToOne
    private Student owner;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Justificative> listJustificative;




}
