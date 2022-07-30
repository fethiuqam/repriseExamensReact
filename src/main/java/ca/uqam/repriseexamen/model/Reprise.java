package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateHeure;
    private int dureeMinutes;
    private String local;
    private String surveillant;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.REFRESH)
    private CoursGroupe coursGroupe;

}
