package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.enums.UserRole;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BarberService {

    private final BarberRepository barberRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final BarberMapper barberMapper = new BarberMapper();


    @Autowired
    public BarberService(BarberRepository barberRepository, AddressRepository addressRepository,  UserService userService) {
        this.barberRepository = barberRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public BarberEntity save(BarberDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getEmail());
        userDTO.setPassword(PasswordUtil.hashPassword("123mudar"));
        userDTO.setRole(UserRole.BARBER);

        UserEntity user = this.userService.save(userDTO);


        BarberEntity entity = barberMapper.toEntity(dto);
        entity.setUser(user);
        return barberRepository.save(entity);
    }

    public List<BarberEntity> get() {
        return barberRepository.findAll();
    }

    public Optional<BarberEntity> findById(Long id){
        return barberRepository.findById(id);
    }
    public void deleteById(Long id) {
        barberRepository.deleteById(id);
    }

    public BarberEntity update(BarberEntity entity) {
        return barberRepository.save(entity);
    }

}
