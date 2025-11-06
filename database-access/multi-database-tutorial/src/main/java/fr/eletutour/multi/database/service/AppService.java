package fr.eletutour.multi.database.service;

import fr.eletutour.multi.database.controller.CreateUserAndOrderRequest;
import fr.eletutour.multi.database.orders.domain.Order;
import fr.eletutour.multi.database.orders.domain.Product;
import fr.eletutour.multi.database.orders.repository.OrderRepository;
import fr.eletutour.multi.database.orders.repository.ProductRepository;
import fr.eletutour.multi.database.users.domain.User;
import fr.eletutour.multi.database.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public AppService(UserRepository userRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional("userTransactionManager")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional("orderTransactionManager")
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional("orderTransactionManager")
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public String createUserAndOrder(CreateUserAndOrderRequest request) {
        User user = new User(request.getUsername(), request.getFirstName(), request.getLastName(), request.getEmail());
        User savedUser = createUser(user);

        Product product = new Product(request.getProductName(), request.getProductPrice());
        Product savedProduct = createProduct(product);

        Order order = new Order(savedProduct, savedUser.getId());
        Order savedOrder = createOrder(order);

        return String.format("User '%s' (ID: %d) created in H2. Product '%s' (ID: %d) created in H2. Order (ID: %d) created in H2 linking them.",
                savedUser.getUsername(),
                savedUser.getId(),
                savedProduct.getName(),
                savedProduct.getId(),
                savedOrder.getId());
    }
}
