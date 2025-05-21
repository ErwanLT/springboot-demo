package fr.eletutour.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service gérant la génération, la validation et l'extraction des informations des tokens JWT.
 * Ce service est responsable de toutes les opérations liées aux tokens JWT dans l'application.
 */
@Service
public class JwtService {

    private final String secretKey;
    private final long jwtExpiration;

    /**
     * Constructeur du service JWT.
     *
     * @param secretKey La clé secrète utilisée pour signer les tokens JWT
     * @param jwtExpiration La durée de validité des tokens JWT en millisecondes
     */
    public JwtService(@Value("${security.jwt.secret-key}") String secretKey,
                      @Value("${security.jwt.expiration-time}") long jwtExpiration) {
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * Extrait le nom d'utilisateur à partir d'un token JWT.
     *
     * @param token Le token JWT
     * @return Le nom d'utilisateur extrait du token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une information spécifique du token JWT.
     *
     * @param token Le token JWT
     * @param claimsResolver La fonction pour extraire l'information souhaitée
     * @param <T> Le type de l'information à extraire
     * @return L'information extraite du token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génère un nouveau token JWT pour un utilisateur.
     *
     * @param userDetails Les détails de l'utilisateur
     * @return Le token JWT généré
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Génère un nouveau token JWT avec des informations supplémentaires.
     *
     * @param extraClaims Les informations supplémentaires à inclure dans le token
     * @param userDetails Les détails de l'utilisateur
     * @return Le token JWT généré
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Récupère la durée de validité des tokens JWT.
     *
     * @return La durée de validité en millisecondes
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Construit un token JWT avec les informations fournies.
     *
     * @param extraClaims Les informations supplémentaires à inclure
     * @param userDetails Les détails de l'utilisateur
     * @param expiration La durée de validité du token
     * @return Le token JWT construit
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifie si un token JWT est valide pour un utilisateur donné.
     *
     * @param token Le token JWT à vérifier
     * @param userDetails Les détails de l'utilisateur
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Vérifie si un token JWT est expiré.
     *
     * @param token Le token JWT à vérifier
     * @return true si le token est expiré, false sinon
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d'expiration d'un token JWT.
     *
     * @param token Le token JWT
     * @return La date d'expiration du token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait toutes les informations d'un token JWT.
     *
     * @param token Le token JWT
     * @return Les informations extraites du token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Génère la clé de signature pour les tokens JWT.
     *
     * @return La clé de signature
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}