package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Statut {

    // Attributs

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference(value =  "listeStatut")
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private DemandeRepriseExamen demandeRepriseExamen;

    private LocalDateTime dateHeure;
    private TypeStatut typeStatut;
}
