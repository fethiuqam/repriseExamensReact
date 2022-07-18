package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TypeMessage typeMessage;

    private String contenu;

    private LocalDateTime dateHeure;

    @JsonIgnore
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;
}
