package br.com.barbertech.controller;

import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    public void testLoginSuccess() throws Exception {
        // Mocka um usuário para o teste
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("hashedPassword");

        // Configura o mock para retornar um usuário válido
        when(authService.login("testUser", "password")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/auth/login")
                        .param("username", "testUser")
                        .param("password", "password")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void testLoginFailure() throws Exception {
        // Configura o mock para retornar vazio, simulando falha de autenticação
        when(authService.login("testUser", "wrongPassword")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                        .param("username", "testUser")
                        .param("password", "wrongPassword")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        // Configura o mock para simular uma redefinição de senha bem-sucedida
        when(authService.resetPassword("testUser", "newPassword")).thenReturn(true);

        mockMvc.perform(post("/auth/reset-password")
                        .param("username", "testUser")
                        .param("newPassword", "newPassword")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Senha atualizada com sucesso"));
    }

    @Test
    public void testResetPasswordUserNotFound() throws Exception {
        // Configura o mock para simular que o usuário não foi encontrado
        when(authService.resetPassword("testUser", "newPassword")).thenReturn(false);

        mockMvc.perform(post("/auth/reset-password")
                        .param("username", "testUser")
                        .param("newPassword", "newPassword")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Usuário não encontrado"));
    }
}
