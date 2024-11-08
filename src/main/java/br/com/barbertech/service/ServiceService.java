package br.com.barbertech.service;

import br.com.barbertech.dto.ServiceDTO;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import br.com.barbertech.exception.NotFoundException;
import br.com.barbertech.mappers.ServiceMapper;
import br.com.barbertech.repository.CompanyRepository;
import br.com.barbertech.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final CompanyRepository companyRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper mapper = new ServiceMapper();


    @Autowired
    public ServiceService(ServiceRepository serviceRepository, CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        this.serviceRepository = serviceRepository;
    }

    public ServiceDTO save(ServiceDTO dto) {
        CompanyEntity companyEntity = this.companyRepository.findById(dto.getIdCompany()).orElseThrow(() -> new NotFoundException("Company inválida."));
        ServiceEntity entity = mapper.toEntity(dto);
        entity.setCompany(companyEntity);

        return mapper.toDTO(serviceRepository.save(entity));
    }

    public List<ServiceEntity> get() {
        return serviceRepository.findAll();
    }

    public List<ServiceEntity> findByCompanyId(long id) {
        return serviceRepository.findByCompanyId(id);
    }

    public Optional<ServiceEntity> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteById(Long id) {
        serviceRepository.findById(id).orElseThrow(()-> new NotFoundException("Serviço não encontrado"));
        serviceRepository.deleteById(id);
    }

    public ServiceDTO update(ServiceEntity entity) {
        return mapper.toDTO(serviceRepository.save(entity));
    }

}
