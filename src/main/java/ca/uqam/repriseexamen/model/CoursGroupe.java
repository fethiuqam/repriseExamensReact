package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CoursGroupe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupe;
    private Session session;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cours cours;
    @ManyToOne
    private Enseignant enseignant;
    @OneToMany(mappedBy = "coursGroupe")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DemandeRepriseExamen> demandeRepriseExamenList;

}
