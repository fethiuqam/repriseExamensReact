package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Statut {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateHeure;
    private TypeStatut typeStatut;
    @JsonBackReference
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;

}
