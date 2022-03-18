FROM cassandra:3.11.12

EXPOSE 9042 9160 7199 1414

RUN set -eux; \
	apt-get update; \
    apt-get install -y wget

ARG ECAUDIT_VERSION="2.9.0"
ENV ECAUDIT_VERSION="$ECAUDIT_VERSION"

# download ecaudit
RUN wget -P /opt/cassandra/lib/ --no-verbose -O /opt/cassandra/lib/ecaudit-${ECAUDIT_VERSION}.jar https://repo1.maven.org/maven2/com/ericsson/bss/cassandra/ecaudit/ecaudit_c3.11/${ECAUDIT_VERSION}/ecaudit_c3.11-${ECAUDIT_VERSION}.jar

COPY --chown=cassandra:cassandra files/* /etc/cassandra/

