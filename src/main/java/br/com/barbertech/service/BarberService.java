package br.com.barbertech.service;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarberService {
    private final BarberRepository repository;

    @Autowired
    public BarberService(BarberRepository barberRepository) {
        this.repository = barberRepository;
    }

    public BarberEntity save(BarberEntity barber) {
        return repository.save(barber);
    }

    public List<BarberEntity> get() {
        return repository.findAll();
    }

    public Optional<BarberEntity> findById(Long id){
        return repository.findById(id);
    }
    public void deleteById(Long id) {
         repository.deleteById(id);
    }



}
