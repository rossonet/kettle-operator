FROM postgres:14
ARG MAINTAINER="Andrea Ambrosini <andrea.ambrosini@rossonet.org>"
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y wget git s3cmd && apt-get clean && rm -rf /var/lib/apt/lists/*
