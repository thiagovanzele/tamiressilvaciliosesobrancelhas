package br.com.tscs.controller;

import br.com.tscs.dtos.request.AppointmentRequestDTO;
import br.com.tscs.dtos.request.AppointmentUpdateRequestDTO;
import br.com.tscs.dtos.request.ProcedureRequestDTO;
import br.com.tscs.dtos.response.AppointmentResponseDTO;
import br.com.tscs.model.Appointment;
import br.com.tscs.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        AppointmentResponseDTO responseDTO = appointmentService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/new")
    public ResponseEntity<AppointmentResponseDTO> insert(@RequestBody AppointmentRequestDTO data) {
        Appointment appointment = appointmentService.insert(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();

        AppointmentResponseDTO response = new AppointmentResponseDTO(appointment);
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id, @RequestBody AppointmentUpdateRequestDTO data) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.update(id, data.bookingTime(), data.procedures());
        return ResponseEntity.ok(appointmentResponseDTO);
    }
}
