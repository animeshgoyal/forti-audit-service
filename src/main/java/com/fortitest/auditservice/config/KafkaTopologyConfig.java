package com.fortitest.auditservice.config;

import com.fortitest.auditservice.kafka.TopologyProvider;
import com.fortitest.auditservice.kafka.serdes.CustomAuditEventSerde;
import com.fortitest.auditservice.kafka.topology.AuditEventProcessorTopologyProvider;
import com.fortitest.auditservice.service.AuditService;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.*;

@EnableKafka
@Configuration
public class KafkaTopologyConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "audit-app");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomAuditEventSerde.class.getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    TopologyProvider topologyProvider(@Value(value = "${spring.kafka.bootstrap-servers}") String sourceTopicName,
                                      AuditService auditService){
        return new AuditEventProcessorTopologyProvider(sourceTopicName, auditService);
    }
}
