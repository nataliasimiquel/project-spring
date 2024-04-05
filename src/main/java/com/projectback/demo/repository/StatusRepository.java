package com.projectback.demo.repository;

import com.projectback.demo.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    List<StatusEntity> findByEstado(String estado);
    List<StatusEntity> findByData(LocalDateTime data);

}
