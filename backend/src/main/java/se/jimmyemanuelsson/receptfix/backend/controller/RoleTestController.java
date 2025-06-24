package se.jimmyemanuelsson.receptfix.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class RoleTestController {

    @GetMapping("/admin")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("Hello, admin!");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userTest() {
        return ResponseEntity.ok("Hello, user!");
    }

}
