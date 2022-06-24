package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.RoleRepository;
import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    // obtenir role by id rest api
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le role n'existe pas avec cet :" + id));
        return ResponseEntity.ok(role);
    }

    // modifier role rest api
   @PutMapping("/roles/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le role n'existe pas avec cet:" + id));

        role.setNom(roleDetails.getNom());
        role.setPermissions(roleDetails.getPermissions());

        Role updatedRole = roleRepository.save(role);
        return ResponseEntity.ok(updatedRole);
    }

    // supprimer role rest api
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRole(@PathVariable Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le role n'existe pas avec cet :" + id));

        roleRepository.delete(role);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
