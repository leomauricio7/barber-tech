package br.com.barbertech.controller;

import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestParam String username, @RequestParam String password) {
        Optional<UserEntity> userOptional = authService.login(username, password);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            // Retorna o usuário autenticado sem a senha
            user.setPassword(null); // Remove a senha antes de retornar
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body(null);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
        if (authService.resetPassword(username, newPassword)) {
            return ResponseEntity.ok("Senha atualizada com sucesso");
        }
        return ResponseEntity.status(404).body("Usuário não encontrado");
    }
}
