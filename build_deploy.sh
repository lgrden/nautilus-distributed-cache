#!/bin/bash
mvn clean install
docker build --tag nautilus-simple-microservice:1.0.0 simple-microservice
docker-compose -f docker-compose.yaml --env-file docker.env up -d