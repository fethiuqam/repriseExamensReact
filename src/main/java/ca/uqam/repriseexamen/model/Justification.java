package ca.uqam.repriseexamen.model;

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
    private String description;
    private String url;

}
