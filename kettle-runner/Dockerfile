FROM ubuntu:20.04
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y openjdk-11-jre bash gawk sed grep bc coreutils wget binutils unzip && apt-get clean && rm -rf /var/lib/apt/lists/*
WORKDIR /
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/pdi-ce-9.3.0.0-428.zip -O pdi.zip && \
unzip pdi.zip && \
rm -f pdi.zip
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/mariadb-java-client-2.7.3.jar -O /data-integration/drivers/mariadb-java-client-2.7.3.jar 
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/mssql-jdbc-10.2.1.jre11.jar -O /data-integration/drivers/mssql-jdbc-10.2.1.jre11.jar
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/postgresql-42.4.0.jar -O /data-integration/drivers/postgresql-42.4.0.jar
WORKDIR /data-integration
ENV PATH=${PATH}:/data-integration
