package br.com.barbertech.mappers;

import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, UserDTO> {

    private final AddressMapper addressMapper = new AddressMapper();

    @Override
    public UserDTO toDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public UserEntity toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }
}
