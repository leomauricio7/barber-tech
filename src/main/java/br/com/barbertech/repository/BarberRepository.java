package br.com.barbertech.repository;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberRepository extends JpaRepository<BarberEntity, Long> {
    List<BarberEntity> findByCompanyId(long companyId);

    @Query("SELECT b FROM BarberEntity b JOIN b.services s WHERE s.id = :serviceId AND b.company.id = :companyId")
    List<BarberEntity> findByServiceIdAndCompanyId(@Param("serviceId") Long serviceId, @Param("companyId") Long companyId);
}
