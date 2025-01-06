package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.enums.TypeService;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Service;
import br.com.tscs.repositories.ServiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClosingTimeValidatorTest {

    @InjectMocks
    private ClosingTimeValidator validator;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private Service service;

    @Test
    void shouldThrowExceptionIfServiceDoesNotExist() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> validator.validateAppointment(dto));
    }

    @Test
    void shouldNotThrowExceptionIfServiceExists() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.of(service));
        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));
    }

    @Test
    void shouldThrowExceptionIfEndTimeIsAfterClosingTime() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        Service service = new Service();
        service.setDuration(Duration.ofMinutes(90));

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.of(service));
        Assertions.assertThrows(ValidationException.class, () -> validator.validateAppointment(dto));
    }

    @Test
    void shouldNotThrowExceptionIfEndTimeIsBeforeClosingTime() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        Service service = new Service();
        service.setDuration(Duration.ofMinutes(60));

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.of(service));
        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));
    }

}