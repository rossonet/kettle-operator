#!/bin/bash
./gradlew compileJava
cat kubernetes/template_yaml > kubernetes/operator.yaml
for file in $(ls build/classes/java/main/META-INF/fabric8/*-v1.yml)
do
	cat $file >> kubernetes/operator.yaml
	echo "---" >> kubernetes/operator.yaml
done
