package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import br.com.barbertech.entity.UserEntity;
import br.com.barbertech.enums.UserRole;
import br.com.barbertech.exception.NotFoundException;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.repository.AddressRepository;
import br.com.barbertech.repository.BarberRepository;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.ServiceRepository;
import br.com.barbertech.utils.PasswordUtil;
import jakarta.transaction.Transactional;
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
    private final ServiceRepository serviceRepository;
    private final BarberMapper barberMapper = new BarberMapper();
    private final CompanyRepository companyRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository, AddressRepository addressRepository,  UserService userService,  ServiceRepository serviceRepository, CompanyRepository companyRepository) {
        this.barberRepository = barberRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
    }

    public BarberEntity save(BarberDTO dto) {

        CompanyEntity companyEntity = this.companyRepository.findById(dto.getIdCompany()).orElseThrow(() -> new NotFoundException("Company inválida."));

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getEmail());
        userDTO.setPassword(PasswordUtil.hashPassword("123mudar"));
        userDTO.setRole(UserRole.BARBER);

        UserEntity user = this.userService.save(userDTO);


        BarberEntity entity = barberMapper.toEntity(dto);

        entity.setCompany(companyEntity);
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

    @Transactional
    public BarberEntity linkServicesToBarber(Long barberId, List<Long> serviceIds) {
        Optional<BarberEntity> barberOpt = barberRepository.findById(barberId);
        if (barberOpt.isEmpty()) {
            throw new RuntimeException("Barbeiro não encontrado");
        }

        BarberEntity barber = barberOpt.get();
        List<ServiceEntity> services = serviceRepository.findAllById(serviceIds);

        if (services.isEmpty()) {
            throw new RuntimeException("Serviços não encontrados");
        }

        barber.setServices(services);

        return barberRepository.save(barber);
    }

    public List<BarberEntity> findByCompanyId(long id) {
        return barberRepository.findByCompanyId(id);
    }

    public List<BarberEntity> getBarbersByServiceIdAndCompanyId(Long serviceId, Long companyId) {
        return barberRepository.findByServiceIdAndCompanyId(serviceId, companyId);
    }

    @Transactional
    public BarberEntity unlinkServiceFromBarber(Long barberId, Long serviceId) {

        Optional<BarberEntity> barberOpt = barberRepository.findById(barberId);
        if (barberOpt.isEmpty()) {
            throw new RuntimeException("Barbeiro não encontrado");
        }

        BarberEntity barber = barberOpt.get();

        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            throw new RuntimeException("Serviço não encontrado");
        }

        ServiceEntity service = serviceOpt.get();

        barber.getServices().remove(service);

        return barberRepository.save(barber);
    }

}
