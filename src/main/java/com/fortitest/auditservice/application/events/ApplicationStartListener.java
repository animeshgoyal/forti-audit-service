package com.fortitest.auditservice.application.events;

import com.fortitest.auditservice.kafka.TopologyProvider;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LogManager.getLogger(ApplicationStartListener.class);
    private final TopologyProvider topologyProvider;
    private final KafkaStreamsConfiguration kafkaStreamsConfiguration;

    public ApplicationStartListener(KafkaStreamsConfiguration kafkaStreamsConfiguration,
                                    TopologyProvider topologyProvider) {
        this.topologyProvider = topologyProvider;
        this.kafkaStreamsConfiguration = kafkaStreamsConfiguration;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent cse) {
        LOG.info("Initializing Kafka Stream Topology");
        final Topology topology = topologyProvider.get();

        LOG.info("{}", topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, kafkaStreamsConfiguration.asProperties());
        // cleanup before starting
        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // do cleanup before stopping.
            streams.cleanUp();
            streams.close();
        }));
        LOG.info("Initialized Kafka Stream Topology");

    }
}
