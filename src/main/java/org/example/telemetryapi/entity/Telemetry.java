package org.example.telemetryapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Telemetry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hostname", nullable = false, length = 100)
    private String hostname;

    @Column(name = "cpu_usage_percentage", nullable = false)
    private Double cpuUsagePercentage;

    @Column(name = "memory_total_mb", nullable = false)
    private Double memoryTotalMb;

    @Column(name = "memory_used_mb", nullable = false)
    private Double memoryUsedMb;

    @Column(name = "disk_total_gb", nullable = false)
    private Double diskTotalGb;

    @Column(name = "disk_used_gb", nullable = false)
    private Double diskUsedGb;

    @Column(name = "system_uptime_seconds", nullable = false)
    private Long systemUptimeSeconds;

    @Column(name = "captured_at", nullable = false, updatable = false)
    private LocalDateTime capturedAt;

    @PrePersist
    protected void onCreate() {
        if (this.capturedAt == null) {
            this.capturedAt = LocalDateTime.now();
        }
    }

}
