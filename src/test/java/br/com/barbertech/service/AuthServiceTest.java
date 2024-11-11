package br.com.barbertech.service;


import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.repository.UserRepository;
import br.com.barbertech.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mockando um usuário para os testes
        user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword(PasswordUtil.hashPassword("password"));
    }

    @Test
    void login_Successful() {
        // Configura o repositório para retornar o usuário mockado
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Chama o método de login
        Optional<UserEntity> result = authService.login("testuser", "password");

        // Verifica se o login foi bem-sucedido
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void login_Failure_InvalidPassword() {
        // Configura o repositório para retornar o usuário mockado
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Tenta fazer login com uma senha incorreta
        Optional<UserEntity> result = authService.login("testuser", "wrongpassword");

        // Verifica se o login falhou
        assertFalse(result.isPresent());
    }

    @Test
    void login_Failure_UserNotFound() {
        // Configura o repositório para retornar um usuário vazio (não encontrado)
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Tenta fazer login com um nome de usuário inexistente
        Optional<UserEntity> result = authService.login("nonexistent", "password");

        // Verifica se o login falhou
        assertFalse(result.isPresent());
    }

    @Test
    void resetPassword_Successful() {
        // Configura o repositório para retornar o usuário mockado
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        // Chama o método de redefinição de senha
        boolean result = authService.resetPassword("testuser", "newpassword");

        // Verifica se a senha foi redefinida com sucesso
        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void resetPassword_Failure_UserNotFound() {
        // Configura o repositório para retornar um usuário vazio (não encontrado)
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Tenta redefinir a senha de um usuário inexistente
        boolean result = authService.resetPassword("nonexistent", "newpassword");

        // Verifica se a redefinição de senha falhou
        assertFalse(result);
        verify(userRepository, never()).save(any());
    }
}
