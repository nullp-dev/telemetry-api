package org.example.telemetryapi.dto;

public record TelemetryPayload(
        String hostname,
        Double cpuUsagePercentage,
        Double memoryTotalMb,
        Double memoryUsedMb,
        Double diskTotalGb,
        Double diskUsedGb,
        Long systemUptimeSeconds

) {
}
