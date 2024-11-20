package br.com.barbertech.controller;

import br.com.barbertech.dto.ServiceDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService service;

    @Test
    public void testGetAllServices() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CompanyEntity company = CompanyEntity.builder()
                .id(1L)
                .name("Teste")
                .email("teste@empresa.com")
                .phone("123456789")
                .openingHours("08:00-17:00")
                .barbers(Collections.emptyList())
                .scheduling(Collections.emptyList())
                .services(Collections.emptyList())
                .user(user)
                .build();

        ServiceEntity service1 = new ServiceEntity(1L, "Service 1", BigDecimal.valueOf(100),"Description 1", company);
        ServiceEntity service2 = new ServiceEntity(2L, "Service 2", BigDecimal.valueOf(500),"Description 1", company);


        when(service.get()).thenReturn(Arrays.asList(service1, service2));

        mockMvc.perform(get("/service"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Service 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Service 2"));
    }

    @Test
    public void testGetServiceById() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CompanyEntity company = CompanyEntity.builder()
                .id(1L)
                .name("Teste")
                .email("teste@empresa.com")
                .phone("123456789")
                .openingHours("08:00-17:00")
                .barbers(Collections.emptyList())
                .scheduling(Collections.emptyList())
                .services(Collections.emptyList())
                .user(user)
                .build();

        ServiceEntity service1 = new ServiceEntity(1L, "Service 1", BigDecimal.valueOf(100),"Description 1", company);
        when(service.findById(1L)).thenReturn(Optional.of(service1));

        mockMvc.perform(get("/service/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Service 1"));
    }

    @Test
    public void testPostService() throws Exception {

        ServiceDTO serviceDTO = new ServiceDTO(1L, "Service", BigDecimal.valueOf(100),"Description", 1L);
        ServiceDTO savedServiceDTO = new ServiceDTO(1L, "Service", BigDecimal.valueOf(100),"Description", 1L);


        when(service.save(any(ServiceDTO.class))).thenReturn(savedServiceDTO);

        mockMvc.perform(post("/service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Service\",\"description\":\"Updated Description\",\"price\":100.0,\"idCompany\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Service"));
    }

    @Test
    public void testPutService() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        CompanyEntity company = CompanyEntity.builder()
                .id(1L)
                .name("Teste")
                .email("teste@empresa.com")
                .phone("123456789")
                .openingHours("08:00-17:00")
                .barbers(Collections.emptyList())
                .scheduling(Collections.emptyList())
                .services(Collections.emptyList())
                .user(user)
                .build();

        ServiceEntity existingService = new ServiceEntity(1L, "Service", BigDecimal.valueOf(100),"Description", company);
        ServiceDTO updatedServiceDTO = new ServiceDTO(1L, "Updated Service", BigDecimal.valueOf(200),"Updated Description", 1L);

        when(service.findById(1L)).thenReturn(Optional.of(existingService));
        when(service.update(any(ServiceEntity.class))).thenReturn(updatedServiceDTO);

        mockMvc.perform(put("/service/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Service\",\"description\":\"Updated Description\",\"price\":200.0,\"idCompany\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Service"));

    }

    @Test
    public void testDeleteService() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/service/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);
    }

    @Test
    public void testFindServicesByCompanyId() throws Exception {

        UserEntity user = new UserEntity();
        user.setId(1L);

        CompanyEntity company = CompanyEntity.builder()
                .id(1L)
                .name("Teste")
                .email("teste@empresa.com")
                .phone("123456789")
                .openingHours("08:00-17:00")
                .barbers(Collections.emptyList())
                .scheduling(Collections.emptyList())
                .services(Collections.emptyList())
                .user(user)
                .build();

        ServiceEntity service1 = new ServiceEntity(1L, "Service", BigDecimal.valueOf(100),"Description", company);
        ServiceEntity service2 = new ServiceEntity(2L, "Service", BigDecimal.valueOf(100), "Description",company);

        when(service.findByCompanyId(1L)).thenReturn(Arrays.asList(service1, service2));

        mockMvc.perform(get("/service/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
