package br.com.barbertech.mappers;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;

public class ClientMapper implements Mapper<ClientEntity, ClientDTO> {

    private final AddressMapper addressMapper = new AddressMapper();

    @Override
    public ClientDTO toDTO(ClientEntity entity) {
        if (entity == null) {
            return null;
        }
        ClientDTO dto = new ClientDTO();
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setGender(entity.getGender());
        dto.setAddress(addressMapper.toDTO(entity.getAddress()));
        return dto;
    }

    @Override
    public ClientEntity toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }
        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setAddress(addressMapper.toEntity(dto.getAddress()));
        return entity;
    }
}
