package com.mathematical_assistant.util.math_polynomial;

import com.mathematical_assistant.entity.Equation;

public class EquationRotsOperation {

    public static int getRootsNum(String roots) {
        int counter = 0;
        for(int i = 0; i < roots.length(); i++) {
            if(roots.charAt(i) == ';') {
                counter++;
            }
        }
        return counter;
    }

    public static void checkRoots(Equation equation, String roots) {
        if(!roots.contains(";"))
            checkRoot(equation, roots);

        String[] rootsArray = roots.split(";");

        for(String root: rootsArray) {
            checkRoot(equation, root);
        }
    }

    private static void checkRoot(Equation equation, String rootString) {
        double root = Double.parseDouble(rootString.trim());

        String[] leftRight = equation.getPolynomial().split("=");

        PolynomialCalculator calculator = new PolynomialCalculator(leftRight[0]);
        double left = calculator.calculateValue(root);

        calculator.setPolynomial(leftRight[1]);
        double right = calculator.calculateValue(root);

        if(Math.abs(left - right) > 1e-9) {
            throw new IllegalArgumentException("Wrong root: " + rootString);
        }
    }
}
