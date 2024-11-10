package br.com.tscs.dtos.response;

import br.com.tscs.model.Procedure;

import java.time.Duration;

public record ProcedureResponseDTO(String name, String type, Double value, String duration) {
    public ProcedureResponseDTO(Procedure procedure) {
        this(procedure.getService().getName(), procedure.getTypeService().toString(), procedure.getValue(), formatDuration(procedure.getService().getDuration()));
    }

    private static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        if (hours > 0 && minutes > 0) {
            return String.format("%dh%d minutos", hours, minutes);
        } else if (hours > 0 && minutes == 0) {
            return String.format("%dh", hours);
        } else
            return String.format("%d minutos", minutes);
    }
}

