package br.com.barbertech.controller;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.enums.GenderEnum;
import br.com.barbertech.service.BarberService;
import br.com.barbertech.service.SchedulingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BarberController.class)
public class BarberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BarberService service;

    @MockBean
    private SchedulingService schedulingService;

    @Test
    public void testGetBarbers() throws Exception {
        // Configura o mock para retornar uma lista de barbeiros
        BarberEntity barber = new BarberEntity();
        barber.setId(1L);
        barber.setName("John Doe");

        Mockito.when(service.get()).thenReturn(List.of(barber));

        mockMvc.perform(get("/barber"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testGetBarberById() throws Exception {
        // Configura o mock para retornar um barbeiro específico
        BarberEntity barber = new BarberEntity();
        barber.setId(1L);
        barber.setName("John Doe");

        Mockito.when(service.findById(1L)).thenReturn(Optional.of(barber));

        mockMvc.perform(get("/barber/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testPostBarber() throws Exception {
        // Configura o mock para salvar e retornar um barbeiro
        BarberDTO barberDTO = new BarberDTO();
        barberDTO.setName("John Doe");
        barberDTO.setEmail("johndoe@example.com");
        barberDTO.setPhone("84 12345-6789"); // Certifique-se de que o formato do telefone está correto
        barberDTO.setGender(GenderEnum.M); // Use o valor correto do GenderEnum

        BarberEntity barber = new BarberEntity();
        barber.setId(1L);
        barber.setName("John Doe");

        Mockito.when(service.save(any(BarberDTO.class))).thenReturn(barber);

        mockMvc.perform(post("/barber")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"idCompany\":1, \"email\":\"johndoe@example.com\", \"phone\":\"84 12345-6789\", \"gender\":\"M\"}"))
                .andExpect(status().isOk())  // Status esperado para sucesso
                .andExpect(jsonPath("$.id").value(1))  // Verifica o ID retornado
                .andExpect(jsonPath("$.name").value("John Doe"));  // Verifica o nome retornado
    }


    @Test
    public void testPutBarber() throws Exception {
        // Configura o mock para encontrar e atualizar um barbeiro
        BarberEntity barber = new BarberEntity();
        barber.setId(1L);
        barber.setName("John Doe");
        barber.setEmail("johndoe@example.com");
        barber.setPhone("84 12345-6789");
        barber.setGender(GenderEnum.M);

        Mockito.when(service.findById(anyLong())).thenReturn(Optional.of(barber));
        Mockito.when(service.update(any(BarberEntity.class))).thenReturn(barber);

        mockMvc.perform(put("/barber/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"idCompany\":1, \"email\":\"johndoe@example.com\", \"phone\":\"84 12345-6789\", \"gender\":\"M\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.gender").value("M"));
    }


    @Test
    public void testDeleteBarber() throws Exception {
        // Configura o mock para encontrar e deletar um barbeiro
        BarberEntity barber = new BarberEntity();
        barber.setId(1L);

        Mockito.when(service.findById(1L)).thenReturn(Optional.of(barber));
        Mockito.doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/barber/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAvailableTimes() throws Exception {
        // Configura o mock para retornar horários disponíveis
        Date date = new Date();
        List<String> times = List.of("10:00", "10:30", "11:00");

        Mockito.when(service.getAvailableTimes(anyLong(), any(Date.class))).thenReturn(times);

        mockMvc.perform(get("/barber/1/availability")
                        .param("selectedDate", "2024-11-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("10:00"))
                .andExpect(jsonPath("$[1]").value("10:30"))
                .andExpect(jsonPath("$[2]").value("11:00"));
    }
}
