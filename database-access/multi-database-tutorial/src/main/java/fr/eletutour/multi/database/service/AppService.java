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

    public AppService(UserRepository userRepository,
                      OrderRepository orderRepository,
                      ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional("userTransactionManager")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional("orderTransactionManager")
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional("orderTransactionManager")
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Crée un utilisateur dans la base "users_db",
     * puis un produit et une commande dans la base "orders_db".
     * Chaque transaction reste indépendante.
     */
    public String createUserAndOrder(CreateUserAndOrderRequest request) {
        // 1️⃣ Création de l'utilisateur dans la base users_db
        User user = new User(request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail());
        User savedUser = createUser(user);

        // 2️⃣ Création du produit dans la base orders_db
        Product product = new Product(request.getProductName(), request.getProductPrice());
        Product savedProduct = createProduct(product);

        // 3️⃣ Création de la commande dans la base orders_db
        Order order = new Order(savedProduct, savedUser.getId());
        Order savedOrder = createOrder(order);

        return String.format("""
                ✅ Utilisateur '%s' (ID: %d) créé dans users_db.
                ✅ Produit '%s' (ID: %d) créé dans orders_db.
                ✅ Commande (ID: %d) créée dans orders_db, liée à l’utilisateur.""",
                savedUser.getUsername(),
                savedUser.getId(),
                savedProduct.getName(),
                savedProduct.getId(),
                savedOrder.getId());
    }
}
