package br.com.barbertech.mappers;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.dto.ServiceDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.entity.ServiceEntity;

public class ServiceMapper implements Mapper<ServiceEntity, ServiceDTO> {

    @Override
    public ServiceDTO toDTO(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }
        ServiceDTO dto = new ServiceDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setIdCompany(entity.getCompany().getId());
        return dto;
    }

    @Override
    public ServiceEntity toEntity(ServiceDTO dto) {
        if (dto == null) {
            return null;
        }
        ServiceEntity entity = new ServiceEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
