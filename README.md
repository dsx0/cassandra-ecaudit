# cassandra-ecaudit

This project is for testing cassandra with [ecaudit-plugin](https://github.com/Ericsson/ecaudit).

It consists of three parts:
- cassandra-ecaudit-docker (docker-image based on [dockerhub-cassandra](https://hub.docker.com/_/cassandra) with [ecaudit-plugin](https://github.com/Ericsson/ecaudit) installed and activated)
- cassandra-ecaudit-docker-local-ecaudit-build (docker-image based on [dockerhub-cassandra](https://hub.docker.com/_/cassandra) with [ecaudit-plugin](https://github.com/Ericsson/ecaudit) from **local folder "ecaudit-builds"**
- cassandra-ecaudit-testclient (a maven-based java-project with a simple testclass acting as cassandra client)


## cassandra-ecaudit-docker
Either build Dockerimage by your own after checkout or use **already published image** on [dockerhub-cassandra-ecaudit-docker](https://hub.docker.com/r/dsx0/cassandra-ecaudit-docker).

### docker commands
```
docker build --tag=cassandra-ecaudit-docker:v3.11.12 .
docker run --name some-cassandra -p 9042:9042 -p 1414:1414 -d cassandra-ecaudit-docker:v3.11.12
```
or
```
docker pull dsx0/cassandra-ecaudit-docker:v3.11.12
docker run --name some-cassandra -p 9042:9042 -p 1414:1414 -d dsx0/cassandra-ecaudit-docker:v3.11.12
```
This will start cassandra(with ecaudit) and opens 2 ports for connecting:
- **9042** (the standard port for java client-connections)
- **1414** (a java remote debugging port. you can debug cassandra and ecaudit with it)

To check **logs**:
```
docker ps -a
docker exec -it <containerhash> /bin/bash
  tail -f /opt/cassandra/logs/system.log
  cat /opt/cassandra/logs/audit/audit.log
```
You can use the containerhash to **stop and start cassandra**:
```
docker stop <containerhash>
docker start <containerhash>
```


## cassandra-ecaudit-docker-local-ecaudit-build
Build Dockerimage by your own after checkout.
### docker commands
```
docker build --build-arg ECAUDIT_JAR_FILE=ecaudit_c3.11-2.10.0-SNAPSHOT-emolsson-issue-193.jar --tag=cassandra-ecaudit-docker-local-ecaudit-build:v3.11.12 .
docker run --name some-cassandra -p 9042:9042 -p 1414:1414 -d cassandra-ecaudit-docker-local-ecaudit-build:v3.11.12
```
To check **logs**:
```
docker ps -a
docker exec -it <containerhash> /bin/bash
  tail -f /opt/cassandra/logs/system.log
  cat /opt/cassandra/logs/audit/audit.log
```
You can use the containerhash to **stop and start cassandra**:
```
docker stop <containerhash>
docker start <containerhash>
```

## cassandra-ecaudit-testclient
Is a simple maven based java-project containing the testclass "**CassandraTestClient**".
Source: [CassandraTestClient](https://github.com/dsx0/cassandra-ecaudit/blob/main/cassandra-ecaudit-testclient/src/main/java/test/CassandraTestClient.java)

- It automatically connects to localhost:9042 using standard user (cassandra:cassandra) and creates a "persons" keyspace (**if not exists**) with testdata
- It executes selects using different types:
  - **Test manual statement** (Simple query)
  - **Test manual PreparedStatement** (Query with PreparedStatement)
  - **Test MappingManager with Mapper** (Using Cassandra MappingManager with a mapper)
  - **Test MappingManager with Accessor** (Using Cassandra MappingManager with an accessor)

