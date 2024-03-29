FROM ubuntu:20.04 as ar4k-builder
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y openjdk-11-jdk
COPY . /ar4kAgent
WORKDIR /ar4kAgent
RUN chmod +x gradlew
RUN ./gradlew clean shadowJar

FROM ubuntu:20.04
ARG MAINTAINER="Andrea Ambrosini <andrea.ambrosini@rossonet.org>"
RUN apt update && DEBIAN_FRONTEND=noninteractive apt install -y openjdk-11-jre bash gawk sed grep bc coreutils wget binutils nmap && apt-get clean && rm -rf /var/lib/apt/lists/*
ENTRYPOINT ["java"]
CMD ["-XX:+UnlockExperimentalVMOptions","-Djava.net.preferIPv4Stack=true","-XshowSettings:vm","-Djava.security.egd=file:/dev/./urandom","-jar","/operator.jar"]
COPY --from=ar4k-builder /ar4kAgent/build/libs/*-all.jar /operator.jar
