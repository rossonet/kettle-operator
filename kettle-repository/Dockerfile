FROM postgres:14
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y wget git s3cmd && apt-get clean && rm -rf /var/lib/apt/lists/*
