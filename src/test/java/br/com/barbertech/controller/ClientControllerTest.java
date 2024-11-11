package br.com.barbertech.controller;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.enums.GenderEnum;
import br.com.barbertech.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @InjectMocks
    private ClientController controller;

    private ClientDTO clientDTO;
    private ClientEntity clientEntity;

    @BeforeEach
    public void setUp() {
        clientDTO = new ClientDTO();
        clientDTO.setName("John Doe");
        clientDTO.setEmail("johndoe@example.com");
        clientDTO.setPhone("84 12345-6789");
        clientDTO.setGender(GenderEnum.M);

        clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setName("John Doe");
        clientEntity.setEmail("johndoe@example.com");
        clientEntity.setPhone("84 12345-6789");
        clientEntity.setGender(GenderEnum.M);
    }

    @Test
    public void testGetAllClients() throws Exception {
        when(service.get()).thenReturn(List.of(clientEntity));

        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testGetClientById() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.of(clientEntity));

        mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetClientByIdNotFound() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/client/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostClient() throws Exception {
        when(service.save(any(ClientDTO.class))).thenReturn(clientEntity);

        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\", \"email\":\"johndoe@example.com\", \"phone\":\"84 12345-6789\", \"gender\":\"M\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testPutClient() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(service.update(any(ClientEntity.class))).thenReturn(clientEntity);

        mockMvc.perform(put("/client/1")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe Updated\", \"email\":\"johndoeupdated@example.com\", \"phone\":\"84 98765-4321\", \"gender\":\"M\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    public void testPutClientNotFound() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/client/999")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe Updated\", \"email\":\"johndoeupdated@example.com\", \"phone\":\"84 98765-4321\", \"gender\":\"M\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteClient() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.of(clientEntity));

        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteClientNotFound() throws Exception {
        when(service.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/client/999"))
                .andExpect(status().isNotFound());
    }
}
