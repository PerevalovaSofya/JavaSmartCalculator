package com.example.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Character.isDigit;
import static java.lang.Math.*;

public class Calulation {
    private List<String> polishString = new ArrayList<>();
    private Stack<Double> stack = new Stack<>();

    public double computation(List<String> polish, double x) {
        polishString = polish;
        double result = 0;
        for (String str : polishString) {
            if (isDigit(str.charAt(0))) {
                stack.push(Double.parseDouble(str));
            } else if (str.equals("x")) {
                stack.push(x);
            } else {
                functionCalculation(str);
            }
        }

        result = stack.pop();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Stop calculation: invalid incoming string");
        }

        return result;
    }

    private void functionCalculation(String str) {
        double res = 0;
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Stop calculation: stack is empty");
        }
        double first = stack.pop();
        if (str.equals("^") || str.equals("-")
                || str.equals("+") || str.equals("/") || str.equals("*") || str.equals("mod")) {
            if (stack.isEmpty()) {
                throw new IllegalArgumentException("Stop calculation: few arguments");
            }
            double second = stack.pop();
            if (str.equals("+")) res = second + first;
            if (str.equals("-")) res = second - first;
            if (str.equals("*")) res = second * first;
            if (str.equals("/")) res = second / first;
            if (str.equals("^")) res = pow(second, first);
            if (str.equals("mod")) res = second % first;
        } else {
            if (str.equals("sin")) res = sin(first);
            if (str.equals("cos")) res = cos(first);
            if (str.equals("tan")) res = tan(first);
            if (str.equals("asin")) res = asin(first);
            if (str.equals("acos")) res = acos(first);
            if (str.equals("atan")) res = atan(first);
            if (str.equals("sqrt")) res = sqrt(first);
            if (str.equals("ln")) res = log(first);
            if (str.equals("log")) res = log10(first);
            if (str.equals("#")) res = first;
            if (str.equals("~")) res = (-1) * first;
        }
        stack.push(res);
    }
}
