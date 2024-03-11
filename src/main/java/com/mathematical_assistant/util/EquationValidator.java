package com.mathematical_assistant.util;

import com.mathematical_assistant.entity.Equation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class EquationValidator implements Validator {

    private final List<Character> allowedChars = List.of(' ', '(', ')', '.', '!', '+', '-', '*', '/', 'x', '=',
            '^', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Equation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Equation equation = (Equation) target;

        if(detectInadmissibleChars(equation.getPolynomial())) {
            errors.rejectValue("polynomial", "400", "Inadmissible chars in polynomial!");
        }

        if(countParenthesesClosure(equation.getPolynomial()) != 0) {
            errors.rejectValue("polynomial", "400", "Wrong polynomial structure! Check parentheses closure.");
        }

        if(detectConsecutiveSigns(equation.getPolynomial())) {
            errors.rejectValue("polynomial", "400", "Wrong polynomial structure! Check the correctness of the sequence of signs.");
        }

        if(detectMultiplePoint(equation.getPolynomial())) {
            errors.rejectValue("polynomial", "400", "Wrong polynomial structure! Check the multiple point in numbers.");
        }

        if (equation.getRoots() != null && detectMultiplePoint(equation.getRoots())) {
            errors.rejectValue("roots", "400", "Wrong roots structure! Check the multiple point in numbers.");
        }

    }

    private boolean detectInadmissibleChars(String polynomial) {
        for(int i = 0; i < polynomial.length(); i++) {
            if(!allowedChars.contains(polynomial.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private int countParenthesesClosure(String polynomial) {
        int openParentheses = 0;

        for(int i = 0; i < polynomial.length(); i++) {
            if(polynomial.charAt(i) == '(')
                openParentheses++;
            else if (polynomial.charAt(i) == ')')
                openParentheses--;
        }

        return openParentheses;
    }

    private boolean detectConsecutiveSigns(String polynomial) {
        for(int i = 0; i < polynomial.length() - 1; i++) {
            if((polynomial.charAt(i) == '+' || polynomial.charAt(i) == '-')
                    && (polynomial.charAt(i + 1) == '*' || polynomial.charAt(i + 1) == '/')) {
                return true;
            }
        }

        return false;
    }

    private boolean detectMultiplePoint(String polynomial) {
        int secondPointCounter = 0;

        for(int i = 0; i < polynomial.length() - 1; i++) {
            if (polynomial.charAt(i) == '.') {
                if(! (polynomial.charAt(i + 1) >= '0' && polynomial.charAt(i + 1) <= '9')) {
                    return true;
                }
                secondPointCounter++;
                if (secondPointCounter > 1)
                    return true;
            } else if (polynomial.charAt(i) == '+' || polynomial.charAt(i) == '-' || polynomial.charAt(i) == '/' ||
                    polynomial.charAt(i) == '*' || polynomial.charAt(i) == '=' || polynomial.charAt(i) == '!'){
                secondPointCounter--;
            }
        }

        return false;
    }
}
