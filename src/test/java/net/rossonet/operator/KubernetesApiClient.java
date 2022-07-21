package net.rossonet.operator;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class KubernetesApiClient {

	// @Test
	public void tryConnection() {
		final Config config = new ConfigBuilder().withNamespace(null).build();
		final KubernetesClient client = new DefaultKubernetesClient(config);
		for (final Pod pod : client.pods().list().getItems()) {
			System.out.println(
					"found pod " + pod.getMetadata().getName() + " in namespace " + pod.getMetadata().getNamespace());
			System.out.println(pod.toString() + "\n");
		}
		client.close();
	}
}
