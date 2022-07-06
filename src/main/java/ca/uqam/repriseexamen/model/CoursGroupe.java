package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CoursGroupe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupe;
    private Session session;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "cours-coursGroupe")
    private Cours cours;
    @ManyToOne
    @JsonBackReference(value = "enseignant-coursGroupe")
    private Enseignant enseignant;
    @OneToMany(mappedBy = "coursGroupe")
    @JsonManagedReference(value = "coursGroupe-demande")
    private List<DemandeRepriseExamen> demandeRepriseExamenList;

}
