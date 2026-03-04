package fr.eletutour.outbox.service;

import fr.eletutour.outbox.TransactionalOutboxApplication;
import fr.eletutour.outbox.dto.CreateOrderRequest;
import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.CustomerOrderRepository;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = TransactionalOutboxApplication.class,
        properties = "spring.task.scheduling.enabled=false"
)
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Test
    void shouldPersistOrderAndOutboxEventInSameTransaction() {
        UUID orderId = orderService.createOrder(new CreateOrderRequest("Erwan", BigDecimal.valueOf(42.50)));

        assertThat(customerOrderRepository.findById(orderId)).isPresent();

        var pendingEvents = outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        assertThat(pendingEvents).hasSize(1);

        var event = pendingEvents.getFirst();
        assertThat(event.getAggregateType()).isEqualTo("ORDER");
        assertThat(event.getAggregateId()).isEqualTo(orderId.toString());
        assertThat(event.getEventType()).isEqualTo("ORDER_CREATED");
        assertThat(event.getPayload()).contains(orderId.toString());
        assertThat(event.getSentAt()).isNull();
    }
}
