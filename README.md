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

## Install on Kubernetes

```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/operator.yaml
```

## Examples

### Repository

To create a repository you can use a [configuration file like this](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/repository.yaml):
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/repository.yaml
```

To get the repository status:

```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get kr
NAME                  AGE
repository-example1   45m
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe kr repository-example1
Name:         repository-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         KettleRepository
Metadata:
  Creation Timestamp:  2022-08-02T16:34:16Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:databaseName:
        f:replicas:
        f:repositoryPassword:
        f:repositoryUrl:
        f:repositoryUsername:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T16:34:16Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCode:
        f:totalJobs:
        f:totalTransformations:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T16:34:16Z
  Resource Version:  432790
  UID:               68ee6597-70aa-49dd-8196-c616dbaca77b
Spec:
  Database Name:        kettle
  Replicas:             1
  Repository Password:  password1
  Repository URL:       https://github.com/rossonet/kettle-operator/raw/main/kubernetes/examples/support_data/kettle.sql.gz
  Repository Username:  kettle_user
Status:
  Return Code:            SYNCHRONIZED
  Total Jobs:             0
  Total Transformations:  0
Events:                   <none>
```

If you want dump the repository datas:
```
REPOSITORY_POD_NAME=<insert the pod name here>
DATABASE=<insert database name>
DATABASE_USERNAME=<insert database username>
kubectl -n test-namespace exec -ti $REPOSITORY_POD_NAME -- sh -c "pg_dump -U $DATABASE_USERNAME $DATABASE | gzip > /tmp/$DATABASE.sql.gz" && kubectl -n test-namespace cp $REPOSITORY_POD_NAME:/tmp/$DATABASE.sql.gz ./kubernetes/examples/support_data/$DATABASE.sql.gz
```

### Spoon IDE

You can create a Spoon IDE to edit the repository datas. [Use this example file](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/spoon-ide.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/spoon-ide.yaml
```

