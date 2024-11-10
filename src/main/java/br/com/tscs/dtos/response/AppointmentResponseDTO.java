package br.com.tscs.dtos.response;

import br.com.tscs.model.Appointment;
import br.com.tscs.model.Procedure;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public record AppointmentResponseDTO(String clientName, Set<ProcedureResponseDTO> procedures, String appointmentDuration, String bookingTime, Double totalValue) {

    public AppointmentResponseDTO(Appointment appointment) {
        this(appointment.getClient().getName(),
                appointment.getProcedures().stream().map(ProcedureResponseDTO::new).collect(Collectors.toSet()),
                formatDuration(appointment.getTotalDuration()),
                appointment.getBookingTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                appointment.getTotalValue());
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hora").append(hours > 1 ? "s" : "").append(" ");
        }
        if (minutes > 0) {
            result.append("e ").append(minutes).append(" minuto").append(minutes > 1 ? "s" : "");
        }

        return result.toString().trim();
    }
}
