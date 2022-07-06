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
public class Cours {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sigle;
    private String nom;
    @OneToMany (mappedBy = "cours")
    @JsonManagedReference(value = "cours-coursGroupe")
    private List<CoursGroupe> coursGroupeList;

}
