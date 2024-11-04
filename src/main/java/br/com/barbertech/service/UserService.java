package br.com.barbertech.service;

import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.mappers.CompanyMapper;
import br.com.barbertech.mappers.UserMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper = new UserMapper();


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserDTO dto) {
        UserEntity entity = mapper.toEntity(dto);

        return userRepository.save(entity);
    }

    public List<UserEntity> get() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id){
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity update(UserEntity entity) {
        return userRepository.save(entity);
    }

}
