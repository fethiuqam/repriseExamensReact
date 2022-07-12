package ca.uqam.repriseexamen.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursGroupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupe;
    private Session session;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cours cours;
    @ManyToOne
    private Enseignant enseignant;
    @OneToMany(mappedBy = "coursGroupe")
    private List<DemandeRepriseExamen> demandeRepriseExamenList;

}
