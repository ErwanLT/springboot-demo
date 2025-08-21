package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principale de l'application Spring Batch.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de Spring Batch pour le traitement par lots</li>
 *     <li>La configuration des jobs et des steps</li>
 *     <li>Le traitement des données en masse</li>
 *     <li>La planification des tâches avec @EnableScheduling</li>
 * </ul>
 */
@SpringBootApplication
@EnableScheduling
public class Main {

    /**
     * Point d'entrée de l'application Spring Batch.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}