package com.projectback.demo.controller;

import com.projectback.demo.client.ClientComponent;
import com.projectback.demo.entity.StatusEntity;
import com.projectback.demo.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
    @AllArgsConstructor
    @RequestMapping(value="/v1/api")
    public class StatusController {

	private final ClientComponent clientComponent;

	private final StatusService statusService;
	@GetMapping("/status")
	public String getStatus() {
	    return clientComponent.getValues();
	}
        @GetMapping("/{estado}")
        public List<StatusEntity> getStatusPorEstado(@PathVariable String estado) {
	    return statusService.getStatusPorEstado(estado);
        }
        @GetMapping("/data")
        public List<StatusEntity> getStatusPorData(
	        @RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
	    return statusService.getStatusPorData(data);
        }
    @GetMapping("/most-alerts-and-negatives")
    public String getEstadoComMaisAlertasENegativos() {
	return statusService.estadoComMaisAlertasENegativos();
    }
        @PostMapping("/create")
            public ResponseEntity<Void> saveStatusFromValues() {
            String values = clientComponent.getValues();
            if (values != null) {
	        String[] estadosStatus = values.split(System.lineSeparator());
	        for (String estadoStatus : estadosStatus) {
	            String[] partes = estadoStatus.split(" Status: ");
	            String estado = partes[0].replace("Estado: ", "");
	            String status = partes[1];

	            StatusEntity statusEntity = new StatusEntity();
	            statusEntity.setEstado(estado);
	            statusEntity.setStatus(status);
	            statusService.salvar(statusEntity);
	        }
	        return ResponseEntity.status(201).build();
            } else {
	        return ResponseEntity.status(500).build();
            }
        }
    }


