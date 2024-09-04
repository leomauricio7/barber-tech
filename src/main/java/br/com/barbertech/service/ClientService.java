package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.mappers.ClientMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ClientMapper barberMapper = new ClientMapper();


    @Autowired
    public ClientService(ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    public ClientEntity save(ClientDTO dto) {
        ClientEntity entity = barberMapper.toEntity(dto);
        ClientEntity entitySave = clientRepository.save(entity);

        if (entity.getAddress() != null) {
            entity.getAddress().setClient(entitySave);
            addressRepository.save(entitySave.getAddress());
        }
        return entitySave;
    }

    public List<ClientEntity> get() {
        return clientRepository.findAll();
    }

    public Optional<ClientEntity> findById(Long id){
        return clientRepository.findById(id);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public ClientEntity update(ClientEntity entity) {
        ClientEntity entitySave = clientRepository.save(entity);
        if (entity.getAddress() != null) {
            addressRepository.save(entity.getAddress());
        }
        return entitySave;
    }

}
