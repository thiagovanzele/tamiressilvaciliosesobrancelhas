package br.com.tscs.controller;

import br.com.tscs.dtos.request.ClientRequestDTO;
import br.com.tscs.dtos.request.ClientUpdateRequestDTO;
import br.com.tscs.dtos.response.ClientResponseDTO;
import br.com.tscs.model.Client;
import br.com.tscs.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/client")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/new")
    public ResponseEntity<ClientResponseDTO> insert(@Valid @RequestBody ClientRequestDTO data) {
        Client client = clientService.insert(data);
        ClientResponseDTO responseDTO = new ClientResponseDTO(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        ClientResponseDTO responseDTO = clientService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        Page<ClientResponseDTO> responseDTOS = clientService.findAll(pageable);
        return ResponseEntity.ok(responseDTOS);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientUpdateRequestDTO data) {
        ClientResponseDTO responseDTO = clientService.update(id, data);
        return ResponseEntity.ok(responseDTO);
    }
}
