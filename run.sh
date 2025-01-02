#!/usr/bin/env sh

# This file will be included as a Docker ENTRYPOINT in our automated testing evironment. 

./gradlew build

#Build docker image
docker image build -t allocation-app:latest .

#Run container
docker run -p 8080:8080 allocation-app:latest

