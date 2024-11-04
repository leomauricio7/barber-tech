package br.com.barbertech.controller;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.service.ClientService;
import br.com.barbertech.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;


    @GetMapping()
    public List<CompanyEntity> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyEntity> getById(@PathVariable(value = "id") long id) {
        Optional<CompanyEntity> entity = service.findById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<CompanyEntity> Post(@Valid @RequestBody CompanyDTO dto) {
        CompanyEntity entity = service.save(dto);
        return new ResponseEntity<CompanyEntity>(entity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CompanyEntity> put(@PathVariable(value = "id") long id, @Valid @RequestBody CompanyDTO dto) {
        Optional<CompanyEntity> entity = service.findById(id);
        if (entity.isPresent()) {

            entity.get().setName(dto.getName());
            entity.get().setPhone(dto.getPhone());
            entity.get().setEmail(dto.getEmail());
            entity.get().setOpeningHours(dto.getOpeningHours());

            CompanyEntity entityUpdate = service.update(entity.get());
            return new ResponseEntity<CompanyEntity>(entityUpdate, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {
        Optional<CompanyEntity> entity = service.findById(id);
        if (entity.isPresent()) {
            service.deleteById(entity.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


