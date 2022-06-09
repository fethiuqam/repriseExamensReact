package ca.uqam.repriseexamen.model;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Utilisateur {
    private @Id @GeneratedValue Long id;
    private String nom;
    private String prenom;
    private String codeMs;
    private String motDePasse;

    @ElementCollection(targetClass=Role.class)
    private List<Role> roles;

}
