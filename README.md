# Pentaho Kettle Operator by Rossonet

## Kubernetes Kettle Operator
[![Test on master branch with Gradle](https://github.com/rossonet/kettle-operator/actions/workflows/test-on-master-with-gradle.yml/badge.svg)](https://github.com/rossonet/kettle-operator/actions/workflows/test-on-master-with-gradle.yml)
[![Build and publish docker image of Operator to DockerHub](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-dockerhub-operator.yml/badge.svg)](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-dockerhub-operator.yml)
[![Build and publish docker images of runner components to DockerHub](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-dockerhub-runners.yml/badge.svg)](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-dockerhub-runners.yml)
[![Build and publish docker image of operator to GitHub Registry](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-github-registry-operator.yml/badge.svg)](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-github-registry-operator.yml)
[![Build and publish docker images of runner components to GitHub Registry](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-github-registry-runners.yml/badge.svg)](https://github.com/rossonet/kettle-operator/actions/workflows/publish-to-github-registry-runners.yml)
[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/rossonet/kettle-operator)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/f3ae38d406804dfc844d94bcff9cc9a7)](https://www.codacy.com/gh/rossonet/kettle-operator/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rossonet/kettle-operator&amp;utm_campaign=Badge_Grade)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](code_of_conduct.md) 

### Install on Kubernetes

```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/operator.yaml
```

### Examples

[Repository kind example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/repository.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/repository.yaml
```

Dump repository datas example
```
REPOSITORY_POD_NAME=<insert the pod name here>
kubectl -n test-namespace exec -ti $REPOSITORY_POD_NAME -- sh -c "pg_dump -U rossonet kettle | gzip > /tmp/kettle.sql.gz" && kubectl -n test-namespace cp $REPOSITORY_POD_NAME:/tmp/kettle.sql.gz ./kubernetes/examples/support_data/kettle.sql.gz
```

[Spoon Kettle IDE kind example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/spoon-ide.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/spoon-ide.yaml
```

[Transformation kind example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/transformation.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/transformation.yaml
```

[Job kind example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/job.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/job.yaml
```

### Framework Java used to create the Kubernetes Operator

[https://javaoperatorsdk.io/](https://javaoperatorsdk.io/)

[examples](https://github.com/java-operator-sdk/java-operator-sdk/tree/main/sample-operators)


![alt text](https://app.rossonet.net/wp-content/uploads/2021/10/rossonet-logo_280_115.png "Rossonet")

[https://www.rossonet.net](https://www.rossonet.net)
