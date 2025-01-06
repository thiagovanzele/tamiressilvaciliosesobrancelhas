package br.com.tscs.config;

import br.com.tscs.enums.TypeService;
import br.com.tscs.model.*;
import br.com.tscs.repositories.AppointmentRepository;
import br.com.tscs.repositories.ClientRepository;
import br.com.tscs.repositories.ServiceRepository;
import br.com.tscs.utils.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Profile("prod")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AddresService addresService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void run(String... args) throws Exception {

        Address a1 = addresService.findByPostalCode("09061030", "344");
        Client c1 = new Client(null, "Thiago", "thiago@teste.com", "0001001001", "12345566", a1);
        clientRepository.save(c1);

        Service volumeBrasileiro = new Service(null, "Brasileiro", 100.0, Duration.ofHours(1));
        Service volumeEgipicio = new Service(null, "Egipicio", 150.0, Duration.ofHours(1));
        Service volumeRusso = new Service(null, "Russo", 200.0, Duration.ofHours(1));
        Service sobrancelhaSimples = new Service(null, "Sobrancelha Simples", 20.0, Duration.ofMinutes(30));
        serviceRepository.saveAll(Arrays.asList(volumeEgipicio,volumeRusso,volumeBrasileiro, sobrancelhaSimples));

        // Criar os procedimentos como embeddables
        Set<Procedure> procedures = new HashSet<>();
        procedures.add(new Procedure(volumeBrasileiro, TypeService.NEW));
        procedures.add(new Procedure(volumeBrasileiro, TypeService.MAINTENANCE));
        procedures.add(new Procedure(sobrancelhaSimples, TypeService.NEW));

        // Criar o agendamento e adicionar os procedimentos
        Appointment appointment = new Appointment();
        appointment.setClient(c1);
        appointment.setProcedures(procedures);
        appointment.setTotalValue();
        appointment.setTotalDuration();
        appointment.setBookingStart(LocalDateTime.now());
        appointmentRepository.save(appointment);
    }
}
