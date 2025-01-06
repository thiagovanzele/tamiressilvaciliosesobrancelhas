package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.model.Client;
import br.com.tscs.repositories.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientValidatorTest {

    @InjectMocks
    ClientValidator validator;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentRequestDTO dto;

    @Test
    void shouldThrowExceptionIfClientDoesNotExist() {

        BDDMockito.given(clientRepository.existsById(dto.clientID())).willReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> validator.validateAppointment(dto));
    }

    @Test
    void shouldNotThrowExceptionIfClientExist() {

        BDDMockito.given(clientRepository.existsById(dto.clientID())).willReturn(true);

        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));
    }
}