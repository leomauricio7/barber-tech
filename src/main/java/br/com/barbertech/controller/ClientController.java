package br.com.barbertech.controller;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/client")
@RestController
public class ClientController {

    @Autowired
    private ClientService service;


    @GetMapping()
    public List<ClientEntity> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable(value = "id") long id) {
        Optional<ClientEntity> entity = service.findById(id);
        return entity.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<ClientEntity> Post(@Valid @RequestBody ClientDTO dto) {
        ClientEntity entity = service.save(dto);
        return new ResponseEntity<ClientEntity>(entity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientEntity> put(@PathVariable(value = "id") long id, @Valid @RequestBody ClientDTO dto) {
        Optional<ClientEntity> entity = service.findById(id);
        if (entity.isPresent()) {

            entity.get().setName(dto.getName());
            entity.get().setEmail(dto.getEmail());
            entity.get().setPhone(dto.getPhone());
            entity.get().setGender(dto.getGender());

            ClientEntity entityUpdate = service.update(entity.get());
            return new ResponseEntity<ClientEntity>(entityUpdate, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {
        Optional<ClientEntity> entity = service.findById(id);
        if (entity.isPresent()) {
            service.deleteById(entity.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


