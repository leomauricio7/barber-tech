package br.com.barbertech.mappers;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.entity.CompanyEntity;

public class CompanyMapper implements Mapper<CompanyEntity, CompanyDTO> {

    private final AddressMapper addressMapper = new AddressMapper();

    @Override
    public CompanyDTO toDTO(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setOpeningHours(entity.getOpeningHours());
        dto.setAddress(addressMapper.toDTO(entity.getAddress()));
        return dto;
    }

    @Override
    public CompanyEntity toEntity(CompanyDTO dto) {
        if (dto == null) {
            return null;
        }
        CompanyEntity entity = new CompanyEntity();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setOpeningHours(dto.getOpeningHours());
        entity.setAddress(addressMapper.toEntity(dto.getAddress()));
        return entity;
    }
}
