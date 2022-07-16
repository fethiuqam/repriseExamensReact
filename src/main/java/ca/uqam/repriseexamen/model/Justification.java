package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Justification {

    // Attributs

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference(value = "listeJustification")
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;

    private String nomFichier;
    private String description;
    private String url;
}
