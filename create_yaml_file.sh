#!/bin/bash
./gradlew compileJava
cat kubernetes/template_yaml > kubernetes/operator.yaml
for file in $(ls build/classes/java/main/META-INF/fabric8/*-v1.yml)
do
	cat $file >> kubernetes/operator.yaml
	cat $file >> src/main/resources/crd/kettle-operator-crd.yaml
	echo "---" >> kubernetes/operator.yaml
	echo "---" >> src/main/resources/crd/kettle-operator-crd.yaml
done
