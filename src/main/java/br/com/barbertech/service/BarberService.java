package br.com.barbertech.service;

import br.com.barbertech.dto.BarberDTO;
import br.com.barbertech.dto.UserDTO;
import br.com.barbertech.entity.*;
import br.com.barbertech.enums.UserRole;
import br.com.barbertech.exception.NotFoundException;
import br.com.barbertech.mappers.BarberMapper;
import br.com.barbertech.repository.*;
import br.com.barbertech.utils.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BarberService {

    private final BarberRepository barberRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ServiceRepository serviceRepository;
    private final BarberMapper barberMapper = new BarberMapper();
    private final CompanyRepository companyRepository;
    private final SchedulingRepository schedulingRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository, AddressRepository addressRepository,  UserService userService,  ServiceRepository serviceRepository, CompanyRepository companyRepository,SchedulingRepository schedulingRepository) {
        this.barberRepository = barberRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.serviceRepository = serviceRepository;
        this.companyRepository = companyRepository;
        this.schedulingRepository = schedulingRepository;
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



    public List<String> getAvailableTimes(Long barberId, Date selectedDate) throws ParseException {
        BarberEntity barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new NotFoundException("Barbeiro não encontrado."));

        CompanyEntity company = barber.getCompany();
        if (company == null) {
            throw new NotFoundException("Empresa não vinculada ao barbeiro.");
        }

        // Extrair o horário de atendimento da empresa
        String[] hours = company.getOpeningHours().split("-");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date startTime = timeFormat.parse(hours[0]);
        Date endTime = timeFormat.parse(hours[1]);


        // Ajustar a data de entrada para ter apenas ano, mês e dia
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);
        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = selectedCalendar.getTime();

        selectedCalendar.set(Calendar.HOUR_OF_DAY, 23);
        selectedCalendar.set(Calendar.MINUTE, 59);
        selectedCalendar.set(Calendar.SECOND, 59);
        Date endOfDay = selectedCalendar.getTime();


        // Gerar intervalos de 30 minutos
        List<String> allTimes = generateTimeSlots(startTime, endTime);

        // Filtrar agendamentos existentes para o barbeiro na data selecionada
        List<Date> scheduledTimes = schedulingRepository.findByBarberIdAndDateBetween(barberId,  startOfDay, endOfDay)
                .stream()
                .map(SchedulingEntity::getDate)
                .collect(Collectors.toList());

        // Converter horários ocupados para String
        List<String> occupiedTimes = scheduledTimes.stream()
                .map(timeFormat::format)
                .collect(Collectors.toList());

        // Filtrar horários disponíveis
        allTimes.removeAll(occupiedTimes);
        return allTimes;
    }

    private List<String> generateTimeSlots(Date start, Date end) {
        List<String> timeSlots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        while (calendar.getTime().before(end) || calendar.getTime().equals(end)) {
            timeSlots.add(timeFormat.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 30);
        }

        return timeSlots;
    }

}
