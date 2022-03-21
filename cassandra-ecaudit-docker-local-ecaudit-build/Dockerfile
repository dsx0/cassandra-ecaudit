FROM cassandra:3.11.12

ARG ECAUDIT_JAR_FILE="BUILDPARAM_ECAUDIT_FILE"

EXPOSE 9042 9160 7199 1414

COPY ecaudit-builds/$ECAUDIT_JAR_FILE /opt/cassandra/lib/

COPY --chown=cassandra:cassandra files/* /etc/cassandra/

