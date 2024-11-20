package br.com.barbertech.repository;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.CompanyEntity;
import br.com.barbertech.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    List<ServiceEntity> findByCompanyId(long companyId);
}
