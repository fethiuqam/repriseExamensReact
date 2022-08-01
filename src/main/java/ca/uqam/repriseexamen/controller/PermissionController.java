package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PermissionController {

    @GetMapping("api/permissions")
    public ResponseEntity<Permission[]> getPermissions(){
        return new ResponseEntity<>(Permission.values(), HttpStatus.OK);
    }

}
