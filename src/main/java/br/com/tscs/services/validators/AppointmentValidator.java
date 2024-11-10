package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;

public interface AppointmentValidator {

    void validateAppointment(AppointmentRequestDTO appointment);
}
