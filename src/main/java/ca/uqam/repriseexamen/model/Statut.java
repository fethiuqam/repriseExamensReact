package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Statut {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateHeure;
    private TypeStatut typeStatut;
    private String details;
    @ManyToOne
    @JsonBackReference(value="demande-statut")
    private DemandeRepriseExamen demandeRepriseExamen;

}
