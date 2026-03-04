package fr.eletutour.outbox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eletutour.outbox.dto.CreateOrderRequest;
import fr.eletutour.outbox.dto.OrderCreatedEvent;
import fr.eletutour.outbox.entity.CustomerOrder;
import fr.eletutour.outbox.entity.OutboxEvent;
import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.CustomerOrderRepository;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    private static final String AGGREGATE_TYPE_ORDER = "ORDER";
    private static final String EVENT_TYPE_ORDER_CREATED = "ORDER_CREATED";

    private final CustomerOrderRepository customerOrderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OrderService(CustomerOrderRepository customerOrderRepository,
                        OutboxEventRepository outboxEventRepository,
                        ObjectMapper objectMapper) {
        this.customerOrderRepository = customerOrderRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UUID createOrder(CreateOrderRequest request) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomerName(request.customerName());
        order.setAmount(request.amount());
        CustomerOrder savedOrder = customerOrderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerName(),
                savedOrder.getAmount(),
                savedOrder.getCreatedAt()
        );

        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType(AGGREGATE_TYPE_ORDER);
        outboxEvent.setAggregateId(savedOrder.getId().toString());
        outboxEvent.setEventType(EVENT_TYPE_ORDER_CREATED);
        outboxEvent.setStatus(OutboxStatus.PENDING);
        outboxEvent.setPayload(toJson(event));
        outboxEventRepository.save(outboxEvent);

        return savedOrder.getId();
    }

    private String toJson(OrderCreatedEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to serialize outbox event", e);
        }
    }
}
