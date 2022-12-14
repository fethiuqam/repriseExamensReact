package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Decision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateHeure;
    private TypeDecision typeDecision;
    private String details;
    @JsonIgnore
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;
}
