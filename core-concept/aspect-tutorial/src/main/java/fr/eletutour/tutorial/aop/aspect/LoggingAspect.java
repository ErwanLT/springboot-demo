package fr.eletutour.tutorial.aop.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* fr.eletutour.tutorial.aop.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Méthode exécutée : {}", joinPoint.getSignature());
    }

    @After("execution(* fr.eletutour.tutorial.aop.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        LOGGER.info("Méthode terminée : {}", joinPoint.getSignature());
    }

    @Around("execution(* fr.eletutour.tutorial.aop.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Avant la méthode : {}", joinPoint.getSignature());
        LOGGER.info("Args de la méthode {}", joinPoint.getArgs());

        // Appel de la méthode cible
        Object result = joinPoint.proceed();

        LOGGER.info("Après la méthode : {}", joinPoint.getSignature());
        return result;
    }

    @AfterReturning(
            pointcut = "execution(* fr.eletutour.tutorial.aop.service.*.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        LOGGER.info("Méthode réussie : {}", joinPoint.getSignature());
        LOGGER.info("Résultat : {}", result);
    }
}
