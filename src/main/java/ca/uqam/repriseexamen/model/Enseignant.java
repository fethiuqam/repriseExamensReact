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
public class Enseignant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    @OneToMany(mappedBy = "enseignant")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CoursGroupe> coursGroupeList;

}