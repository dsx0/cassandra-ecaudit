# cassandra-ecaudit

This project is for testing cassandra with [ecaudit-plugin](https://github.com/Ericsson/ecaudit).

It consists of two parts:
- cassandra-ecaudit-docker (docker-image based on [dockerhub-cassandra](https://hub.docker.com/_/cassandra) with [ecaudit-plugin](https://github.com/Ericsson/ecaudit) installed and activated)
- cassandra-ecaudit-testclient (a maven-based java-project with a simple testclass acting as cassandra client)


## cassandra-ecaudit-docker
Either build Dockerimage by your own after checkout or use already published image on [dockerhub-cassandra-ecaudit-docker](https://hub.docker.com/r/dsx0/cassandra-ecaudit-docker)


## cassandra-ecaudit-testclient
