package br.com.tscs.services;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.dtos.response.AppointmentResponseDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Appointment;
import br.com.tscs.model.Client;
import br.com.tscs.model.Procedure;
import br.com.tscs.repositories.AppointmentRepository;
import br.com.tscs.repositories.ClientRepository;
import br.com.tscs.repositories.ServiceRepository;
import br.com.tscs.services.validators.AppointmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    List<AppointmentValidator> validators;

    public Appointment insert(AppointmentRequestDTO data) {
        validators.forEach(validator -> validator.validateAppointment(data));

        Client client = clientRepository.findById(data.clientID()).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Set<Procedure> procedures = new HashSet<>();

        procedures = data.procedures().stream()
                .map(procedureRequestDTO -> new Procedure(procedureRequestDTO, serviceRepository))
                .collect(Collectors.toSet());

        Appointment appointment = new Appointment();
        appointment.setBookingTime(data.bookingTime());
        appointment.setClient(client);
        appointment.setProcedures(procedures);
        appointment.setTotalDuration();
        appointment.setTotalValue();

        return appointmentRepository.save(appointment);
    }

    public AppointmentResponseDTO findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not fount"));
        return new AppointmentResponseDTO(appointment);
    }

    public AppointmentResponseDTO update(Long id, LocalDateTime bookingTime, Set<ProcedureRequestDTO> procedures) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (bookingTime.isBefore(LocalDateTime.now())) {
            throw new ValidationException("The new date must be later than the current date.");
        }

        updateData(appointment, bookingTime, procedures);
        appointment.setTotalValue();
        appointment.setTotalDuration();
        appointmentRepository.save(appointment);
        return new AppointmentResponseDTO(appointment);
    }

    private void updateData(Appointment appointment, LocalDateTime bookingTime, Set<ProcedureRequestDTO> proceduresData) {
        appointment.setBookingTime(bookingTime);
        if (proceduresData != null && !proceduresData.isEmpty()) {
            Set<Procedure> procedures = proceduresData.stream()
                    .map(procedureRequestDTO -> new Procedure(procedureRequestDTO, serviceRepository))
                    .collect(Collectors.toSet());
            appointment.setProcedures(procedures);
        }
    }
}
