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
class OpeningTimeValidatorTest {

    @InjectMocks
    private OpeningTimeValidator validator;

    @Test
    void shouldThrowExceptionIfStartTimeIsBeforeOpeningTime() {
        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 6, 0)
        );

        Assertions.assertThrows(ValidationException.class, () -> validator.validateAppointment(dto));

    }

    @Test
    void shouldNotThrowExceptionIfStartTimeIsAfterOpeningTime() {
        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 7, 0)
        );

        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));

    }

}