package br.com.barbertech.service;

import br.com.barbertech.entity.BarberEntity;
import br.com.barbertech.entity.SchedulingEntity;
import br.com.barbertech.exception.BadRequestException;
import br.com.barbertech.repository.SchedulingRepository;
import br.com.barbertech.enums.StatusSchedulingEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    public SchedulingEntity save(SchedulingEntity schedulingEntity) {

        // Verifica se já existe um agendamento para o mesmo barbeiro, data e horário
        Optional<SchedulingEntity> existingScheduling = schedulingRepository.findByBarberIdAndDate(
                schedulingEntity.getBarber().getId(), schedulingEntity.getDate());

        if (existingScheduling.isPresent()) {
            throw new BadRequestException("O barbeiro já possui um agendamento para este horário.");
        }


        return schedulingRepository.save(schedulingEntity);
    }

    public void deleteById(Long id) {
        schedulingRepository.deleteById(id);
    }

    public Optional<SchedulingEntity> findById(Long id){
        return schedulingRepository.findById(id);
    }

    public List<SchedulingEntity> get() {
        return schedulingRepository.findAll();
    }

    // metodos de filtros mas especificos
    public List<SchedulingEntity> getByBarberId(Long barberId) {
        return schedulingRepository.findByBarberId(barberId);
    }

    public List<SchedulingEntity> getByServiceId(Long serviceId) {
        return schedulingRepository.findByServiceEntityId(serviceId);
    }

    public List<SchedulingEntity> getByClientId(Long clientId) {
        return schedulingRepository.findByClientId(clientId);
    }

    public List<SchedulingEntity> getByCompanyId(Long companyId) {
        return schedulingRepository.findByCompanyId(companyId);
    }

    public List<SchedulingEntity> getByClientIdAndCompanyId(Long clientId, Long companyId) {
        return schedulingRepository.findByClientIdAndCompanyId(clientId, companyId);
    }

    public List<SchedulingEntity> getByBarberIdAndServiceIdAndStatus(Long barberId, Long serviceId, StatusSchedulingEnum status) {
        return schedulingRepository.findByBarberIdAndServiceEntityIdAndStatus(barberId, serviceId, status);
    }

    public List<SchedulingEntity> getByBarberAndServiceAndStatus(Long barberId, Long serviceId, StatusSchedulingEnum status) {
        return schedulingRepository.findByBarberAndServiceAndStatus(barberId, serviceId, status);
    }
}
