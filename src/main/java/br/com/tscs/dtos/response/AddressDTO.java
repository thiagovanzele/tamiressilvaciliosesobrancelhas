package br.com.tscs.dtos.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressDTO(@JsonAlias("cep") String postalCode,
                         @JsonAlias("logradouro") String street,
                         @JsonAlias("localidade") String city,
                         @JsonAlias("estado") String state) {
}
