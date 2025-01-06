package br.com.tscs.services.validators;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.enums.TypeService;
import br.com.tscs.exceptions.ValidationException;
import br.com.tscs.model.Appointment;
import br.com.tscs.model.Service;
import br.com.tscs.repositories.AppointmentRepository;
import br.com.tscs.repositories.ServiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConflictAppointmentValidatorTest {

    @InjectMocks
    private ConflictAppointmentValidator validator;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private Appointment appointment;

    @Mock
    private Service service;

    @Test
    void shouldThrowExceptionIfWasAConflictWithAppointment() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        LocalDateTime requestedEndTime = dto.bookingTime().plus(service.getDuration());

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.of(service));

        BDDMockito.given(appointmentRepository.findAppointmentsByBookingTime(dto.bookingTime(),
                requestedEndTime)).willReturn(List.of(appointment));

        Assertions.assertThrows(ValidationException.class, () -> validator.validateAppointment(dto));
    }

    @Test
    void shouldNotThrowExceptionIfWasNotAConflictWithAppointment() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO(
                1L,
                Set.of(new ProcedureRequestDTO(1L, TypeService.NEW)),
                LocalDateTime.of(2025, 1, 6, 18, 0)
        );

        LocalDateTime requestedEndTime = dto.bookingTime().plus(service.getDuration());

        BDDMockito.given(serviceRepository.findById(1L)).willReturn(Optional.of(service));

        BDDMockito.given(appointmentRepository.findAppointmentsByBookingTime(dto.bookingTime(),
                requestedEndTime)).willReturn(List.of());

        Assertions.assertDoesNotThrow(() -> validator.validateAppointment(dto));
    }

}