package ca.uqam.repriseexamen.model;

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
    private List<DemandeRepriseExamen> demandeRepriseExamenList;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "coursGroupes")
    private List<Etudiant> etudiants;

    private String groupe;
    private Session session;
    private String annee;

}