You can access the IDE with a Browser
![NOVNC on Spoon IDE](https://github.com/rossonet/kettle-operator/raw/main/artwork/screen/spoon-ide.png)

To get the IDE status:
```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get ki
NAME           AGE
ide-example1   7m9s
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe ki ide-example1
Name:         ide-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         KettleIde
Metadata:
  Creation Timestamp:  2022-08-02T17:27:30Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:path:
        f:servicePort:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T17:27:30Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCode:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T17:27:31Z
  Resource Version:  435232
  UID:               06571d2b-33cb-47ac-b03a-a3e75c81efc5
Spec:
  Path:          /ide1
  Service Port:  443
Status:
  Return Code:  INIT
Events:         <none>
```

### Transformation

[Transformation example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/transformation.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/transformation.yaml
```

```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get kt
NAME                     AGE
trasformation-example1   35s


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe kt trasformation-example1
Name:         trasformation-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         KettleTransformation
Metadata:
  Creation Timestamp:  2022-08-02T17:42:15Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:dir:
        f:level:
        f:pass:
        f:rep:
        f:trans:
        f:user:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T17:42:15Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCodeDescription:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T17:42:15Z
  Resource Version:  436041
  UID:               4c4bb405-0681-4278-a5eb-215140d284d0
Spec:
  Dir:    /examples
  Level:  Debug
  Pass:   admin
  Rep:    Repository repository-example1
  Trans:  transformation_example
  User:   admin
Status:
  Return Code Description:  INIT
Events:                     <none>


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe po trasformation-example1-ch92z 
Name:         trasformation-example1-ch92z
Namespace:    test-namespace
Priority:     0
Node:         minikube/192.168.39.147
Start Time:   Tue, 02 Aug 2022 19:42:15 +0200
Labels:       app=trasformation-example1
              app.kubernetes.io/managed-by=kettle-operator
              app.kubernetes.io/part-of=trasformation-example1
              controller-uid=b0778400-088a-4603-8f81-68a87818fcf7
              job-name=trasformation-example1
Annotations:  <none>
Status:       Succeeded
IP:           172.17.0.8
IPs:
  IP:           172.17.0.8
Controlled By:  Job/trasformation-example1
Containers:
  trasformation-example1:
    Container ID:  docker://f7681596981af671968508db1d59f6f42eda55b83bb71feadcf855cb8146b844
    Image:         rossonet/kettle-runner:latest
    Image ID:      docker-pullable://rossonet/kettle-runner@sha256:b55d0e314aecaa213992af2b498d19f804b7449416150cc4e28ac5a1bf299f8f
    Port:          <none>
    Host Port:     <none>
    Command:
      /bin/sh
      -c
    Args:
      mkdir -p /root/.kettle ; cp /data/repositories.xml /root/.kettle/repositories.xml ; /data-integration/pan.sh -dir='/examples' -level='Debug' -pass='admin' -rep='Repository repository-example1' -trans='transformation_example' -user='admin'
    State:          Terminated
      Reason:       Completed
      Exit Code:    0
      Started:      Tue, 02 Aug 2022 19:42:18 +0200
      Finished:     Tue, 02 Aug 2022 19:42:45 +0200
    Ready:          False
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /data from repositories-config (rw)
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-dv279 (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             False 
  ContainersReady   False 
  PodScheduled      True 
Volumes:
  repositories-config:
    Type:      ConfigMap (a volume populated by a ConfigMap)
    Name:      trasformation-example1
    Optional:  false
  kube-api-access-dv279:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age   From               Message
  ----    ------     ----  ----               -------
  Normal  Scheduled  69s   default-scheduler  Successfully assigned test-namespace/trasformation-example1-ch92z to minikube
  Normal  Pulling    68s   kubelet            Pulling image "rossonet/kettle-runner:latest"
  Normal  Pulled     66s   kubelet            Successfully pulled image "rossonet/kettle-runner:latest" in 1.484237666s
  Normal  Created    66s   kubelet            Created container trasformation-example1
  Normal  Started    66s   kubelet            Started container trasformation-example1


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace logs trasformation-example1-ch92z 
#######################################################################
WARNING:  no libwebkitgtk-1.0 detected, some features will be unavailable
    Consider installing the package with apt-get or yum.
    e.g. 'sudo apt-get install libwebkitgtk-1.0-0'
#######################################################################
17:42:20,832 INFO  [KarafBoot] Checking to see if org.pentaho.clean.karaf.cache is enabled
17:42:20,886 INFO  [KarafInstance] 
*******************************************************************************
*** Karaf Instance Number: 1 at /data-integration/./system/karaf/caches/pan ***
***   /data-1                                                               ***
*** Karaf Port:8802                                                         ***
*** OSGI Service Port:9051                                                  ***
*******************************************************************************
Aug 02, 2022 5:42:21 PM org.apache.karaf.main.Main launch
INFO: Installing and starting initial bundles
Aug 02, 2022 5:42:21 PM org.apache.karaf.main.Main launch
INFO: All initial bundles installed and set to start
Aug 02, 2022 5:42:21 PM org.apache.karaf.main.Main$KarafLockCallback lockAcquired
INFO: Lock acquired. Setting startlevel to 100
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.logging} from /data-integration/system/karaf/etc/org.ops4j.pax.logging.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.features.repos} from /data-integration/system/karaf/etc/org.apache.karaf.features.repos.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.management} from /data-integration/system/karaf/etc/org.apache.karaf.management.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {mondrian} from /data-integration/system/karaf/etc/mondrian.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.jaas} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.jaas.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.kar} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.kar.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.log} from /data-integration/system/karaf/etc/org.apache.karaf.log.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.web} from /data-integration/system/karaf/etc/org.ops4j.pax.web.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.system} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.system.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.shell} from /data-integration/system/karaf/etc/org.apache.karaf.shell.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {profile} from /data-integration/system/karaf/etc/profile.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.bundle} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.bundle.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.features} from /data-integration/system/karaf/etc/org.apache.karaf.features.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.shim} from /data-integration/system/karaf/etc/pentaho.shim.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.feature} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.feature.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.config} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.config.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.felix.fileinstall~deploy} from /data-integration/system/karaf/etc/org.apache.felix.fileinstall-deploy.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {jmx.acl.org.apache.karaf.config} from /data-integration/system/karaf/etc/jmx.acl.org.apache.karaf.config.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.url.mvn} from /data-integration/system/karaf/etc/org.ops4j.pax.url.mvn.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.kar} from /data-integration/system/karaf/etc/org.apache.karaf.kar.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.caching~default} from /data-integration/system/karaf/etc/org.pentaho.caching-default.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.jaas} from /data-integration/system/karaf/etc/org.apache.karaf.jaas.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.scope_bundle} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.scope_bundle.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.aries.rsa.provider.fastbin} from /data-integration/system/karaf/etc/org.apache.aries.rsa.provider.fastbin.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.pdi.engine.spark} from /data-integration/system/karaf/etc/org.pentaho.pdi.engine.spark.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.transx.tm.geronimo} from /data-integration/system/karaf/etc/org.ops4j.pax.transx.tm.geronimo.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.shell} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.shell.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {jmx.acl.org.apache.karaf.bundle} from /data-integration/system/karaf/etc/jmx.acl.org.apache.karaf.bundle.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.felix.eventadmin.impl.EventAdmin} from /data-integration/system/karaf/etc/org.apache.felix.eventadmin.impl.EventAdmin.cfg
Aug 02, 2022 5:42:21 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.features} from /data-integration/system/karaf/etc-pan/org.pentaho.features.cfg
Aug 02, 2022 5:42:24 PM org.pentaho.caching.impl.PentahoCacheManagerFactory$RegistrationHandler$1 onSuccess
INFO: New Caching Service registered
Aug 02, 2022 5:42:26 PM org.pentaho.caching.impl.PentahoCacheManagerFactory$RegistrationHandler$1 onSuccess
INFO: New Caching Service registered
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.http.Http1FieldPreEncoder of service org.eclipse.jetty.http.HttpFieldPreEncoder in bundle org.eclipse.jetty.http
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.security.jaspi.JaspiAuthenticatorFactory of service org.eclipse.jetty.security.Authenticator$Factory in bundle org.eclipse.jetty.security.jaspi
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.identity.IdentityExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.fragment.FragmentExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.PerMessageDeflateExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.DeflateFrameExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.XWebkitDeflateFrameExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.JettyClientContainerProvider of service javax.websocket.ContainerProvider in bundle org.eclipse.jetty.websocket.javax.websocket
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.server.NativeWebSocketServletContainerInitializer of service javax.servlet.ServletContainerInitializer in bundle org.eclipse.jetty.websocket.server
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer of service javax.servlet.ServletContainerInitializer in bundle org.eclipse.jetty.websocket.javax.websocket.server
Aug 02, 2022 5:42:28 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.server.ContainerDefaultConfigurator of service javax.websocket.server.ServerEndpointConfig$Configurator in bundle org.eclipse.jetty.websocket.javax.websocket.server
2022-08-02 17:42:28.602:INFO::features-3-thread-1: Logging initialized @9775ms to org.eclipse.jetty.util.log.StdErrLog
Aug 02, 2022 5:42:29 PM org.apache.cxf.transport.http.osgi.ServletExporter updated
INFO: Registering new instance of "/cxf" servlet
Aug 02, 2022 5:42:29 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-management (128) [org.apache.cxf.management.InstrumentationManager]
Aug 02, 2022 5:42:29 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-rs-service-description (133) [org.apache.cxf.jaxrs.model.wadl.WadlGenerator]
Aug 02, 2022 5:42:29 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-transports-http (135) [org.apache.cxf.transport.http.HTTPTransportFactory, org.apache.cxf.transport.http.HTTPWSDLExtensionLoader, org.apache.cxf.transport.http.policy.HTTPClientAssertionBuilder, org.apache.cxf.transport.http.policy.HTTPServerAssertionBuilder, org.apache.cxf.transport.http.policy.NoOpPolicyInterceptorProvider]
Aug 02, 2022 5:42:29 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.big.data.impl.cluster} from /data-integration/system/karaf/etc/pentaho.big.data.impl.cluster.cfg
Aug 02, 2022 5:42:29 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be /i18n
2022-08-02 17:42:29.943:INFO:oejws.WebSocketServerFactory:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:30.091:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): DefaultSessionIdManager workerName=node0
2022-08-02 17:42:30.092:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): No SessionScavenger set, using defaults
2022-08-02 17:42:30.094:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): node0 Scavenging every 660000ms
2022-08-02 17:42:30.166:INFO:oejsh.ContextHandler:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=org.apache.cxf.cxf-rt-transports-http [135], contextID=default]}
2022-08-02 17:42:30.194:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:30.202:INFO:oejs.Server:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): jetty-9.4.43.v20210629; built: 2021-06-30T11:07:22.254Z; git: 526006ecfa3af7f1a27ef3a288e2bef7ea9dd7e8; jvm 11.0.15+10-Ubuntu-0ubuntu0.20.04.1
2022-08-02 17:42:30.203:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-i18n-webservice-bundle [220], contextID=default]}
2022-08-02 17:42:30.233:INFO:oejs.AbstractConnector:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): Started default@244f9d23{HTTP/1.1, (http/1.1)}{0.0.0.0:9051}
2022-08-02 17:42:30.233:INFO:oejs.Server:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): Started @11406ms
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.metaverse} from /data-integration/system/karaf/etc/pentaho.metaverse.cfg
Aug 02, 2022 5:42:36 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.geo.roles} from /data-integration/system/karaf/etc/pentaho.geo.roles.cfg
2022-08-02 17:42:37.699:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:37.719:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-sanitize [255], contextID=default]}
2022-08-02 17:42:37.876:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:37.882:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=org.pentaho.requirejs-manager-impl [250], contextID=default]}
2022-08-02 17:42:37.900:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:37.902:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-route [254], contextID=default]}
2022-08-02 17:42:37.938:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:37.941:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-animate [253], contextID=default]}
Aug 02, 2022 5:42:38 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be /marketplace
Aug 02, 2022 5:42:38 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.marketplace.di} from /data-integration/system/karaf/etc/pentaho.marketplace.di.cfg
2022-08-02 17:42:38.912:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:38.924:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-ui-bootstrap-bower [257], contextID=default]}
2022-08-02 17:42:39.362:INFO:oejws.WebSocketServerFactory:FelixDispatchQueue: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:39.370:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:39.377:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-jquery [258], contextID=default]}
2022-08-02 17:42:39.391:INFO:oejsh.ContextHandler:FelixDispatchQueue: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-marketplace-di [251], contextID=default]}
2022-08-02 17:42:39.418:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:39.426:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-translate [256], contextID=default]}
2022-08-02 17:42:39.462:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:39.466:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular [252], contextID=default]}
2022-08-02 17:42:39.557:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:39.559:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-underscore [259], contextID=default]}
Aug 02, 2022 5:42:39 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be /marketplace
17:42:42,713 INFO  [DriverManager] Installing driver kars.
17:42:42,715 INFO  [DriverManager] 0 drivers will be installed.
17:42:42,716 INFO  [DriverManager] Finished installing drivers kars.
2022/08/02 17:42:43 - Pan - Logging is at level : Debug
Arguments:
rep          : Repository repository-example1
user         : admin
trustuser    : 
pass         : admin
trans        : transformation_example
dir          : /examples
file         : 
level        : Debug
logfile      : 
log          : 
listdir      : 
listtrans    : 
listrep      : 
exprep       : 
norep        : 
safemode     : 
version      : 
jarfile      : 
param        : null
listparam    : 
initialDir   : /data-integration/
stepname     : 
copynum      : 
zip          : 
uuid         : 
metrics      : 
maxloglines  : 
maxlogtimeou : 

