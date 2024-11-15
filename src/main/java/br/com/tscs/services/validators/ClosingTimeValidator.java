package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Service;
import br.com.tscs.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class ClosingTimeValidator implements AppointmentValidator {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void validateAppointment(AppointmentRequestDTO appointment) {
        Duration totalDuration = appointment.procedures().stream().map(p -> {
                    Service service = serviceRepository.findById(p.serviceID()).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
                    return service.getDuration();
                })
                .reduce(Duration.ZERO, Duration::plus);

        LocalTime endTime = appointment.bookingTime().plus(totalDuration).toLocalTime();

        if (endTime.isAfter(LocalTime.of(19, 0))) {
            throw new ValidationException("Appointment cannot be scheduled after 7 PM");
        }
    }
}
