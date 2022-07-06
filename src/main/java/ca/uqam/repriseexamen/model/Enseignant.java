package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Enseignant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "enseignant-coursGroupe")
    private List<CoursGroupe> coursGroupeList;

}