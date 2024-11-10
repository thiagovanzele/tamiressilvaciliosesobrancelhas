package br.com.tscs.dtos.request;

import br.com.tscs.model.Procedure;

import java.time.LocalDateTime;
import java.util.Set;

public record AppointmentRequestDTO(Long clientID, Set<ProcedureRequestDTO> procedures, LocalDateTime bookingTime) {
}
