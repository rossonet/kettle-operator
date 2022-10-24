FROM dorowu/ubuntu-desktop-lxde-vnc:focal
ARG MAINTAINER="Andrea Ambrosini <andrea.ambrosini@rossonet.org>"
RUN echo "deb http://cz.archive.ubuntu.com/ubuntu bionic main universe" >> /etc/apt/sources.list
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y openjdk-11-jre bash gawk sed grep bc coreutils wget binutils unzip libwebkitgtk-1.0-0 && apt-get clean && rm -rf /var/lib/apt/lists/*
COPY spoon.desktop /usr/share/applications/spoon.desktop
COPY spoon.desktop /root/Desktop/spoon.desktop
COPY bg1.jpg /usr/local/share/doro-lxde-wallpapers/bg1.jpg
COPY bg2.jpg /usr/local/share/doro-lxde-wallpapers/bg2.jpg
COPY bg3.jpg /usr/local/share/doro-lxde-wallpapers/bg3.jpg
COPY bg4.jpg /usr/local/share/doro-lxde-wallpapers/bg4.jpg
WORKDIR /
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/pdi-ce-9.3.0.0-428.zip -O pdi.zip && \
unzip pdi.zip && \
rm -f pdi.zip
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/mariadb-java-client-2.7.3.jar -O /data-integration/drivers/mariadb-java-client-2.7.3.jar 
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/mssql-jdbc-10.2.1.jre11.jar -O /data-integration/drivers/mssql-jdbc-10.2.1.jre11.jar
RUN wget --no-check-certificate https://www.rossonet.net/dati/pdi/drivers/postgresql-42.4.0.jar -O /data-integration/drivers/postgresql-42.4.0.jar
WORKDIR /data-integration
ENV PATH=${PATH}:/data-integration
