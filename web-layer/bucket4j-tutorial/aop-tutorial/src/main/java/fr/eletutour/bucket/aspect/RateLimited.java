package fr.eletutour.bucket.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimited {
    /**
     * Type de limite à appliquer : par IP, par API key, etc.
     * Cela permettra d’adapter la logique dans l’aspect.
     */
    String strategy() default "global";
}
