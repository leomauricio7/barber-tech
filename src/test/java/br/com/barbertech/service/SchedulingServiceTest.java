package br.com.barbertech.service;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.SchedulingEntity;
import br.com.barbertech.enums.StatusSchedulingEnum;
import br.com.barbertech.exception.BadRequestException;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.SchedulingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulingServiceTest {

    @Mock
    private SchedulingRepository schedulingRepository;

    @Mock
    private BarberRepository barberRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private SchedulingService schedulingService;

    private SchedulingEntity schedulingEntity;
    private BarberEntity barberEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        barberEntity = new BarberEntity();
        barberEntity.setId(1L);

        schedulingEntity = new SchedulingEntity();
        schedulingEntity.setId(1L);
        schedulingEntity.setBarber(barberEntity);
        schedulingEntity.setDate(new Date());
    }

    @Test
    void save_Successful() {
        when(schedulingRepository.findByBarberIdAndDate(barberEntity.getId(), schedulingEntity.getDate()))
                .thenReturn(Optional.empty());
        when(schedulingRepository.save(schedulingEntity)).thenReturn(schedulingEntity);

        SchedulingEntity savedScheduling = schedulingService.save(schedulingEntity);

        assertNotNull(savedScheduling);
        assertEquals(schedulingEntity, savedScheduling);
        verify(schedulingRepository, times(1)).save(schedulingEntity);
    }

    @Test
    void save_Failure_DuplicateScheduling() {
        when(schedulingRepository.findByBarberIdAndDate(barberEntity.getId(), schedulingEntity.getDate()))
                .thenReturn(Optional.of(schedulingEntity));

        assertThrows(BadRequestException.class, () -> schedulingService.save(schedulingEntity));
        verify(schedulingRepository, never()).save(schedulingEntity);
    }

    @Test
    void deleteById_Successful() {
        Long id = 1L;
        schedulingService.deleteById(id);
        verify(schedulingRepository, times(1)).deleteById(id);
    }

    @Test
    void findById_Successful() {
        Long id = 1L;
        when(schedulingRepository.findById(id)).thenReturn(Optional.of(schedulingEntity));

        Optional<SchedulingEntity> result = schedulingService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(schedulingEntity, result.get());
        verify(schedulingRepository, times(1)).findById(id);
    }

    @Test
    void findById_NotFound() {
        Long id = 2L;
        when(schedulingRepository.findById(id)).thenReturn(Optional.empty());

        Optional<SchedulingEntity> result = schedulingService.findById(id);

        assertFalse(result.isPresent());
        verify(schedulingRepository, times(1)).findById(id);
    }

    @Test
    void getAllSchedulings_Successful() {
        when(schedulingRepository.findAll()).thenReturn(List.of(schedulingEntity));

        List<SchedulingEntity> schedulings = schedulingService.get();

        assertNotNull(schedulings);
        assertEquals(1, schedulings.size());
        assertEquals(schedulingEntity, schedulings.get(0));
        verify(schedulingRepository, times(1)).findAll();
    }

    @Test
    void getByBarberId_Successful() {
        Long barberId = 1L;
        when(schedulingRepository.findByBarberId(barberId)).thenReturn(List.of(schedulingEntity));

        List<SchedulingEntity> schedulings = schedulingService.getByBarberId(barberId);

        assertNotNull(schedulings);
        assertEquals(1, schedulings.size());
        assertEquals(schedulingEntity, schedulings.get(0));
        verify(schedulingRepository, times(1)).findByBarberId(barberId);
    }

    @Test
    void getByClientId_Successful() {
        Long clientId = 1L;
        String sortBy = "date";
        String direction = "ASC";
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        when(schedulingRepository.findByClientId(clientId, sort)).thenReturn(List.of(schedulingEntity));

        List<SchedulingEntity> schedulings = schedulingService.getByClientId(clientId, sortBy, direction);

        assertNotNull(schedulings);
        assertEquals(1, schedulings.size());
        assertEquals(schedulingEntity, schedulings.get(0));
        verify(schedulingRepository, times(1)).findByClientId(clientId, sort);
    }

    @Test
    void getByBarberIdAndServiceIdAndStatus_Successful() {
        Long barberId = 1L;
        Long serviceId = 1L;
        StatusSchedulingEnum status = StatusSchedulingEnum.PENDENTE;

        when(schedulingRepository.findByBarberIdAndServiceEntityIdAndStatus(barberId, serviceId, status))
                .thenReturn(List.of(schedulingEntity));

        List<SchedulingEntity> schedulings = schedulingService.getByBarberIdAndServiceIdAndStatus(barberId, serviceId, status);

        assertNotNull(schedulings);
        assertEquals(1, schedulings.size());
        assertEquals(schedulingEntity, schedulings.get(0));
        verify(schedulingRepository, times(1)).findByBarberIdAndServiceEntityIdAndStatus(barberId, serviceId, status);
    }
}
