package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TypeMessage typeMessage;

    private String contenu;

    private LocalDateTime dateHeure;

    @JsonBackReference
    @ManyToOne
    private DemandeRepriseExamen demandeRepriseExamen;
}
