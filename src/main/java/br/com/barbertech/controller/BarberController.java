package br.com.barbertech.controller;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.service.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/barber")
@RestController
public class BarberController {

    @Autowired
    private BarberService barberService;

    @GetMapping()
    public List<BarberEntity> get() {
        return barberService.get();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BarberEntity> getById(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> barber = barberService.findById(id);
        return barber.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<BarberEntity> Post(@Valid @RequestBody BarberDTO barberDTO)
    {
        BarberEntity barber =  barberService.save(barberDTO);
        return new ResponseEntity<BarberEntity>(barber, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BarberEntity> put(@PathVariable(value = "id") long id, @Valid @RequestBody BarberDTO barberDTO)
    {
        Optional<BarberEntity> oldBarber = barberService.findById(id);
        if(oldBarber.isPresent()){

            BarberEntity barberEntity = barberService.save(barberDTO);
            return new ResponseEntity<BarberEntity>(barberEntity, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> barber = barberService.findById(id);
        if(barber.isPresent()){
            barberService.deleteById(barber.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
