package br.com.tscs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ElementCollection
    private Set<Procedure> procedures = new HashSet<>();

    private Double totalValue;

    @Column(name = "booking_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookingTime;

    @Column(name = "duration")
    private Duration totalDuration;

    public void setTotalValue() {
        this.totalValue = procedures.stream().mapToDouble(Procedure::getValue).sum();
    }

    public void setTotalDuration() {
        totalDuration = Duration.ZERO;
        for (Procedure procedure : procedures) {
            totalDuration = totalDuration.plus(procedure.getService().getDuration());
        }
    }
}
