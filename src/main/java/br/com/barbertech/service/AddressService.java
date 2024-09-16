package br.com.barbertech.service;

import br.com.barbertech.entity.AddressEntity;
import br.com.barbertech.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.repository = addressRepository;
    }

    public AddressEntity save(AddressEntity address) {
        return repository.save(address);
    }

    public List<AddressEntity> get() {
        return repository.findAll();
    }

    public Optional<AddressEntity> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
