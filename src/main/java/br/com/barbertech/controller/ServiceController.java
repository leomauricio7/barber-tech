package br.com.barbertech.controller;

import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.dto.ServiceDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import br.com.barbertech.service.CompanyService;
import br.com.barbertech.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/service")
@RestController
public class ServiceController {

    @Autowired
    private ServiceService service;


    @GetMapping()
    public List<ServiceEntity> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceEntity> getById(@PathVariable(value = "id") long id) {
        Optional<ServiceEntity> entity = service.findById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<ServiceDTO> Post(@Valid @RequestBody ServiceDTO dto) {
        ServiceDTO entity = service.save(dto);
        return new ResponseEntity<ServiceDTO>(entity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ServiceDTO> put(@PathVariable(value = "id") long id, @Valid @RequestBody ServiceDTO dto) {
        Optional<ServiceEntity> entity = service.findById(id);
        if (entity.isPresent()) {

            entity.get().setName(dto.getName());
            entity.get().setDescription(dto.getDescription());
            entity.get().setPrice(dto.getPrice());

            ServiceDTO entityUpdate = service.update(entity.get());
            return new ResponseEntity<ServiceDTO>(entityUpdate, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/company/{id}")
    public ResponseEntity<List<ServiceEntity>> findByCompanyId(@PathVariable(value = "id") long id) {
        List<ServiceEntity> entityList = service.findByCompanyId(id);
        return new ResponseEntity<List<ServiceEntity>>(entityList, HttpStatus.OK);
    }
}


