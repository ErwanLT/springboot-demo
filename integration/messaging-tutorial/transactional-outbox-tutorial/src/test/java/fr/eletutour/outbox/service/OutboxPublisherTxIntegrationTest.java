package fr.eletutour.outbox.service;

import fr.eletutour.outbox.TransactionalOutboxApplication;
import fr.eletutour.outbox.entity.OutboxEvent;
import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = TransactionalOutboxApplication.class,
        properties = "spring.task.scheduling.enabled=false"
)
class OutboxPublisherTxIntegrationTest {

    @Autowired
    private OutboxPublisherTx outboxPublisherTx;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void shouldMarkOutboxEventAsSentAfterKafkaPublication() throws Exception {
        OutboxEvent event = new OutboxEvent();
        event.setAggregateType("ORDER");
        event.setAggregateId("order-123");
        event.setEventType("ORDER_CREATED");
        event.setPayload("{\"orderId\":\"order-123\"}");
        event.setStatus(OutboxStatus.PENDING);
        OutboxEvent saved = outboxEventRepository.save(event);

        @SuppressWarnings("unchecked")
        SendResult<String, String> result = Mockito.mock(SendResult.class);
        RecordMetadata metadata = Mockito.mock(RecordMetadata.class);
        when(result.getRecordMetadata()).thenReturn(metadata);
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(result));

        outboxPublisherTx.publishOne(saved.getId());

        OutboxEvent updated = outboxEventRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(OutboxStatus.SENT);
        assertThat(updated.getSentAt()).isNotNull();
    }
}
