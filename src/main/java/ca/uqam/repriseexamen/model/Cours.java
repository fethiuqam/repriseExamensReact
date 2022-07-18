package ca.uqam.repriseexamen.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sigle;
    private String nom;
    @OneToMany(mappedBy = "cours")
    @ToString.Exclude
    private List<CoursGroupe> coursGroupeList;

}
