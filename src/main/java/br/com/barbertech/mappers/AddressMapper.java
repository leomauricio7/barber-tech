package br.com.barbertech.mappers;

import br.com.barbertech.dto.AddressDTO;
import br.com.barbertech.entity.AddressEntity;

public class AddressMapper implements Mapper<AddressEntity, AddressDTO> {

    @Override
    public AddressDTO toDTO(AddressEntity address) {
        if (address == null) {
            return null;
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }

    @Override
    public AddressEntity toEntity(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        AddressEntity address = new AddressEntity();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }
}
