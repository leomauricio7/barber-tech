package br.com.barbertech.mappers;

import br.com.barbertech.dto.SchedulingDTO;
import br.com.barbertech.entity.*;
import br.com.barbertech.exception.NotFoundException;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.repository.ClientRepository;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulingMapper {

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Conversão de SchedulingEntity para SchedulingDTO
    public SchedulingDTO toDto(SchedulingEntity schedulingEntity) {
        if (schedulingEntity == null) {
            return null;
        }

        SchedulingDTO dto = new SchedulingDTO();
        dto.setId(schedulingEntity.getId());
        dto.setIdClient(schedulingEntity.getClient().getId());
        dto.setIdCompany(schedulingEntity.getCompany().getId());
        dto.setIdBarber(schedulingEntity.getBarber().getId());
        dto.setIdService(schedulingEntity.getServiceEntity().getId());
        dto.setDate(schedulingEntity.getDate());
        dto.setStatus(schedulingEntity.getStatus());

        return dto;
    }

    // Conversão de SchedulingDTO para SchedulingEntity
    public SchedulingEntity toEntity(SchedulingDTO schedulingDTO) {
        if (schedulingDTO == null) {
            return null;
        }

        SchedulingEntity entity = new SchedulingEntity();
        // Atribuindo valores diretos
        entity.setId(schedulingDTO.getId());
        entity.setDate(schedulingDTO.getDate());
        entity.setStatus(schedulingDTO.getStatus());

        // Buscando as entidades relacionadas
        ClientEntity client = clientRepository.findById(schedulingDTO.getIdClient()).orElseThrow(() -> new NotFoundException("Cliente inválido."));
        BarberEntity barber = barberRepository.findById(schedulingDTO.getIdBarber()).orElseThrow(() -> new NotFoundException("Barbeiro inválido."));
        CompanyEntity company = companyRepository.findById(schedulingDTO.getIdCompany()).orElseThrow(() -> new NotFoundException("Barbearia inválida."));
        ServiceEntity service = serviceRepository.findById(schedulingDTO.getIdService()).orElseThrow(() -> new NotFoundException("Servico inválido."));

        entity.setBarber(barber);
        entity.setCompany(company);
        entity.setServiceEntity(service);
        entity.setClient(client);

        return entity;
    }
}
