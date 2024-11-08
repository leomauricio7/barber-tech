package br.com.barbertech.repository;


import br.com.barbertech.entity.SchedulingEntity;
import br.com.barbertech.enums.StatusSchedulingEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SchedulingRepository extends JpaRepository<SchedulingEntity, Long> {

    // Filtrar por ID do barbeiro
    List<SchedulingEntity> findByBarberId(Long barberId);

    // Filtrar por ID do serviço
    List<SchedulingEntity> findByServiceEntityId(Long serviceId);

    // Filtrar por ID do cliente
    List<SchedulingEntity> findByClientId(Long clientId);

    // Filtrar por ID da empresa
    List<SchedulingEntity> findByCompanyId(Long companyId);

    // Filtrar por múltiplos IDs: cliente e empresa
    List<SchedulingEntity> findByClientIdAndCompanyId(Long clientId, Long companyId);

    // Filtrar por múltiplos IDs: barbeiro, serviço e status
    List<SchedulingEntity> findByBarberIdAndServiceEntityIdAndStatus(Long barberId, Long serviceId, StatusSchedulingEnum status);

    // Usando @Query para uma consulta mais complexa com IDs
    @Query("SELECT s FROM SchedulingEntity s WHERE s.barber.id = :barberId AND s.serviceEntity.id = :serviceId AND s.status = :status")
    List<SchedulingEntity> findByBarberAndServiceAndStatus(@Param("barberId") Long barberId,
                                                           @Param("serviceId") Long serviceId,
                                                           @Param("status") StatusSchedulingEnum status);

    // Verifica se existe algum agendamento para o barbeiro, no mesmo dia e horário
    Optional<SchedulingEntity> findByBarberIdAndDate(Long barberId, Date date);


    @Query("SELECT s FROM SchedulingEntity s WHERE s.barber.id = :barberId " +
            "AND s.date BETWEEN :startOfDay AND :endOfDay")
    List<SchedulingEntity> findByBarberIdAndDateBetween(@Param("barberId") Long barberId,
                                                        @Param("startOfDay") Date startOfDay,
                                                        @Param("endOfDay") Date endOfDay);
}
