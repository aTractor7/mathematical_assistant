package com.mathematical_assistant.services;


import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

@Getter
@Setter
public class PolynomialValueCalculate {

    private double x;
    private String polynomial;

    public PolynomialValueCalculate(double x, String polynomial) {
        this.x = x;
        this.polynomial = polynomial;
    }

    public double calculate() {
        String transformed = powerTransform();
        transformed = replaceX(transformed);

        return calculate(convertToReversePolish(transformed));
    }

    private String powerTransform() {
        StringBuilder transformed = new StringBuilder();

        int numStartIndex = 0;
        for(int i = 0; i < polynomial.length(); i++) {
            if(polynomial.charAt(i) == '^') {
                String num = polynomial.substring(numStartIndex + 1, i);
                int power = Character.getNumericValue(polynomial.charAt(++i));

                transformed.append((" * " + num).repeat(power - 1));
            } else {
                if(polynomial.charAt(i) == ' ' || polynomial.charAt(i) == '(')
                    numStartIndex = i;
                transformed.append(polynomial.charAt(i));
            }
        }

        return transformed.toString();
    }

    private String replaceX(String polynomial) {
        StringBuilder replaced = new StringBuilder();

        for(int i = 0; i < polynomial.length(); i++) {
            if(polynomial.charAt(i) == 'x') {
                replaced.append(x);
            } else {
                replaced.append(polynomial.charAt(i));
            }
        }

        return replaced.toString();
    }

    private String convertToReversePolish(String polynomial) {
        String result = "";

        Stack<Character> operator = new Stack<>();
        Stack<String> reversePolish = new Stack<>();
        operator.push('#');

        for (int i = 0; i < polynomial.length();) {

            while (i < polynomial.length() && polynomial.charAt(i) == ' ')
                i++;


            if (isNumOrConst(polynomial.charAt(i))) {
                StringBuilder num = new StringBuilder();
                while (i < polynomial.length() && isNumOrConst(polynomial.charAt(i)))
                    num.append(polynomial.charAt(i++));
                reversePolish.push(num.toString());

            } else if (isOperator(polynomial.charAt(i))) {
                char op = polynomial.charAt(i);
                switch (op) {
                    case '(':
                        operator.push(op);
                        break;
                    case ')':
                        while (operator.peek() != '(')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.pop();
                        break;
                    case '+':
                    case '-':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;
                    case '*':
                    case '/':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '+' &&
                                    operator.peek() != '-' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;

                }
                i++;
            }
        }
        while (operator.peek() != '#')
            reversePolish.push(Character.toString(operator.pop()));
        while (!reversePolish.isEmpty())
            result = result.length() == 0? reversePolish.pop() + result: reversePolish.pop() + " " + result;
        return result;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    private static boolean isNumOrConst(char c) {
        return c >= '0' && c <= '9' || c == '.' || c == 'x';
    }

    private double calculate(String s) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = s.split(" ");

        for (String token : tokens) {
            double result, element1, element2;
            switch (token) {
                case "+" -> {
                    element1 = stack.pop();
                    element2 = stack.pop();
                    result = element2 + element1;
                    stack.push(result);
                }
                case "-" -> {
                    element1 = stack.pop();
                    element2 = stack.pop();
                    result = element2 - element1;
                    stack.push(result);
                }
                case "*" -> {
                    element1 = stack.pop();
                    element2 = stack.pop();
                    result = element2 * element1;
                    stack.push(result);
                }
                case "/" -> {
                    element1 = stack.pop();
                    element2 = stack.pop();
                    result = element2 / element1;
                    stack.push(result);
                }
                default -> {
                    result = Double.parseDouble(token);
                    stack.push(result);
                }
            }
        }
        return  stack.pop();
    }
}
