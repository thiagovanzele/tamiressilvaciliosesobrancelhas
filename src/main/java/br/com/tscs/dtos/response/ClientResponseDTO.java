package br.com.tscs.dtos.response;

import br.com.tscs.model.Client;

public record ClientResponseDTO(String name, String email) {

    public ClientResponseDTO(Client client) {
        this(client.getName(), client.getEmail());
    }
}
