package fr.eletutour.ia.mcp.server.service;

import fr.eletutour.ia.mcp.server.configuration.GhostProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class GhostJwtService {

    private final GhostProperties properties;

    public GhostJwtService(GhostProperties properties) {
        this.properties = properties;
    }

    public String generateToken() {

        String[] parts = properties.adminApiKey().split(":");

        String keyId = parts[0];
        String secret = parts[1];

        byte[] secretBytes = hexStringToByteArray(secret);

        SecretKey key = Keys.hmacShaKeyFor(secretBytes);

        Instant now = Instant.now();

        return Jwts.builder()
                .header()
                    .keyId(keyId)
                    .type("JWT")
                    .and()

                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(300)))
                .audience()
                    .add("/admin/")
                    .and()

                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private byte[] hexStringToByteArray(String hex) {

        int len = hex.length();

        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {

            data[i / 2] = (byte)
                    ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }

        return data;
    }
}