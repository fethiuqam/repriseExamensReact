package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Etudiant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String codePermanent;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @OneToMany(mappedBy = "etudiant")
    private List<DemandeRepriseExamen> demandes;

}
