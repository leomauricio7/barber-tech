package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BarberService {

    private final BarberRepository barberRepository;
    private final AddressRepository addressRepository;
    private final BarberMapper barberMapper = new BarberMapper();


    @Autowired
    public BarberService(BarberRepository barberRepository, AddressRepository addressRepository) {
        this.barberRepository = barberRepository;
        this.addressRepository = addressRepository;
    }

    public BarberEntity save(BarberDTO dto) {
        BarberEntity entity = barberMapper.toEntity(dto);
        return barberRepository.save(entity);
    }

    public List<BarberEntity> get() {
        return barberRepository.findAll();
    }

    public Optional<BarberEntity> findById(Long id){
        return barberRepository.findById(id);
    }
    public void deleteById(Long id) {
        barberRepository.deleteById(id);
    }

    public BarberEntity update(BarberEntity entity) {
        return barberRepository.save(entity);
    }

}
