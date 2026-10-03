package org.example.telemetryapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.telemetryapi.dto.TelemetryPayload;
import org.example.telemetryapi.entity.Telemetry;
import org.example.telemetryapi.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/telemetry")
@RequiredArgsConstructor
public class TelemetryController {
    private static final Logger log = LoggerFactory.getLogger(TelemetryController.class);
    private final TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<Void> receiveTelemetry(@RequestBody TelemetryPayload payload) {
        log.info("Received telemetry data from agent for hostname: {}", payload.hostname());
        telemetryService.processTelemetry(payload);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Telemetry>> getLatestTelemetry() {
        log.info("Received request to fetch latest telemetry records");
        List<Telemetry> records = telemetryService.getLatestTelemetry();
        return ResponseEntity.ok(records);
    }

}
