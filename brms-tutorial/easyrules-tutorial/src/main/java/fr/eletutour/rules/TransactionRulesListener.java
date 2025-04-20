package fr.eletutour.rules;

import fr.eletutour.exception.TransactionException;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

public class TransactionRulesListener implements RuleListener {

    private TransactionException transactionException;

    public void reset() {
        this.transactionException = null;
    }

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        return true; // Permet l'évaluation de la règle
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        // Rien à faire après l'évaluation
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        // Rien à faire avant l'exécution
    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        // Rien à faire en cas de succès
    }

    @Override
    public void onFailure(Rule rule, Facts facts, Exception exception) {
        // Capturer TransactionException si elle est la cause
        Throwable cause = exception;
        while (cause != null) {
            if (cause instanceof TransactionException) {
                this.transactionException = (TransactionException) cause;
                return;
            }
            cause = cause.getCause();
        }
        // Stocker une exception générique si ce n'est pas une TransactionException
        this.transactionException = new TransactionException("Error executing rule: " + rule.getName());
    }

    /**
     * Lance l'exception capturée, s'il y en a une.
     */
    public void throwIfFailed() throws TransactionException {
        if (transactionException != null) {
            throw transactionException;
        }
    }
}