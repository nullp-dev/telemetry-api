#!/bin/bash

API_URL="http://localhost:8080/v1/telemetry"
API_KEY="local-dev-key-123"

echo "Starting continuous telemetry agent. Press [CTRL+C] to stop."

while true; do
    HOSTNAME=$(hostname)
    CPU_IDLE=$(top -bn1 | grep "Cpu(s)" | sed "s/.*, *\([0-9.]*\)%* id.*/\1/")
    CPU_USAGE=$(awk "BEGIN {print 100 - $CPU_IDLE}")
    MEM_TOTAL=$(free -m | awk '/^Mem:/{print $2}')
    MEM_USED=$(free -m | awk '/^Mem:/{print $3}')
    DISK_TOTAL=$(df -BG / | awk 'NR==2 {print $2}' | sed 's/G//')
    DISK_USED=$(df -BG / | awk 'NR==2 {print $3}' | sed 's/G//')
    UPTIME=$(cat /proc/uptime | awk '{print $1}' | cut -d. -f1)

    PAYLOAD=$(cat <<EOF
{
  "hostname": "$HOSTNAME",
  "cpuUsagePercentage": $CPU_USAGE,
  "memoryTotalMb": $MEM_TOTAL,
  "memoryUsedMb": $MEM_USED,
  "diskTotalGb": $DISK_TOTAL,
  "diskUsedGb": $DISK_USED,
  "systemUptimeSeconds": $UPTIME
}
EOF
    )

    HTTP_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$API_URL" \
      -H "Content-Type: application/json" \
      -H "X-API-KEY: $API_KEY" \
      -d "$PAYLOAD")

    if [ "$HTTP_RESPONSE" -eq 201 ]; then
        echo "$(date -u +"%Y-%m-%dT%H:%M:%SZ") - INFO - Telemetry data successfully sent. HTTP Status: 201"
    else
        echo "$(date -u +"%Y-%m-%dT%H:%M:%SZ") - ERROR - Failed to send telemetry data. HTTP Status: $HTTP_RESPONSE"
    fi

done