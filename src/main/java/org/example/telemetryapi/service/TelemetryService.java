package org.example.telemetryapi.service;

import lombok.RequiredArgsConstructor;
import org.example.telemetryapi.dto.TelemetryPayload;
import org.example.telemetryapi.entity.Telemetry;
import org.example.telemetryapi.repository.TelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryService {
    private static final Logger log = LoggerFactory.getLogger(TelemetryService.class);
        private final TelemetryRepository telemetryRepository;

        @Transactional
        public void processTelemetry(TelemetryPayload payload) {
            log.debug("Processing incoming telemetry payload for hostname: {}", payload.hostname());

            Telemetry telemetry = Telemetry.builder()
                    .hostname(payload.hostname())
                    .cpuUsagePercentage(payload.cpuUsagePercentage())
                    .memoryTotalMb(payload.memoryTotalMb())
                    .memoryUsedMb(payload.memoryUsedMb())
                    .diskTotalGb(payload.diskTotalGb())
                    .diskUsedGb(payload.diskUsedGb())
                    .systemUptimeSeconds(payload.systemUptimeSeconds())
                    .build();

            telemetryRepository.save(telemetry);
            log.debug("Telemetry data successfully persisted for hostname: {}", payload.hostname());
        }

        @Transactional(readOnly = true)
        public List<Telemetry> getLatestTelemetry() {
            log.debug("Fetching the latest 100 telemetry records");
            return telemetryRepository.findTop100ByOrderByCapturedAtDesc();
        }

}