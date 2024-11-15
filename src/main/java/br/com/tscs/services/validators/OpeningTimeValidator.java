package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OpeningTimeValidator implements AppointmentValidator {
    @Override
    public void validateAppointment(AppointmentRequestDTO appointment) {
        if (appointment.bookingTime().getHour() < 7) {
            throw new ValidationException("Appointment cannot be scheduled before 7 AM");
        }
    }
}