2022/08/02 17:42:43 - Pan - Start of run.
2022/08/02 17:42:43 - Pan - Allocate new transformation.
2022/08/02 17:42:43 - Pan - Starting to look at options...
2022/08/02 17:42:43 - Pan - Parsing command line options.
2022/08/02 17:42:43 - Pan - Loading available repositories.
2022/08/02 17:42:43 - RepositoriesMeta - No repositories file found in the local directory: /data-integration/repositories.xml
2022/08/02 17:42:43 - RepositoriesMeta - Reading repositories XML file: /root/.kettle/repositories.xml
2022/08/02 17:42:43 - RepositoriesMeta - We have 1 connections...
2022/08/02 17:42:43 - RepositoriesMeta - Looking at connection #0
2022/08/02 17:42:43 - RepositoriesMeta - Read at connection: repository-example1
2022/08/02 17:42:43 - RepositoriesMeta - We have 1 repositories...
2022/08/02 17:42:43 - RepositoriesMeta - Looking at repository #0
2022/08/02 17:42:43 - RepositoriesMeta - Read at repository: Repository repository-example1
2022/08/02 17:42:43 - Pan - Finding repository [Repository repository-example1]
2022/08/02 17:42:43 - Pan - Check supplied username and password.
2022/08/02 17:42:43 - Pan - Allocate & connect to repository.
2022/08/02 17:42:43 - Pan - Load the transformation info...
2022/08/02 17:42:43 - Repository repository-example1 - Looking for the transformation [transformation_example] in directory [/examples]
2022/08/02 17:42:43 - Repository repository-example1 - Loading transformation [transformation_example] from repository...
2022/08/02 17:42:43 - Repository repository-example1 - Loading step with ID: 1
2022/08/02 17:42:43 - Repository repository-example1 - Loading step with ID: 2
2022/08/02 17:42:43 - Repository repository-example1 - Loading step with ID: 3
2022/08/02 17:42:44 - Repository repository-example1 - Loading step with ID: 4
2022/08/02 17:42:44 - Repository repository-example1 - ID_DIRECTORY=1
2022/08/02 17:42:44 - Repository repository-example1 - Loaded the transformation [transformation_example] , directory == null : false
2022/08/02 17:42:44 - Repository repository-example1 - Loaded the transformation [transformation_example] in directory /examples
2022/08/02 17:42:44 - Pan - Allocate transformation...
2022/08/02 17:42:44 - transformation_example - Dispatching started for transformation [transformation_example]
2022/08/02 17:42:44 - transformation_example - Nr of arguments detected:0 
2022/08/02 17:42:44 - transformation_example - This is not a replay transformation
2022/08/02 17:42:44 - transformation_example - I found 4 different steps to launch.
2022/08/02 17:42:44 - transformation_example - Allocating rowsets...
2022/08/02 17:42:44 - transformation_example -  Allocating rowsets for step 0 --> Generate 100 rows
2022/08/02 17:42:44 - transformation_example -   prevcopies = 1, nextcopies=1
2022/08/02 17:42:44 - transformation_example - Transformation allocated new rowset [Generate 100 rows.0 - Secret key generator.0]
2022/08/02 17:42:44 - transformation_example -  Allocated 1 rowsets for step 0 --> Generate 100 rows  
2022/08/02 17:42:44 - transformation_example -  Allocating rowsets for step 1 --> Secret key generator
2022/08/02 17:42:44 - transformation_example -   prevcopies = 1, nextcopies=1
2022/08/02 17:42:44 - transformation_example - Transformation allocated new rowset [Secret key generator.0 - Add a checksum.0]
2022/08/02 17:42:44 - transformation_example -  Allocated 2 rowsets for step 1 --> Secret key generator  
2022/08/02 17:42:44 - transformation_example -  Allocating rowsets for step 2 --> Add a checksum
2022/08/02 17:42:44 - transformation_example -   prevcopies = 1, nextcopies=1
2022/08/02 17:42:44 - transformation_example - Transformation allocated new rowset [Add a checksum.0 - XML output.0]
2022/08/02 17:42:44 - transformation_example -  Allocated 3 rowsets for step 2 --> Add a checksum  
2022/08/02 17:42:44 - transformation_example -  Allocating rowsets for step 3 --> XML output
2022/08/02 17:42:44 - transformation_example -  Allocated 3 rowsets for step 3 --> XML output  
2022/08/02 17:42:44 - transformation_example - Allocating Steps & StepData...
2022/08/02 17:42:44 - transformation_example -  Transformation is about to allocate step [Generate 100 rows] of type [RowGenerator]
2022/08/02 17:42:44 - transformation_example -   Step has nrcopies=1
2022/08/02 17:42:44 - Generate 100 rows.0 - distribution activated
2022/08/02 17:42:44 - Generate 100 rows.0 - Starting allocation of buffers & new threads...
2022/08/02 17:42:44 - Generate 100 rows.0 - Step info: nrinput=0 nroutput=1
2022/08/02 17:42:44 - Generate 100 rows.0 - output rel. is  1:1
2022/08/02 17:42:44 - Generate 100 rows.0 - Found output rowset [Generate 100 rows.0 - Secret key generator.0]
2022/08/02 17:42:44 - Generate 100 rows.0 - Finished dispatching
2022/08/02 17:42:44 - transformation_example -  Transformation has allocated a new step: [Generate 100 rows].0
2022/08/02 17:42:44 - transformation_example -  Transformation is about to allocate step [Secret key generator] of type [SecretKeyGenerator]
2022/08/02 17:42:44 - transformation_example -   Step has nrcopies=1
2022/08/02 17:42:44 - Secret key generator.0 - distribution de-activated
2022/08/02 17:42:44 - Secret key generator.0 - Starting allocation of buffers & new threads...
2022/08/02 17:42:44 - Secret key generator.0 - Step info: nrinput=1 nroutput=1
2022/08/02 17:42:44 - Secret key generator.0 - Got previous step from [Secret key generator] #0 --> Generate 100 rows
2022/08/02 17:42:44 - Secret key generator.0 - input rel is 1:1
2022/08/02 17:42:44 - Secret key generator.0 - Found input rowset [Generate 100 rows.0 - Secret key generator.0]
2022/08/02 17:42:44 - Secret key generator.0 - output rel. is  1:1
2022/08/02 17:42:44 - Secret key generator.0 - Found output rowset [Secret key generator.0 - Add a checksum.0]
2022/08/02 17:42:44 - Secret key generator.0 - Finished dispatching
2022/08/02 17:42:44 - transformation_example -  Transformation has allocated a new step: [Secret key generator].0
2022/08/02 17:42:44 - transformation_example -  Transformation is about to allocate step [Add a checksum] of type [CheckSum]
2022/08/02 17:42:44 - transformation_example -   Step has nrcopies=1
2022/08/02 17:42:44 - Add a checksum.0 - distribution activated
2022/08/02 17:42:44 - Add a checksum.0 - Starting allocation of buffers & new threads...
2022/08/02 17:42:44 - Add a checksum.0 - Step info: nrinput=1 nroutput=1
2022/08/02 17:42:44 - Add a checksum.0 - Got previous step from [Add a checksum] #0 --> Secret key generator
2022/08/02 17:42:44 - Add a checksum.0 - input rel is 1:1
2022/08/02 17:42:44 - Add a checksum.0 - Found input rowset [Secret key generator.0 - Add a checksum.0]
2022/08/02 17:42:44 - Add a checksum.0 - output rel. is  1:1
2022/08/02 17:42:44 - Add a checksum.0 - Found output rowset [Add a checksum.0 - XML output.0]
2022/08/02 17:42:44 - Add a checksum.0 - Finished dispatching
2022/08/02 17:42:44 - transformation_example -  Transformation has allocated a new step: [Add a checksum].0
2022/08/02 17:42:44 - transformation_example -  Transformation is about to allocate step [XML output] of type [XMLOutput]
2022/08/02 17:42:44 - transformation_example -   Step has nrcopies=1
2022/08/02 17:42:44 - XML output.0 - distribution activated
2022/08/02 17:42:44 - XML output.0 - Starting allocation of buffers & new threads...
2022/08/02 17:42:44 - XML output.0 - Step info: nrinput=1 nroutput=0
2022/08/02 17:42:44 - XML output.0 - Got previous step from [XML output] #0 --> Add a checksum
2022/08/02 17:42:44 - XML output.0 - input rel is 1:1
2022/08/02 17:42:44 - XML output.0 - Found input rowset [Add a checksum.0 - XML output.0]
2022/08/02 17:42:44 - XML output.0 - Finished dispatching
2022/08/02 17:42:44 - transformation_example -  Transformation has allocated a new step: [XML output].0
2022/08/02 17:42:44 - transformation_example - This transformation can be replayed with replay date: 2022/08/02 17:42:44
2022/08/02 17:42:44 - transformation_example - Initialising 4 steps...
2022/08/02 17:42:44 - Generate 100 rows.0 - Released server socket on port 0
2022/08/02 17:42:44 - Secret key generator.0 - Released server socket on port 0
2022/08/02 17:42:44 - Add a checksum.0 - Released server socket on port 0
2022/08/02 17:42:44 - XML output.0 - Released server socket on port 0
2022/08/02 17:42:44 - XML output.0 - Opening output stream in encoding: UTF-8
2022/08/02 17:42:44 - transformation_example - Step [Generate 100 rows.0] initialized flawlessly.
2022/08/02 17:42:44 - transformation_example - Step [Secret key generator.0] initialized flawlessly.
2022/08/02 17:42:44 - transformation_example - Step [Add a checksum.0] initialized flawlessly.
2022/08/02 17:42:44 - transformation_example - Step [XML output.0] initialized flawlessly.
2022/08/02 17:42:44 - Generate 100 rows.0 - Starting to run...
2022/08/02 17:42:44 - Generate 100 rows.0 - Signaling 'output done' to 1 output rowsets.
2022/08/02 17:42:44 - Generate 100 rows.0 - Finished processing (I=0, O=0, R=0, W=100, U=0, E=0)
2022/08/02 17:42:44 - XML output.0 - Starting to run...
2022/08/02 17:42:44 - transformation_example - Transformation has allocated 4 threads and 3 rowsets.
2022/08/02 17:42:44 - Secret key generator.0 - Starting to run...
2022/08/02 17:42:44 - Add a checksum.0 - Starting to run...
2022/08/02 17:42:44 - Secret key generator.0 - Signaling 'output done' to 1 output rowsets.
2022/08/02 17:42:44 - Secret key generator.0 - Finished processing (I=0, O=0, R=1001, W=1000, U=0, E=0)
2022/08/02 17:42:44 - Add a checksum.0 - Signaling 'output done' to 1 output rowsets.
2022/08/02 17:42:44 - Add a checksum.0 - Finished processing (I=0, O=0, R=1000, W=1000, U=0, E=0)
2022/08/02 17:42:44 - XML output.0 - Signaling 'output done' to 0 output rowsets.
2022/08/02 17:42:44 - XML output.0 - Finished processing (I=0, O=1000, R=1000, W=1000, U=0, E=0)
2022/08/02 17:42:44 - transformation_example - searching for annotations
2022/08/02 17:42:44 - transformation_example - no annotations found
2022/08/02 17:42:44 - Carte - Installing timer to purge stale objects after 1440 minutes.
2022/08/02 17:42:44 - Pan - Finished!
2022/08/02 17:42:44 - Pan - Start=2022/08/02 17:42:44.359, Stop=2022/08/02 17:42:44.802
2022/08/02 17:42:44 - Pan - Processing ended after 0 seconds.
2022/08/02 17:42:44 - transformation_example -  
2022/08/02 17:42:44 - transformation_example - Step Generate 100 rows.0 ended successfully, processed 100 lines. ( - lines/s)
2022/08/02 17:42:44 - transformation_example - Step Secret key generator.0 ended successfully, processed 1001 lines. ( - lines/s)
2022/08/02 17:42:44 - transformation_example - Step Add a checksum.0 ended successfully, processed 1000 lines. ( - lines/s)
2022/08/02 17:42:44 - transformation_example - Step XML output.0 ended successfully, processed 1000 lines. ( - lines/s)
```

### Job

[Job example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/job.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/job.yaml
```

