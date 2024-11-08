package br.com.barbertech.service;

import br.com.barbertech.dto.ClientDTO;
import br.com.barbertech.dto.CompanyDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ClientEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.enums.UserRole;
import br.com.barbertech.mappers.ClientMapper;
import br.com.barbertech.mappers.CompanyMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.ClientRepository;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final CompanyMapper mapper = new CompanyMapper();

    @Autowired
    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public CompanyEntity save(CompanyDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getEmail());
        userDTO.setPassword(PasswordUtil.hashPassword("123mudar"));
        userDTO.setRole(UserRole.COMPANY);

        UserEntity user = this.userService.save(userDTO);

        CompanyEntity entity = mapper.toEntity(dto);
        entity.setUser(user);

        CompanyEntity entitySave = companyRepository.save(entity);

        if (entity.getAddress() != null) {
            entity.getAddress().setCompany(entitySave);
            addressRepository.save(entitySave.getAddress());
        }
        return entitySave;
    }

    public List<CompanyEntity> get() {
        return companyRepository.findAll();
    }

    public Optional<CompanyEntity> findById(Long id){
        return companyRepository.findById(id);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyEntity update(CompanyEntity entity) {
        UserEntity user = entity.getUser();
        user.setRole(UserRole.COMPANY);
        entity.setUser(user);

        CompanyEntity entitySave = companyRepository.save(entity);
        if (entity.getAddress() != null) {
            addressRepository.save(entity.getAddress());
        }
        return entitySave;
    }


    public List<CompanyEntity> findBarbersByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

}
