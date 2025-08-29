package fr.eletutour.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour marquer les méthodes qui implémentent des règles de transaction.
 * Cette annotation est utilisée par l'aspect TransactionAspect pour intercepter
 * l'exécution des méthodes et appliquer les règles métier appropriées.
 * Elle peut être appliquée uniquement aux méthodes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransactionRule {
}