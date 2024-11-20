package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.enums.UserRole;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.mappers.ClientMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.repository.ClientRepository;
import br.com.barbertech.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ClientMapper mapper = new ClientMapper();


    @Autowired
    public ClientService(ClientRepository clientRepository, AddressRepository addressRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public ClientEntity save(ClientDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getEmail());
        userDTO.setPassword(PasswordUtil.hashPassword("123mudar"));
        userDTO.setRole(UserRole.CLIENT);

        UserEntity user = this.userService.save(userDTO);

        ClientEntity entity = mapper.toEntity(dto);
        entity.setUser(user);

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

        UserEntity user = entity.getUser();
        user.setRole(UserRole.CLIENT);
        entity.setUser(user);

        ClientEntity entitySave = clientRepository.save(entity);
        if (entity.getAddress() != null) {
            addressRepository.save(entity.getAddress());
        }
        return entitySave;
    }

}
