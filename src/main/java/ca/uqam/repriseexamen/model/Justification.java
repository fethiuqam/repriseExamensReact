package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Justification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomFichier;
    private String description;
    private String url;
    @JsonIgnore
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;

}
