package br.com.tscs.dtos.request;

import br.com.tscs.enums.TypeService;

public record ProcedureRequestDTO(Long serviceID, TypeService typeService) {
}
