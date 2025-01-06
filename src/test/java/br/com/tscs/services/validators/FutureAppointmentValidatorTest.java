package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.enums.TypeService;
import br.com.tscs.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FutureAppointmentValidatorTest {

    @InjectMocks
    private FutureAppointmentValidator validator;

    @Test
    void shouldThrowExceptionIfAppointmentIsBeforeNowDate() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.now().minusMinutes(60)
        );

        Assertions.assertThrows(ValidationException.class, () -> validator.validateAppointment(dto));
    }

    @Test
    void shouldNotThrowExceptionIfAppointmentIsAfterNowDate() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.now().plusMinutes(60)
        );

        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));
    }
}