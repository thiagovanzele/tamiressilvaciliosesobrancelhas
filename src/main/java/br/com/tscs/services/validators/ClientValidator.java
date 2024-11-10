package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator implements AppointmentValidator {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void validateAppointment(AppointmentRequestDTO appointment) {
        if (!clientRepository.existsById(appointment.clientID())) {
            throw new ResourceNotFoundException("Client not found");
        }
    }
}
