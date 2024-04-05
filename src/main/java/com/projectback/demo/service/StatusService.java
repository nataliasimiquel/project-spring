package com.projectback.demo.service;

import com.projectback.demo.client.ClientComponent;
import com.projectback.demo.entity.StatusEntity;
import com.projectback.demo.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    private final ClientComponent clientComponent;

    public void salvar(StatusEntity statusEntity) {
        statusRepository.save(statusEntity);
    }
    @Scheduled(fixedRate = 300000)
    public void saveEstadosStatus(){
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
                statusEntity.setData(LocalDateTime.now());
                this.salvar(statusEntity);
            }
        }
    }
    public List<StatusEntity> getStatusPorEstado(String estado) {
        return statusRepository.findByEstado(estado);
    }

    public List<StatusEntity> getStatusPorData(LocalDateTime data) {
        return statusRepository.findByData(data);
    }
    public String estadoComMaisAlertasENegativos() {
        List<StatusEntity> allStatus = statusRepository.findAll();

        Map<String, Long> countByEstado = allStatus.stream()
                .filter(status -> status.getStatus().equals("alerta") || status.getStatus().equals("negativo"))
                .collect(Collectors.groupingBy(StatusEntity::getEstado, Collectors.counting()));

        String estadoMaisComAlertaENegativo = countByEstado.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum estado encontrado");

        return estadoMaisComAlertaENegativo;
    }
}
