package ca.uqam.repriseexamen.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ca.uqam.repriseexamen.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private @Id @GeneratedValue Long id;
    private String name;

    @ElementCollection(targetClass=Permission.class)
    private List<Permission> permissions;
}
