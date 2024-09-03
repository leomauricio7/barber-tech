package br.com.barbertech.controller;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.repository.BarberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BarberController {

    @Autowired
    private BarberRepository _barberRepository;

    @RequestMapping(value = "/barber", method = RequestMethod.GET)
    public List<BarberEntity> Get() {
        return _barberRepository.findAll();
    }

    @RequestMapping(value = "/barber/{id}", method = RequestMethod.GET)
    public ResponseEntity<BarberEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> barber = _barberRepository.findById(id);
        return barber.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/barber", method =  RequestMethod.POST)
    public BarberEntity Post(@Valid @RequestBody BarberEntity barberEntity)
    {
        return _barberRepository.save(barberEntity);
    }

    @RequestMapping(value = "/barber/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<BarberEntity> Put(@PathVariable(value = "id") long id, @Valid @RequestBody BarberEntity newBarberEntity)
    {
        Optional<BarberEntity> oldBarber = _barberRepository.findById(id);
        if(oldBarber.isPresent()){
            BarberEntity barberEntity = oldBarber.get();
            barberEntity.setName(newBarberEntity.getName());
            _barberRepository.save(barberEntity);
            return new ResponseEntity<BarberEntity>(barberEntity, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/barber/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> barber = _barberRepository.findById(id);
        if(barber.isPresent()){
            _barberRepository.delete(barber.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
