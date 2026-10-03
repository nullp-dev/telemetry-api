FROM prom/prometheus:v3.6.0

USER root

WORKDIR /etc/prometheus
COPY prometheus.yml.template /etc/prometheus/prometheus.yml.template

RUN echo '#!/bin/sh' > /entrypoint.sh && \
    echo 'sed -e "s|\${API_TARGET}|$API_TARGET|g" \' >> /entrypoint.sh && \
    echo '    -e "s|\${EXPORTER_TARGET}|$EXPORTER_TARGET|g" \' >> /entrypoint.sh && \
    echo '    /etc/prometheus/prometheus.yml.template > /etc/prometheus/prometheus.yml' >> /entrypoint.sh && \
    echo 'exec /bin/prometheus --config.file=/etc/prometheus/prometheus.yml --storage.tsdb.path=/prometheus --web.console.libraries=/usr/share/prometheus/console_libraries --web.console.templates=/usr/share/prometheus/consoles' >> /entrypoint.sh && \
    chmod +x /entrypoint.sh

USER nobody

ENTRYPOINT ["/entrypoint.sh"]
