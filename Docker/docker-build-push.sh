#!/usr/bin/env bash

# Usage (from root of project): sh ./Docker/docker-build-push.sh

docker build -f Docker/Dockerfile --no-cache -t anhlh/ticbus:0.4.5 .
docker push anhlh/ticbus:0.4.5

