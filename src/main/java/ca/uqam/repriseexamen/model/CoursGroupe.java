package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursGroupe {

    // Attributs

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cours cours;

    @ManyToOne
    private Enseignant enseignant;

    @JsonIgnore
    @OneToMany(mappedBy = "coursGroupe")
    private List<DemandeRepriseExamen> demandes;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "coursGroupes")
    private List<Etudiant> etudiants;

    @JsonManagedReference
    @OneToOne
    Reprise reprise;

    private String groupe;
    private Session session;
    private String annee;


}
