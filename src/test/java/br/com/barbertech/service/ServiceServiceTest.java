package br.com.barbertech.service;

import br.com.barbertech.dto.ServiceDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import br.com.barbertech.exception.NotFoundException;
import br.com.barbertech.mappers.ServiceMapper;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private ServiceService serviceService;

    private ServiceDTO serviceDTO;
    private ServiceEntity serviceEntity;
    private CompanyEntity companyEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup CompanyEntity
        companyEntity = new CompanyEntity();
        companyEntity.setId(1L);

        // Setup ServiceDTO
        serviceDTO = new ServiceDTO();
        serviceDTO.setIdCompany(1L);
        serviceDTO.setName("Haircut");
        serviceDTO.setPrice(BigDecimal.valueOf(50.0));
        serviceDTO.setDescription("Basic haircut");

        // Setup ServiceEntity
        serviceEntity = new ServiceEntity();
        serviceEntity.setId(1L);
        serviceEntity.setName("Haircut");
        serviceEntity.setPrice(BigDecimal.valueOf(50.0));
        serviceEntity.setDescription("Basic haircut");
        serviceEntity.setCompany(companyEntity);
    }

    @Test
    void save_Successful() {
        when(companyRepository.findById(serviceDTO.getIdCompany())).thenReturn(Optional.of(companyEntity));
        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(serviceEntity);

        ServiceDTO savedService = serviceService.save(serviceDTO);

        assertNotNull(savedService);
        assertEquals(serviceDTO.getName(), savedService.getName());
        assertEquals(serviceDTO.getPrice(), savedService.getPrice());
        verify(companyRepository, times(1)).findById(serviceDTO.getIdCompany());
        verify(serviceRepository, times(1)).save(any(ServiceEntity.class));
    }

    @Test
    void save_CompanyNotFound() {
        when(companyRepository.findById(serviceDTO.getIdCompany())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceService.save(serviceDTO));
        verify(companyRepository, times(1)).findById(serviceDTO.getIdCompany());
        verify(serviceRepository, never()).save(any(ServiceEntity.class));
    }

    @Test
    void get_Successful() {
        when(serviceRepository.findAll()).thenReturn(List.of(serviceEntity));

        List<ServiceEntity> services = serviceService.get();

        assertNotNull(services);
        assertEquals(1, services.size());
        assertEquals(serviceEntity, services.get(0));
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void findByCompanyId_Successful() {
        Long companyId = 1L;
        when(serviceRepository.findByCompanyId(companyId)).thenReturn(List.of(serviceEntity));

        List<ServiceEntity> services = serviceService.findByCompanyId(companyId);

        assertNotNull(services);
        assertEquals(1, services.size());
        assertEquals(serviceEntity, services.get(0));
        verify(serviceRepository, times(1)).findByCompanyId(companyId);
    }

    @Test
    void findById_Successful() {
        Long serviceId = 1L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(serviceEntity));

        Optional<ServiceEntity> result = serviceService.findById(serviceId);

        assertTrue(result.isPresent());
        assertEquals(serviceEntity, result.get());
        verify(serviceRepository, times(1)).findById(serviceId);
    }

    @Test
    void findById_NotFound() {
        Long serviceId = 2L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        Optional<ServiceEntity> result = serviceService.findById(serviceId);

        assertFalse(result.isPresent());
        verify(serviceRepository, times(1)).findById(serviceId);
    }

    @Test
    void deleteById_Successful() {
        Long serviceId = 1L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.of(serviceEntity));

        serviceService.deleteById(serviceId);

        verify(serviceRepository, times(1)).findById(serviceId);
        verify(serviceRepository, times(1)).deleteById(serviceId);
    }

    @Test
    void deleteById_NotFound() {
        Long serviceId = 2L;
        when(serviceRepository.findById(serviceId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceService.deleteById(serviceId));
        verify(serviceRepository, times(1)).findById(serviceId);
        verify(serviceRepository, never()).deleteById(serviceId);
    }

    @Test
    void update_Successful() {
        when(serviceRepository.save(any(ServiceEntity.class))).thenReturn(serviceEntity);

        ServiceDTO updatedService = serviceService.update(serviceEntity);

        assertNotNull(updatedService);
        assertEquals(serviceEntity.getName(), updatedService.getName());
        assertEquals(serviceEntity.getPrice(), updatedService.getPrice());
        verify(serviceRepository, times(1)).save(any(ServiceEntity.class));
    }
}
