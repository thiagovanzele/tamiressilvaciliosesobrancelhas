package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FutureAppointmentValidator implements AppointmentValidator {
    @Override
    public void validateAppointment(AppointmentRequestDTO appointment) {
        LocalDateTime now = LocalDateTime.now();
        if (appointment.bookingTime().isBefore(now)) {
            throw new ValidationException("The date must be later than the current date.");
        }
    }
}
