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
public class Etudiant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String codePermanent;
    private String nom;
    private String email;
    private String telephone;
    @OneToMany(mappedBy = "detenteur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DemandeRepriseExamen> listeDemandeRepriseExamen;

}
