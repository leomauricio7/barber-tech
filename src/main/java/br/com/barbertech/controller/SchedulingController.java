package br.com.barbertech.controller;

import br.com.barbertech.dto.SchedulingDTO;
import br.com.barbertech.entity.SchedulingEntity;
import br.com.barbertech.enums.StatusSchedulingEnum;
import br.com.barbertech.mappers.SchedulingMapper;
import br.com.barbertech.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;


@RequestMapping("/scheduling")
@RestController
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @PostMapping
    public ResponseEntity<SchedulingDTO> createScheduling(@Valid @RequestBody SchedulingDTO schedulingDTO) {
        SchedulingEntity schedulingEntity = schedulingMapper.toEntity(schedulingDTO);
        SchedulingEntity savedScheduling = schedulingService.save(schedulingEntity);
        SchedulingDTO savedDTO = schedulingMapper.toDto(savedScheduling);
        return ResponseEntity.ok(savedDTO);
    }

    // Endpoint para buscar todos os agendamentos
    @GetMapping
    public List<SchedulingDTO> getAllScheduling() {
        return schedulingService.get().stream()
                .map(schedulingMapper::toDto)
                .collect(Collectors.toList());
    }

    // Endpoint para buscar agendamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<SchedulingDTO> getSchedulingById(@PathVariable Long id) {
        return schedulingService.findById(id)
                .map(schedulingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deletar agendamento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduling(@PathVariable Long id) {
        schedulingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para buscar agendamentos por ID do barbeiro
    @GetMapping("/barber/{barberId}")
    public List<SchedulingEntity> getSchedulingByBarberId(@PathVariable Long barberId) {
        return schedulingService.getByBarberId(barberId);
    }

    // Endpoint para buscar agendamentos por ID do serviço
    @GetMapping("/service/{serviceId}")
    public List<SchedulingEntity> getSchedulingByServiceId(@PathVariable Long serviceId) {
        return schedulingService.getByServiceId(serviceId);
    }

    // Endpoint para buscar agendamentos por ID do cliente
    @GetMapping("/client/{clientId}")
    public List<SchedulingEntity> getSchedulingByClientId(@PathVariable Long clientId) {
        return schedulingService.getByClientId(clientId);
    }

    // Endpoint para buscar agendamentos por ID da empresa
    @GetMapping("/company/{companyId}")
    public List<SchedulingEntity> getSchedulingByCompanyId(@PathVariable Long companyId) {
        return schedulingService.getByCompanyId(companyId);
    }

    // Endpoint para buscar agendamentos por ID do cliente e ID da empresa
    @GetMapping("/client/{clientId}/company/{companyId}")
    public List<SchedulingEntity> getSchedulingByClientIdAndCompanyId(@PathVariable Long clientId,
                                                                      @PathVariable Long companyId) {
        return schedulingService.getByClientIdAndCompanyId(clientId, companyId);
    }

    // Endpoint para buscar agendamentos por ID do barbeiro, serviço e status
    @GetMapping("/barber/{barberId}/service/{serviceId}/status/{status}")
    public List<SchedulingEntity> getSchedulingByBarberAndServiceAndStatus(@PathVariable Long barberId,
                                                                           @PathVariable Long serviceId,
                                                                           @PathVariable StatusSchedulingEnum status) {
        return schedulingService.getByBarberAndServiceAndStatus(barberId, serviceId, status);
    }

}
