package fr.eletutour.bucket.aspect;

import fr.eletutour.bucket.service.BucketService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitingAspect {

    private final BucketService bucketService;

    public RateLimitingAspect(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @Around("@annotation(RateLimited)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // Récupération de l'annotation et de la stratégie
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);
        String strategy = rateLimited.strategy();

        // Détermination de la clé selon la stratégie
        String key;
        switch (strategy) {
            case "ip" -> key = request.getRemoteAddr();
            case "api-key" -> {
                String apiKey = request.getHeader("X-API-KEY");
                if (apiKey == null || apiKey.isBlank()) {
                    return ResponseEntity.badRequest().body("Clé API manquante dans le header X-API-KEY");
                }
                key = apiKey;
            }
            default -> key = "global";
        }

        Bucket bucket = bucketService.resolveBucket(key);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            if (response != null) {
                response.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            }
            return joinPoint.proceed();
        }

        long wait = probe.getNanosToWaitForRefill() / 1_000_000_000;
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(wait))
                .body("Quota dépassé, merci de réessayer dans " + wait + " secondes.");

    }
}
