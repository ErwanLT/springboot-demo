package fr.eletutour.dynamic.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eletutour.dynamic.dto.Command;
import fr.eletutour.dynamic.dto.OnlineCommand;
import fr.eletutour.dynamic.dto.StoreCommand;
import fr.eletutour.dynamic.entities.OrderEntity;
import fr.eletutour.dynamic.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public Long createOrder(Command command) {

        OrderEntity entity = new OrderEntity();

        switch (command) {
            case OnlineCommand online -> {
                logger.info("Reception d'une commande en ligne");
                entity.setType("online");
                entity.setPayload(serialize(online));
            }
            case StoreCommand store -> {
                logger.info("Reception d'une commande en magasin");
                entity.setType("store");
                entity.setPayload(serialize(store));
            }
        }

        return repository.save(entity).getId();
    }

    private String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de sérialisation", e);
        }
    }
}
