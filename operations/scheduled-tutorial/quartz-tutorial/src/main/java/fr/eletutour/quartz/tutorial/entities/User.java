package fr.eletutour.quartz.tutorial.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entité représentant un utilisateur dans le système.
 * Cette entité est utilisée pour :
 * <ul>
 *     <li>Le stockage des informations utilisateur</li>
 *     <li>Le suivi des connexions</li>
 *     <li>La gestion des statuts utilisateur</li>
 * </ul>
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Identifiant unique de l'utilisateur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom d'utilisateur unique.
     */
    private String username;

    /**
     * Date et heure de la dernière connexion de l'utilisateur.
     */
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    /**
     * Statut actuel de l'utilisateur (actif, inactif, etc.).
     */
    private String status;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public User() {
    }

    /**
     * Constructeur avec paramètres pour créer un nouvel utilisateur.
     *
     * @param username Le nom d'utilisateur
     * @param lastLoginDate La date de dernière connexion
     * @param status Le statut de l'utilisateur
     */
    public User(String username, LocalDateTime lastLoginDate, String status) {
        this.username = username;
        this.lastLoginDate = lastLoginDate;
        this.status = status;
    }

    /**
     * Récupère l'identifiant de l'utilisateur.
     *
     * @return L'identifiant de l'utilisateur
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'utilisateur.
     *
     * @param id Le nouvel identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur.
     *
     * @return Le nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur.
     *
     * @param username Le nouveau nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère la date de dernière connexion.
     *
     * @return La date de dernière connexion
     */
    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * Définit la date de dernière connexion.
     *
     * @param lastLoginDate La nouvelle date de dernière connexion
     */
    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * Récupère le statut de l'utilisateur.
     *
     * @return Le statut de l'utilisateur
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut de l'utilisateur.
     *
     * @param status Le nouveau statut
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retourne une représentation textuelle de l'utilisateur.
     *
     * @return Une chaîne de caractères représentant l'utilisateur
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", status='" + status + '\'' +
                '}';
    }
}