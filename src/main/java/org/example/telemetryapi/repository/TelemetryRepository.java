package org.example.telemetryapi.repository;

import org.example.telemetryapi.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
    List<Telemetry> findTop100ByOrderByCapturedAtDesc();
}
