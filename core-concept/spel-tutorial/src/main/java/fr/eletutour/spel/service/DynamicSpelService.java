package fr.eletutour.spel.service;

import fr.eletutour.spel.model.Car;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

@Service
public class DynamicSpelService {

    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * Evaluates a SpEL expression with a variable number passed as an argument.
     *
     * @param number the number to use as a variable in the expression.
     * @return the boolean result of the expression.
     */
    public boolean evaluateIsGreaterThan(int number) {
        // The expression string with a variable placeholder #number
        String expressionString = "#number > 50";

        // 1. Parse the expression
        Expression expression = parser.parseExpression(expressionString);

        // 2. Create an evaluation context
        EvaluationContext context = new StandardEvaluationContext();

        // 3. Set the variable in the context
        context.setVariable("number", number);

        // 4. Evaluate the expression and get the result
        Boolean result = expression.getValue(context, Boolean.class);

        return result != null && result;
    }

    /**
     * Evaluates an expression against a root object, passing an argument.
     *
     * @param car the Car object to be the root of the evaluation.
     * @param yearToCompare the year to compare against, passed as a variable.
     * @return the boolean result.
     */
    public boolean evaluateCarNewerThan(Car car, int yearToCompare) {
        String expressionString = "isNewerThan(#year)"; // Calls isNewerThan on the root object (the car)
        Expression expression = parser.parseExpression(expressionString);
        EvaluationContext context = new StandardEvaluationContext(car); // Set 'car' as the root object
        context.setVariable("year", yearToCompare);

        Boolean result = expression.getValue(context, Boolean.class);
        return result != null && result;
    }
}
