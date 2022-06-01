package ca.uqam.repriseexamen.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Bonjour, les demandes de reprises d'examens!";
    }
}
