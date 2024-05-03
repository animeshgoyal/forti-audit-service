package com.fortitest.auditservice.kafka;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.kafka.serdes.CustomAuditEventSerde;
import com.fortitest.auditservice.kafka.topology.AuditEventProcessorTopologyProvider;
import com.fortitest.auditservice.service.AuditService;
import com.fortitest.auditservice.test.utils.ObjectCreator;
import com.fortitest.auditservice.test.utils.SecurityTestUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class KafkaTopologyTest {

    private static TopologyTestDriver testDriver;

    private CustomAuditEventSerde customAuditEventSerde = new CustomAuditEventSerde();

    @Autowired
    private AuditService auditService;

    @BeforeEach
    public void setup() {
        TopologyProvider topologyProvider = new AuditEventProcessorTopologyProvider("input-topic", auditService);
        Topology topology = topologyProvider.get();
        // setup test driver
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "maxAggregation");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CustomAuditEventSerde.class.getName());
        testDriver = new TopologyTestDriver(topology, props);
    }

    @Test
    public void testAdminUser() throws IOException {
        SecurityTestUtils.setupAdmin();
        TestInputTopic<String, AuditEvent> inputTopic = testDriver.createInputTopic("input-topic", Serdes.String().serializer(), customAuditEventSerde.serializer());
        inputTopic.pipeInput("key", ObjectCreator.getEvent());
        List<AuditEvent> auditEventList = auditService.getAuditEvents(0, 4, "timeStamp");
        assertNotNull(auditEventList);
    }

    @Test
    public void testNonAdminUser() throws IOException {
        SecurityTestUtils.setupNonAdmin();
        TestInputTopic<String, AuditEvent> inputTopic = testDriver.createInputTopic("input-topic", Serdes.String().serializer(), customAuditEventSerde.serializer());
        for(AuditEvent event : ObjectCreator.getEvents()) {
            inputTopic.pipeInput("key", event);
        }
        List<AuditEvent> auditEventList = auditService.getAuditEvents(0, 4, "timeStamp");
        assertNotNull(auditEventList);
    }
}
