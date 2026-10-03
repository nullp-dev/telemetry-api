FROM grafana/grafana:13.2.2

ENV GF_AUTH_ANONYMOUS_ENABLED=true
ENV GF_AUTH_ANONYMOUS_ORG_ROLE=Admin

COPY provisioning /etc/grafana/provisioning

EXPOSE 3000
