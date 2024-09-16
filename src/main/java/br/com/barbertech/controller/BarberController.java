package br.com.barbertech.controller;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.mappers.BarberMapper;
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
    private BarberService service;


    @GetMapping()
    public List<BarberEntity> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BarberEntity> getById(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> entity = service.findById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<BarberEntity> Post(@Valid @RequestBody BarberDTO dto)
    {
        BarberEntity entity =  service.save(dto);
        return new ResponseEntity<BarberEntity>(entity, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BarberEntity> put(@PathVariable(value = "id") long id, @Valid @RequestBody BarberDTO dto)
    {
        Optional<BarberEntity> entity = service.findById(id);
        if(entity.isPresent()){

            entity.get().setName(dto.getName());
            entity.get().setEmail(dto.getEmail());
            entity.get().setPhone(dto.getPhone());
            entity.get().setGender(dto.getGender());
            BarberEntity entityUpdate = service.update(entity.get());
            return new ResponseEntity<BarberEntity>(entityUpdate, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id)
    {
        Optional<BarberEntity> entity = service.findById(id);
        if(entity.isPresent()){
            service.deleteById(entity.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
