package ca.uqam.repriseexamen.model;


import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personnel extends Utilisateur {
    private int employeId;
}