```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get kj
NAME           AGE
job-example1   3m29s


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe kj job-example1
Name:         job-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         KettleJob
Metadata:
  Creation Timestamp:  2022-08-02T17:42:25Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:dir:
        f:job:
        f:level:
        f:pass:
        f:rep:
        f:user:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T17:42:25Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCodeDescription:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T17:42:25Z
  Resource Version:  436064
  UID:               a1edf771-21a4-485f-aa4e-f7e630757011
Spec:
  Dir:    /examples
  Job:    job_example
  Level:  Debug
  Pass:   admin
  Rep:    Repository repository-example1
  User:   admin
Status:
  Return Code Description:  INIT
Events:                     <none>


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe po job-example1-ck7nh 
Name:         job-example1-ck7nh
Namespace:    test-namespace
Priority:     0
Node:         minikube/192.168.39.147
Start Time:   Tue, 02 Aug 2022 19:42:25 +0200
Labels:       app=job-example1
              app.kubernetes.io/managed-by=kettle-operator
              app.kubernetes.io/part-of=job-example1
              controller-uid=8f11b8a2-af29-43dd-a863-2421171fcdb4
              job-name=job-example1
Annotations:  <none>
Status:       Succeeded
IP:           172.17.0.9
IPs:
  IP:           172.17.0.9
Controlled By:  Job/job-example1
Containers:
  job-example1:
    Container ID:  docker://5fbaee04851a9b55d623b394d19085acf1b3e84308de35dc54f33d5e275e0a46
    Image:         rossonet/kettle-runner:latest
    Image ID:      docker-pullable://rossonet/kettle-runner@sha256:b55d0e314aecaa213992af2b498d19f804b7449416150cc4e28ac5a1bf299f8f
    Port:          <none>
    Host Port:     <none>
    Command:
      /bin/sh
      -c
    Args:
      mkdir -p /root/.kettle ; cp /data/repositories.xml /root/.kettle/repositories.xml ; /data-integration/kitchen.sh -dir='/examples' -job='job_example' -level='Debug' -pass='admin' -rep='Repository repository-example1' -user='admin'
    State:          Terminated
      Reason:       Completed
      Exit Code:    0
      Started:      Tue, 02 Aug 2022 19:42:27 +0200
      Finished:     Tue, 02 Aug 2022 19:42:50 +0200
    Ready:          False
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /data from repositories-config (rw)
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-kv879 (ro)
Conditions:
  Type              Status
  Initialized       True 
  Ready             False 
  ContainersReady   False 
  PodScheduled      True 
Volumes:
  repositories-config:
    Type:      ConfigMap (a volume populated by a ConfigMap)
    Name:      job-example1
    Optional:  false
  kube-api-access-kv879:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    ConfigMapOptional:       <nil>
    DownwardAPI:             true
QoS Class:                   BestEffort
Node-Selectors:              <none>
Tolerations:                 node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type    Reason     Age    From               Message
  ----    ------     ----   ----               -------
  Normal  Scheduled  3m55s  default-scheduler  Successfully assigned test-namespace/job-example1-ck7nh to minikube
  Normal  Pulling    3m54s  kubelet            Pulling image "rossonet/kettle-runner:latest"
  Normal  Pulled     3m53s  kubelet            Successfully pulled image "rossonet/kettle-runner:latest" in 1.419323233s
  Normal  Created    3m53s  kubelet            Created container job-example1
  Normal  Started    3m53s  kubelet            Started container job-example1


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace logs job-example1-ck7nh 
#######################################################################
WARNING:  no libwebkitgtk-1.0 detected, some features will be unavailable
    Consider installing the package with apt-get or yum.
    e.g. 'sudo apt-get install libwebkitgtk-1.0-0'
#######################################################################
17:42:31,148 INFO  [KarafBoot] Checking to see if org.pentaho.clean.karaf.cache is enabled
17:42:31,246 INFO  [KarafInstance] 
*******************************************************************************
*** Karaf Instance Number: 1 at /data-integration/./system/karaf/caches/kit ***
***   chen/data-1                                                           ***
*** Karaf Port:8802                                                         ***
*** OSGI Service Port:9051                                                  ***
*******************************************************************************
Aug 02, 2022 5:42:31 PM org.apache.karaf.main.Main launch
INFO: Installing and starting initial bundles
Aug 02, 2022 5:42:31 PM org.apache.karaf.main.Main launch
INFO: All initial bundles installed and set to start
Aug 02, 2022 5:42:32 PM org.apache.karaf.main.Main$KarafLockCallback lockAcquired
INFO: Lock acquired. Setting startlevel to 100
2022/08/02 17:42:32 - Kitchen - Logging is at level : Debug
2022/08/02 17:42:32 - Kitchen - Start of run.
2022/08/02 17:42:32 - Kitchen - Allocate new job.
2022/08/02 17:42:32 - Kitchen - Parsing command line options.
2022/08/02 17:42:32 - Kitchen - Loading available repositories.
2022/08/02 17:42:32 - RepositoriesMeta - No repositories file found in the local directory: /data-integration/repositories.xml
2022/08/02 17:42:32 - RepositoriesMeta - Reading repositories XML file: /root/.kettle/repositories.xml
2022/08/02 17:42:32 - RepositoriesMeta - We have 1 connections...
2022/08/02 17:42:32 - RepositoriesMeta - Looking at connection #0
2022/08/02 17:42:32 - RepositoriesMeta - Read at connection: repository-example1
2022/08/02 17:42:32 - RepositoriesMeta - We have 1 repositories...
2022/08/02 17:42:32 - RepositoriesMeta - Looking at repository #0
2022/08/02 17:42:32 - RepositoriesMeta - Read at repository: Repository repository-example1
2022/08/02 17:42:32 - Kitchen - Finding repository [Repository repository-example1]
2022/08/02 17:42:32 - Kitchen - Check supplied username and password.
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.logging} from /data-integration/system/karaf/etc/org.ops4j.pax.logging.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.features.repos} from /data-integration/system/karaf/etc/org.apache.karaf.features.repos.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.management} from /data-integration/system/karaf/etc/org.apache.karaf.management.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {mondrian} from /data-integration/system/karaf/etc/mondrian.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.jaas} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.jaas.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.kar} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.kar.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.log} from /data-integration/system/karaf/etc/org.apache.karaf.log.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.web} from /data-integration/system/karaf/etc/org.ops4j.pax.web.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.system} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.system.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.shell} from /data-integration/system/karaf/etc/org.apache.karaf.shell.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {profile} from /data-integration/system/karaf/etc/profile.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.bundle} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.bundle.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.features} from /data-integration/system/karaf/etc/org.apache.karaf.features.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.shim} from /data-integration/system/karaf/etc/pentaho.shim.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.feature} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.feature.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.config} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.config.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.felix.fileinstall~deploy} from /data-integration/system/karaf/etc/org.apache.felix.fileinstall-deploy.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {jmx.acl.org.apache.karaf.config} from /data-integration/system/karaf/etc/jmx.acl.org.apache.karaf.config.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.url.mvn} from /data-integration/system/karaf/etc/org.ops4j.pax.url.mvn.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.kar} from /data-integration/system/karaf/etc/org.apache.karaf.kar.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.caching~default} from /data-integration/system/karaf/etc/org.pentaho.caching-default.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.jaas} from /data-integration/system/karaf/etc/org.apache.karaf.jaas.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.scope_bundle} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.scope_bundle.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.aries.rsa.provider.fastbin} from /data-integration/system/karaf/etc/org.apache.aries.rsa.provider.fastbin.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.pdi.engine.spark} from /data-integration/system/karaf/etc/org.pentaho.pdi.engine.spark.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.ops4j.pax.transx.tm.geronimo} from /data-integration/system/karaf/etc/org.ops4j.pax.transx.tm.geronimo.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.karaf.command.acl.shell} from /data-integration/system/karaf/etc/org.apache.karaf.command.acl.shell.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {jmx.acl.org.apache.karaf.bundle} from /data-integration/system/karaf/etc/jmx.acl.org.apache.karaf.bundle.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.apache.felix.eventadmin.impl.EventAdmin} from /data-integration/system/karaf/etc/org.apache.felix.eventadmin.impl.EventAdmin.cfg
Aug 02, 2022 5:42:33 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {org.pentaho.features} from /data-integration/system/karaf/etc-kitchen/org.pentaho.features.cfg
2022/08/02 17:42:34 - Kitchen - Allocate & connect to repository.
2022/08/02 17:42:34 - Kitchen - Load the  repositories defined on this system.
Aug 02, 2022 5:42:39 PM org.pentaho.caching.impl.PentahoCacheManagerFactory$RegistrationHandler$1 onSuccess
INFO: New Caching Service registered
Aug 02, 2022 5:42:41 PM org.pentaho.caching.impl.PentahoCacheManagerFactory$RegistrationHandler$1 onSuccess
INFO: New Caching Service registered
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.identity.IdentityExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.fragment.FragmentExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.PerMessageDeflateExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.DeflateFrameExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.common.extensions.compress.XWebkitDeflateFrameExtension of service org.eclipse.jetty.websocket.api.extensions.Extension in bundle org.eclipse.jetty.websocket.common
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.http.Http1FieldPreEncoder of service org.eclipse.jetty.http.HttpFieldPreEncoder in bundle org.eclipse.jetty.http
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.security.jaspi.JaspiAuthenticatorFactory of service org.eclipse.jetty.security.Authenticator$Factory in bundle org.eclipse.jetty.security.jaspi
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.server.NativeWebSocketServletContainerInitializer of service javax.servlet.ServletContainerInitializer in bundle org.eclipse.jetty.websocket.server
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer of service javax.servlet.ServletContainerInitializer in bundle org.eclipse.jetty.websocket.javax.websocket.server
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.server.ContainerDefaultConfigurator of service javax.websocket.server.ServerEndpointConfig$Configurator in bundle org.eclipse.jetty.websocket.javax.websocket.server
Aug 02, 2022 5:42:43 PM org.apache.aries.spifly.BaseActivator log
INFO: Registered provider org.eclipse.jetty.websocket.jsr356.JettyClientContainerProvider of service javax.websocket.ContainerProvider in bundle org.eclipse.jetty.websocket.javax.websocket
2022-08-02 17:42:44.015:INFO::features-3-thread-1: Logging initialized @16191ms to org.eclipse.jetty.util.log.StdErrLog
Aug 02, 2022 5:42:44 PM org.apache.cxf.transport.http.osgi.ServletExporter updated
INFO: Registering new instance of "/cxf" servlet
Aug 02, 2022 5:42:44 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-management (128) [org.apache.cxf.management.InstrumentationManager]
Aug 02, 2022 5:42:44 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-rs-service-description (133) [org.apache.cxf.jaxrs.model.wadl.WadlGenerator]
Aug 02, 2022 5:42:44 PM org.apache.cxf.bus.osgi.CXFExtensionBundleListener addExtensions
INFO: Adding the extensions from bundle org.apache.cxf.cxf-rt-transports-http (135) [org.apache.cxf.transport.http.HTTPTransportFactory, org.apache.cxf.transport.http.HTTPWSDLExtensionLoader, org.apache.cxf.transport.http.policy.HTTPClientAssertionBuilder, org.apache.cxf.transport.http.policy.HTTPServerAssertionBuilder, org.apache.cxf.transport.http.policy.NoOpPolicyInterceptorProvider]
Aug 02, 2022 5:42:45 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be /i18n
Aug 02, 2022 5:42:45 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.big.data.impl.cluster} from /data-integration/system/karaf/etc/pentaho.big.data.impl.cluster.cfg
2022-08-02 17:42:45.430:INFO:oejws.WebSocketServerFactory:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:45.495:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): DefaultSessionIdManager workerName=node0
2022-08-02 17:42:45.495:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): No SessionScavenger set, using defaults
2022-08-02 17:42:45.496:INFO:oejs.session:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): node0 Scavenging every 600000ms
2022-08-02 17:42:45.500:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:45.509:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-i18n-webservice-bundle [220], contextID=default]}
2022-08-02 17:42:45.514:INFO:oejsh.ContextHandler:CM Configuration Updater (ManagedService Update: pid=[org.apache.cxf.osgi]): Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=org.apache.cxf.cxf-rt-transports-http [135], contextID=default]}
2022-08-02 17:42:45.523:INFO:oejs.Server:features-3-thread-1: jetty-9.4.43.v20210629; built: 2021-06-30T11:07:22.254Z; git: 526006ecfa3af7f1a27ef3a288e2bef7ea9dd7e8; jvm 11.0.15+10-Ubuntu-0ubuntu0.20.04.1
2022-08-02 17:42:45.546:INFO:oejs.AbstractConnector:features-3-thread-1: Started default@83cc056{HTTP/1.1, (http/1.1)}{0.0.0.0:9051}
2022-08-02 17:42:45.546:INFO:oejs.Server:features-3-thread-1: Started @17722ms
Aug 02, 2022 5:42:47 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.metaverse} from /data-integration/system/karaf/etc/pentaho.metaverse.cfg
2022-08-02 17:42:49.157:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.161:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-underscore [259], contextID=default]}
2022-08-02 17:42:49.168:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.170:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-translate [256], contextID=default]}
2022-08-02 17:42:49.259:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.265:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=org.pentaho.requirejs-manager-impl [250], contextID=default]}
2022-08-02 17:42:49.277:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.280:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-jquery [258], contextID=default]}
2022-08-02 17:42:49.286:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.289:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-sanitize [255], contextID=default]}
Aug 02, 2022 5:42:49 PM org.apache.felix.fileinstall.internal.Util$DefaultLogger log
INFO: Creating configuration {pentaho.geo.roles} from /data-integration/system/karaf/etc/pentaho.geo.roles.cfg
Aug 02, 2022 5:42:49 PM org.apache.cxf.endpoint.ServerImpl initDestination
INFO: Setting the server's publish address to be /marketplace
2022-08-02 17:42:49.877:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.883:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-route [254], contextID=default]}
2022-08-02 17:42:49.885:INFO:oejws.WebSocketServerFactory:FelixDispatchQueue: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.903:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.906:INFO:oejsh.ContextHandler:FelixDispatchQueue: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-marketplace-di [251], contextID=default]}
2022-08-02 17:42:49.912:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-animate [253], contextID=default]}
2022-08-02 17:42:49.931:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.934:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular-ui-bootstrap-bower [257], contextID=default]}
2022-08-02 17:42:49.948:INFO:oejws.WebSocketServerFactory:features-3-thread-1: No DecoratedObjectFactory provided, using new org.eclipse.jetty.util.DecoratedObjectFactory[decorators=1]
2022-08-02 17:42:49.951:INFO:oejsh.ContextHandler:features-3-thread-1: Started HttpServiceContext{httpContext=DefaultHttpContext [bundle=pentaho-webjars-angular [252], contextID=default]}
17:42:49,955 INFO  [DriverManager] Installing driver kars.
17:42:49,956 INFO  [DriverManager] 0 drivers will be installed.
17:42:49,957 INFO  [DriverManager] Finished installing drivers kars.
2022/08/02 17:42:50 - Repository repository-example1 - Loading 1 notes
2022/08/02 17:42:50 - Repository repository-example1 - Loading 3 job entries
2022/08/02 17:42:50 - Repository repository-example1 - Loading 2 job hops
2022/08/02 17:42:50 - Kitchen - Allocate job...
2022/08/02 17:42:50 - job_example - Start of job execution
2022/08/02 17:42:50 - job_example - exec(0, 0, Start.0)
2022/08/02 17:42:50 - Start - Starting job entry
2022/08/02 17:42:50 - job_example - Starting entry [Write to log]
2022/08/02 17:42:50 - job_example - exec(1, 0, Write to log.0)
2022/08/02 17:42:50 - Write to log - Starting job entry
2022/08/02 17:42:50 - example Job - this is a log by Example Job
2022/08/02 17:42:50 - job_example - Starting entry [Success]
2022/08/02 17:42:50 - job_example - exec(2, 0, Success.0)
2022/08/02 17:42:50 - Success - Starting job entry
2022/08/02 17:42:50 - job_example - Finished job entry [Success] (result=[true])
2022/08/02 17:42:50 - job_example - Finished job entry [Write to log] (result=[true])
2022/08/02 17:42:50 - job_example - Job execution finished
2022/08/02 17:42:50 - Carte - Installing timer to purge stale objects after 1440 minutes.
2022/08/02 17:42:50 - job_example - EmbeddedMetastore objects have been disposed.
2022/08/02 17:42:50 - Kitchen - Finished!
2022/08/02 17:42:50 - Kitchen - Start=2022/08/02 17:42:50.530, Stop=2022/08/02 17:42:50.560
2022/08/02 17:42:50 - Kitchen - Processing ended after 0 seconds.
```

