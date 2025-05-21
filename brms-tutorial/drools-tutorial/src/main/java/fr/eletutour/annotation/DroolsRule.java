package fr.eletutour.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation personnalisée pour marquer les méthodes qui implémentent des règles Drools.
 * Cette annotation est utilisée pour identifier les méthodes qui contiennent
 * la logique des règles métier à exécuter par le moteur Drools.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DroolsRule {
}