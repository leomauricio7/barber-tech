package br.com.barbertech.service;

import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.repository.UserRepository;
import br.com.barbertech.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.barbertech.utils.PasswordUtil.validatePassword;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> login(String username, String password) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Valida a senha
            if (validatePassword(password, user.getPassword())) {
                return Optional.of(user);  // Login bem-sucedido
            }
        }
        return Optional.empty();  // Falha no login
    }

    public boolean resetPassword(String username, String newPassword) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setPassword(PasswordUtil.hashPassword(newPassword));  // Atualiza a senha
            userRepository.save(user.get());
            return true;  // Senha atualizada
        }
        return false;  // Usuário não encontrado
    }
}