### Cron Transformation

[Periodical Transformation example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/cron_transformation.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/cron_transformation.yaml
```

```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get kct
NAME                          AGE
cron-trasformation-example1   35m


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe kct cron-trasformation-example1 
Name:         cron-trasformation-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         CronKettleTransformation
Metadata:
  Creation Timestamp:  2022-08-02T17:13:23Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:dir:
        f:level:
        f:pass:
        f:rep:
        f:schedule:
        f:trans:
        f:user:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T17:13:23Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCodeDescription:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T17:13:23Z
  Resource Version:  434478
  UID:               0da8a883-cc29-4411-8a5b-7f1728b4c7cd
Spec:
  Dir:       /examples
  Level:     Debug
  Pass:      admin
  Rep:       Repository repository-example1
  Schedule:  */5 * * * *
  Trans:     transformation_example
  User:      admin
Status:
  Return Code Description:  INIT
Events:                     <none>


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe cronjobs cron-trasformation-example1
Name:                          cron-trasformation-example1
Namespace:                     test-namespace
Labels:                        app=cron-trasformation-example1
                               app.kubernetes.io/managed-by=kettle-operator
                               app.kubernetes.io/part-of=cron-trasformation-example1
Annotations:                   <none>
Schedule:                      */5 * * * *
Concurrency Policy:            Allow
Suspend:                       False
Successful Job History Limit:  3
Failed Job History Limit:      1
Starting Deadline Seconds:     <unset>
Selector:                      <unset>
Parallelism:                   <unset>
Completions:                   <unset>
Pod Template:
  Labels:  app=cron-trasformation-example1
           app.kubernetes.io/managed-by=kettle-operator
           app.kubernetes.io/part-of=cron-trasformation-example1
  Containers:
   cron-trasformation-example1:
    Image:      rossonet/kettle-runner:latest
    Port:       <none>
    Host Port:  <none>
    Command:
      /bin/sh
      -c
    Args:
      mkdir -p /root/.kettle ; cp /data/repositories.xml /root/.kettle/repositories.xml ; /data-integration/pan.sh -dir='/examples' -level='Debug' -pass='admin' -rep='Repository repository-example1' -trans='transformation_example' -user='admin'
    Environment:  <none>
    Mounts:
      /data from repositories-config (rw)
  Volumes:
   repositories-config:
    Type:            ConfigMap (a volume populated by a ConfigMap)
    Name:            cron-trasformation-example1
    Optional:        false
Last Schedule Time:  Tue, 02 Aug 2022 19:50:00 +0200
Active Jobs:         <none>
Events:
  Type    Reason            Age                From                Message
  ----    ------            ----               ----                -------
  Normal  SuccessfulCreate  35m                cronjob-controller  Created job cron-trasformation-example1-27657675
  Normal  SawCompletedJob   35m                cronjob-controller  Saw completed job: cron-trasformation-example1-27657675, status: Complete
  Normal  SuccessfulCreate  30m                cronjob-controller  Created job cron-trasformation-example1-27657680
  Normal  SawCompletedJob   30m                cronjob-controller  Saw completed job: cron-trasformation-example1-27657680, status: Complete
  Normal  SuccessfulCreate  25m                cronjob-controller  Created job cron-trasformation-example1-27657685
  Normal  SawCompletedJob   25m                cronjob-controller  Saw completed job: cron-trasformation-example1-27657685, status: Complete
  Normal  SuccessfulCreate  20m                cronjob-controller  Created job cron-trasformation-example1-27657690
  Normal  SawCompletedJob   19m (x2 over 19m)  cronjob-controller  Saw completed job: cron-trasformation-example1-27657690, status: Complete
  Normal  SuccessfulDelete  19m                cronjob-controller  Deleted job cron-trasformation-example1-27657675
  Normal  SuccessfulCreate  15m                cronjob-controller  Created job cron-trasformation-example1-27657695
  Normal  SuccessfulDelete  15m                cronjob-controller  Deleted job cron-trasformation-example1-27657680
  Normal  SawCompletedJob   15m                cronjob-controller  Saw completed job: cron-trasformation-example1-27657695, status: Complete
  Normal  SuccessfulCreate  10m                cronjob-controller  Created job cron-trasformation-example1-27657700
  Normal  SawCompletedJob   10m                cronjob-controller  Saw completed job: cron-trasformation-example1-27657700, status: Complete
  Normal  SuccessfulDelete  10m                cronjob-controller  Deleted job cron-trasformation-example1-27657685
  Normal  SuccessfulCreate  5m41s              cronjob-controller  Created job cron-trasformation-example1-27657705
  Normal  SawCompletedJob   5m                 cronjob-controller  Saw completed job: cron-trasformation-example1-27657705, status: Complete
  Normal  SuccessfulDelete  5m                 cronjob-controller  Deleted job cron-trasformation-example1-27657690
  Normal  SuccessfulCreate  41s                cronjob-controller  Created job cron-trasformation-example1-27657710
  Normal  SawCompletedJob   2s                 cronjob-controller  Saw completed job: cron-trasformation-example1-27657710, status: Complete
  Normal  SuccessfulDelete  2s                 cronjob-controller  Deleted job cron-trasformation-example1-27657695
```

### Cron Kettle Job

[Periodical Job example](https://github.com/rossonet/kettle-operator/blob/main/kubernetes/examples/cron_job.yaml)
```
kubectl apply -f https://raw.githubusercontent.com/rossonet/kettle-operator/main/kubernetes/examples/cron_job.yaml
```

```
[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace get kcj
NAME                AGE
cron-job-example1   37m


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe kcj cron-job-example1
Name:         cron-job-example1
Namespace:    test-namespace
Labels:       <none>
Annotations:  <none>
API Version:  kettle.rossonet.net/v1
Kind:         CronKettleJob
Metadata:
  Creation Timestamp:  2022-08-02T17:14:25Z
  Generation:          1
  Managed Fields:
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:metadata:
        f:annotations:
          .:
          f:kubectl.kubernetes.io/last-applied-configuration:
      f:spec:
        .:
        f:dir:
        f:job:
        f:level:
        f:pass:
        f:rep:
        f:schedule:
        f:user:
    Manager:      kubectl-client-side-apply
    Operation:    Update
    Time:         2022-08-02T17:14:25Z
    API Version:  kettle.rossonet.net/v1
    Fields Type:  FieldsV1
    fieldsV1:
      f:status:
        .:
        f:returnCodeDescription:
    Manager:         okhttp
    Operation:       Update
    Subresource:     status
    Time:            2022-08-02T17:14:25Z
  Resource Version:  434524
  UID:               02084947-2c13-4fac-8d4a-86803a01344c
Spec:
  Dir:       /examples
  Job:       job_example
  Level:     Debug
  Pass:      admin
  Rep:       Repository repository-example1
  Schedule:  */5 * * * *
  User:      admin
Status:
  Return Code Description:  INIT
Events:                     <none>


[andrea@legion-rossonet-com kettle-operator]$ kubectl -n test-namespace describe cronjobs cron-job-example1
Name:                          cron-job-example1
Namespace:                     test-namespace
Labels:                        app=cron-job-example1
                               app.kubernetes.io/managed-by=kettle-operator
                               app.kubernetes.io/part-of=cron-job-example1
Annotations:                   <none>
Schedule:                      */5 * * * *
Concurrency Policy:            Allow
Suspend:                       False
Successful Job History Limit:  3
Failed Job History Limit:      1
Starting Deadline Seconds:     <unset>
Selector:                      <unset>
Parallelism:                   <unset>
Completions:                   <unset>
Pod Template:
  Labels:  app=cron-job-example1
           app.kubernetes.io/managed-by=kettle-operator
           app.kubernetes.io/part-of=cron-job-example1
  Containers:
   cron-job-example1:
    Image:      rossonet/kettle-runner:latest
    Port:       <none>
    Host Port:  <none>
    Command:
      /bin/sh
      -c
    Args:
      mkdir -p /root/.kettle ; cp /data/repositories.xml /root/.kettle/repositories.xml ; /data-integration/kitchen.sh -dir='/examples' -job='job_example' -level='Debug' -pass='admin' -rep='Repository repository-example1' -user='admin'
    Environment:  <none>
    Mounts:
      /data from repositories-config (rw)
  Volumes:
   repositories-config:
    Type:            ConfigMap (a volume populated by a ConfigMap)
    Name:            cron-job-example1
    Optional:        false
Last Schedule Time:  Tue, 02 Aug 2022 19:50:00 +0200
Active Jobs:         <none>
Events:
  Type    Reason            Age    From                Message
  ----    ------            ----   ----                -------
  Normal  SuccessfulCreate  37m    cronjob-controller  Created job cron-job-example1-27657675
  Normal  SawCompletedJob   36m    cronjob-controller  Saw completed job: cron-job-example1-27657675, status: Complete
  Normal  SuccessfulCreate  32m    cronjob-controller  Created job cron-job-example1-27657680
  Normal  SawCompletedJob   31m    cronjob-controller  Saw completed job: cron-job-example1-27657680, status: Complete
  Normal  SuccessfulCreate  27m    cronjob-controller  Created job cron-job-example1-27657685
  Normal  SawCompletedJob   26m    cronjob-controller  Saw completed job: cron-job-example1-27657685, status: Complete
  Normal  SuccessfulCreate  22m    cronjob-controller  Created job cron-job-example1-27657690
  Normal  SawCompletedJob   21m    cronjob-controller  Saw completed job: cron-job-example1-27657690, status: Complete
  Normal  SuccessfulDelete  21m    cronjob-controller  Deleted job cron-job-example1-27657675
  Normal  SuccessfulCreate  17m    cronjob-controller  Created job cron-job-example1-27657695
  Normal  SuccessfulDelete  16m    cronjob-controller  Deleted job cron-job-example1-27657680
  Normal  SawCompletedJob   16m    cronjob-controller  Saw completed job: cron-job-example1-27657695, status: Complete
  Normal  SuccessfulCreate  12m    cronjob-controller  Created job cron-job-example1-27657700
  Normal  SawCompletedJob   11m    cronjob-controller  Saw completed job: cron-job-example1-27657700, status: Complete
  Normal  SuccessfulDelete  11m    cronjob-controller  Deleted job cron-job-example1-27657685
  Normal  SuccessfulCreate  7m33s  cronjob-controller  Created job cron-job-example1-27657705
  Normal  SawCompletedJob   6m55s  cronjob-controller  Saw completed job: cron-job-example1-27657705, status: Complete
  Normal  SuccessfulDelete  6m55s  cronjob-controller  Deleted job cron-job-example1-27657690
  Normal  SuccessfulCreate  2m33s  cronjob-controller  Created job cron-job-example1-27657710
  Normal  SawCompletedJob   119s   cronjob-controller  Saw completed job: cron-job-example1-27657710, status: Complete
  Normal  SuccessfulDelete  119s   cronjob-controller  Deleted job cron-job-example1-27657695
```

## Thanks to...

### Pentaho Kettle source code

[Pentaho Kettle repository](https://github.com/pentaho/pentaho-kettle)

### Framework Java used to create the Kubernetes Operator

[https://javaoperatorsdk.io/](https://javaoperatorsdk.io/)

[examples](https://github.com/java-operator-sdk/java-operator-sdk/tree/main/sample-operators)

### Project sponsor 

[![Rossonet s.c.a r.l.](https://raw.githubusercontent.com/rossonet/images/main/artwork/rossonet-logo/png/rossonet-logo_280_115.png)](https://www.rossonet.net)
