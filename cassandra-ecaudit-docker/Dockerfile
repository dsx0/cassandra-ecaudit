FROM cassandra:3.11.12

ARG ECAUDIT_CASSANDRA_VERSION="ecaudit_c3.11"
ARG ECAUDIT_LIB_VERSION="2.9.1"

EXPOSE 9042 9160 7199 1414

RUN set -eux; \
	apt-get update; \
    apt-get install -y wget

# download ecaudit
RUN wget -P /opt/cassandra/lib/ --no-verbose -O /opt/cassandra/lib/ecaudit.jar https://repo1.maven.org/maven2/com/ericsson/bss/cassandra/ecaudit/${ECAUDIT_CASSANDRA_VERSION}/${ECAUDIT_LIB_VERSION}/${ECAUDIT_CASSANDRA_VERSION}-${ECAUDIT_LIB_VERSION}.jar

COPY --chown=cassandra:cassandra files/* /etc/cassandra/

