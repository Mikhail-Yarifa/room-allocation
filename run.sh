#!/usr/bin/env sh

# This file will be included as a Docker ENTRYPOINT in our automated testing evironment. 

./gradlew build

# Change into the build libs directory
cd build/libs

# Start the application
java -jar room-allocation-0.0.1-SNAPSHOT.jar

