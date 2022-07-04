package ca.uqam.repriseexamen.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cours {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sigle;
    private String nom;
    @OneToMany (mappedBy = "cours")
    private List<CoursGroupe> coursGroupeList;

}
