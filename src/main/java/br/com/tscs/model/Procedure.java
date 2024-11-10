package br.com.tscs.model;

import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.enums.TypeService;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.repositories.ServiceRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@Table(name = "tb_procedure")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Procedure {

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Enumerated(EnumType.STRING)
    private TypeService typeService;

    public Double getValue() {
        if (typeService == TypeService.NEW) {
            return service.getValue();
        } else {
            return service.getValue() * 0.5;
        }
    }

    public Procedure(ProcedureRequestDTO data, ServiceRepository serviceRepository) {
        this.service = serviceRepository.findById(data.serviceID()).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        this.typeService = data.typeService();

    }

}

