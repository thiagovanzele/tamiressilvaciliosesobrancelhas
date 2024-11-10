package br.com.tscs.dtos.request;

import java.time.LocalDateTime;
import java.util.Set;

public record AppointmentUpdateRequestDTO(LocalDateTime bookingTime, Set<ProcedureRequestDTO> procedures) {
}
