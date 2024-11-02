package br.com.tscs.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(@NotBlank String name,
                               @NotBlank @Email String email,
                               @NotBlank String document,
                               @NotBlank String phoneNumber,
                               @NotBlank String postalCode,
                               @NotBlank String houseNumber) {
}
