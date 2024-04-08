package com.projectback.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectback.demo.client.ClientComponent;
import com.projectback.demo.entity.StatusEntity;
import com.projectback.demo.service.StatusService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
    @AllArgsConstructor
    @RequestMapping(value="/v1/api")
    public class StatusController {

	private final ClientComponent clientComponent;

	private final StatusService statusService;
    @GetMapping("/status")
    public String getStatus() {
	String values = clientComponent.getValues();
	if (values != null) {
	    try {
		JSONArray jsonArray = new JSONArray();
		String[] lines = values.split(System.lineSeparator());
		for (String line : lines) {
		    String[] parts = line.split(" Status: ");
		    JSONObject jsonObject = new JSONObject();
		    jsonObject.put("estado", parts[0].split("Estado: ")[1]);
		    jsonObject.put("status", parts[1]);
		    jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return null;
    }
        @GetMapping("/{estado}")
        public List<StatusEntity> getStatusPorEstado(@PathVariable String estado) {
	    return statusService.getStatusPorEstado(estado);
        }
    @GetMapping("/data")
    public List<StatusEntity> getStatusPorData(
	    @RequestParam(value = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
	LocalDateTime startDateTime = data.atStartOfDay();
	LocalDateTime endDateTime = data.atTime(LocalTime.MAX);
	return statusService.getStatusPorData(startDateTime, endDateTime);
    }
    @GetMapping("/most-alerts-and-negatives")
    public ResponseEntity<String> getEstadoComMaisAlertasENegativos() {
	Object objetoRetornado = statusService.estadoComMaisAlertasENegativos();
	String jsonString = "";

	try {
	    ObjectMapper objectMapper = new ObjectMapper();
	    jsonString = objectMapper.writeValueAsString(objetoRetornado);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	return ResponseEntity.ok(jsonString);
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


