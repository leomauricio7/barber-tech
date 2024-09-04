package br.com.barbertech.mappers;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;

public class BarberMapper implements Mapper<BarberEntity, BarberDTO> {

    private final AddressMapper addressMapper = new AddressMapper();

    @Override
    public BarberDTO toDTO(BarberEntity barber) {
        if (barber == null) {
            return null;
        }
        BarberDTO barberDTO = new BarberDTO();
        barberDTO.setName(barber.getName());
        barberDTO.setEmail(barber.getEmail());
        barberDTO.setPhone(barber.getPhone());
        barberDTO.setAddress(addressMapper.toDTO(barber.getAddress()));
        return barberDTO;
    }

    @Override
    public BarberEntity toEntity(BarberDTO barberDTO) {
        if (barberDTO == null) {
            return null;
        }
        BarberEntity barber = new BarberEntity();
        barber.setName(barberDTO.getName());
        barber.setEmail(barberDTO.getEmail());
        barber.setPhone(barberDTO.getPhone());
        barber.setAddress(addressMapper.toEntity(barberDTO.getAddress()));
        return barber;
    }
}
