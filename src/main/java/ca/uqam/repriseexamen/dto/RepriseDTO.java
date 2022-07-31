package ca.uqam.repriseexamen.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class RepriseDTO {

    @NotNull
    private LocalDateTime dateHeure;

    @NotNull
    @Positive
    private int dureeMinutes;

    @NotNull
    @NotEmpty
    @NotBlank
    private String local;

    @NotNull
    @NotEmpty
    @NotBlank
    private String surveillant;

    @NotNull
    @Positive
    private long idCoursGroupe;

}
