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

    /**
     * Cette méthode exécute une transaction globale sur les deux bases H2.
     * Si une étape échoue, toutes les opérations sont annulées.
     */
    @Transactional("chainedTransactionManager")
    public String createUserAndOrder(CreateUserAndOrderRequest request) {
        // Création de l'utilisateur (users_db)
        User user = new User(
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
        User savedUser = userRepository.save(user);

        // Création du produit (orders_db)
        Product product = new Product(
                request.getProductName(),
                request.getProductPrice()
        );
        Product savedProduct = productRepository.save(product);

        // Création de la commande (orders_db)
        Order order = new Order(savedProduct, savedUser.getId());
        Order savedOrder = orderRepository.save(order);

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
