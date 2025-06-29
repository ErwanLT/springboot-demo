package fr.eletutour.bucket.filter;

import fr.eletutour.bucket.service.BucketService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Set;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final BucketService bucketService;
    // Liste des préfixes d'URI à whitelister (modifiable facilement)
    private static final Set<String> WHITELIST_PATTERNS = Set.of(
        "/actuator/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public RateLimitingFilter(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null || apiKey.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Clé API manquante (header X-API-KEY).");
            return;
        }
        Bucket bucket = bucketService.resolveBucket(apiKey);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request, response);
        } else {
            long wait = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(wait));
            response.getWriter().write("Quota dépassé. Merci de réessayer dans " + wait + " secondes.");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return WHITELIST_PATTERNS.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, uri));
    }
}
