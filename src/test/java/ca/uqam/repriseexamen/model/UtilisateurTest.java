package ca.uqam.repriseexamen.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilisateurTest {

    @Test
    void getPermissionsRetourneEnsembleVideSiPasDePermissions() {
        Set<Role> roles = Set.of(Role.builder().permissions(Collections.emptyList()).build());
        Personnel personnel = Personnel.builder().roles(roles).build();

        assertEquals(Collections.emptySet(), personnel.getPermissions());
    }

    @Test
    void getPermissionsRetourneLesPermissionsDuRoleSiUnSeulRole() {
        Set<Permission> permissions = Set.of(Permission.AfficherDRE, Permission.GererRoles);

        Set<Role> roles = Set.of(Role.builder().permissions(permissions.stream().toList()).build());
        Personnel personnel = Personnel.builder().roles(roles).build();

        assertEquals(permissions, personnel.getPermissions());
    }

    @Test
    void getPermissionsRetourneUnionDesPermissionsSiPlusieursRoles() {
        Set<Permission> permissions = Set.of(Permission.AfficherDRE, Permission.JugerCommis, Permission.GererRoles);

        Role role1 = Role.builder().permissions(List.of(Permission.AfficherDRE, Permission.GererRoles)).build();
        Role role2 = Role.builder().permissions(List.of(Permission.GererRoles, Permission.JugerCommis)).build();
        Personnel personnel = Personnel.builder().roles(Set.of(role1, role2)).build();

        assertEquals(permissions, personnel.getPermissions());
    }
}
