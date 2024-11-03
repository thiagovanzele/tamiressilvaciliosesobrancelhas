package br.com.tscs.services;

import br.com.tscs.dtos.request.ClientRequestDTO;
import br.com.tscs.dtos.request.ClientUpdateRequestDTO;
import br.com.tscs.dtos.response.ClientResponseDTO;
import br.com.tscs.exceptions.ResourceNotFoundException;
import br.com.tscs.model.Address;
import br.com.tscs.model.Client;
import br.com.tscs.repositories.ClientRepository;
import br.com.tscs.utils.AddresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddresService addresService;

    public Client insert(ClientRequestDTO data) {
        Address address = addresService.findByPostalCode(data.postalCode(), data.houseNumber());

        Client client = new Client();
        client.setAddress(address);
        client.setDocument(data.document());
        client.setPhoneNumber(data.phoneNumber());
        client.setEmail(data.email());
        client.setName(data.name());

        return clientRepository.save(client);
    }

    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return new ClientResponseDTO(client);
    }

    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(ClientResponseDTO::new);
    }

    public ClientResponseDTO update(Long id, ClientUpdateRequestDTO data) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        updateData(client, data);
        clientRepository.save(client);
        return new ClientResponseDTO(client);
    }

    private void updateData(Client client, ClientUpdateRequestDTO data) {
        if (data.name() != null && !data.name().isEmpty()) {
            client.setName(data.name());
        }
        if (data.email() != null && !data.email().isEmpty()) {
            client.setEmail(data.email());
        }
        if (data.phoneNumber() != null && !data.phoneNumber().isEmpty()) {
            client.setPhoneNumber(data.phoneNumber());
        }
    }
}
