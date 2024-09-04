package br.com.barbertech.mappers;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;

public class BarberMapper implements Mapper<BarberEntity, BarberDTO> {

    private final AddressMapper addressMapper = new AddressMapper();

    @Override
    public BarberDTO toDTO(BarberEntity entity) {
        if (entity == null) {
            return null;
        }
        BarberDTO dto = new BarberDTO();
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setGender(entity.getGender());
        dto.setAddress(addressMapper.toDTO(entity.getAddress()));
        return dto;
    }

    @Override
    public BarberEntity toEntity(BarberDTO dto) {
        if (dto == null) {
            return null;
        }
        BarberEntity entity = new BarberEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setAddress(addressMapper.toEntity(dto.getAddress()));
        return entity;
    }
}
