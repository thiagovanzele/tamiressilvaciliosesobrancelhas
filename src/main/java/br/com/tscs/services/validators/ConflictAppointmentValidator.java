package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Appointment;
import br.com.tscs.model.Service;
import br.com.tscs.repositories.AppointmentRepository;
import br.com.tscs.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ConflictAppointmentValidator implements AppointmentValidator{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void validateAppointment(AppointmentRequestDTO appointment) {
        Duration totalDuration = appointment.procedures().stream()
                .map(procedureRequestDTO -> {
                    Service service = serviceRepository.findById(procedureRequestDTO.serviceID())
                            .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
                    return service.getDuration();
                })
                .reduce(Duration.ZERO, Duration::plus);

        LocalDateTime requestedEndTime = appointment.bookingTime().plus(totalDuration);

        List<Appointment> appointments = appointmentRepository.findAppointmentsByBookingTime(
                appointment.bookingTime(), requestedEndTime);

        if (!appointments.isEmpty()) {
            throw new ValidationException("Unavailable time, there is already an appointment scheduled for this time");
        }
    }
}
