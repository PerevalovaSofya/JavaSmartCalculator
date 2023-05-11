package com.example.calc.model;


import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Character.isDigit;

public class PolishNotation {

    private Stack<String> stack = new Stack<>();
    private List<String> polishString = new ArrayList<>();
    private String beginString;

    private static final Map<String, Integer> lexem = new HashMap<>() {
        {
            put("sin", 4);
            put("asin", 4);
            put("cos", 4);
            put("acos", 4);
            put("tan", 4);
            put("atan", 4);
            put("sqrt", 4);
            put("log", 4);
            put("ln", 4);
            put("^", 3);
            put("#", 3);
            put("~", 3);
            put("mod", 2);
            put("/", 2);
            put("*", 2);
            put("-", 1);
            put("+", 1);
            put("(", 0);
        }
    };


    public List<String> createPolishNotation(String str) {
        polishString.clear();
        beginString = str;
        unaryReplace();
        checkExpression();
        for (int index = 0; index < beginString.length(); ++index) {
            if (isDigit(beginString.charAt(index)) || beginString.charAt(index) == '.') {
                index = getOperand(index);
            } else if (beginString.charAt(index) == ')' || beginString.charAt(index) == '(') {
                getBracket(index);
            } else if (beginString.charAt(index) == 'x') {
                polishString.add(beginString.charAt(index) + "");
            } else {
                index = getOperator(index);
            }
        }
        while (!stack.isEmpty()) {
            polishString.add(stack.pop());
        }
        return polishString;
    }


    private void checkExpression() {
        int startBracket = 0;
        int endBracket = 0;
        int operator = 0;
        for (int i = 0; i < beginString.length() && startBracket >= endBracket && operator < 2; i++) {
            if (beginString.charAt(i) == '(') ++startBracket;
            if (beginString.charAt(i) == ')') ++endBracket;
            if (beginString.charAt(i) != '(' && lexem.containsKey("" + beginString.charAt(i))) {
                ++operator;
            } else {
                operator = 0;
            }
        }
        if (startBracket != endBracket || operator > 1) {
            throw new IllegalArgumentException("Stop polish notation: invalid expression");
        }
    }

    private void unaryReplace() {
        beginString = beginString.replaceAll("\\(-", "\\(~");
        beginString = beginString.replaceAll("^-", "~");
        beginString = beginString.replaceAll("\\(\\+", "\\(#");
        beginString = beginString.replaceAll("^\\+", "#");
    }

    private int getOperator(int index) {
        boolean findLexem = false;
        for (Map.Entry<String, Integer> entry : lexem.entrySet()) {
            if (beginString.indexOf(entry.getKey(), index) == index) {
                while (!stack.isEmpty() && (lexem.get(stack.peek()) > entry.getValue()
                        || Objects.equals(lexem.get(stack.peek()), entry.getValue()) && entry.getValue() < 3)) {
                    polishString.add(stack.pop());
                }
                stack.push(entry.getKey());
                index += entry.getKey().length();
                findLexem = true;
                break;
            }
        }
        if (!findLexem) {
            throw new IllegalArgumentException("Stop polish notation: lexem not found");
        }
        return --index;
    }

    private void getBracket(int index) {
        if (beginString.charAt(index) == '(') {
            stack.push("(");
        } else {
            while (!stack.isEmpty() && stack.peek() != "(") {
                polishString.add(stack.pop());
            }
            stack.pop();
        }
    }

    int getOperand(int index) {
        StringBuilder number = new StringBuilder();
        while (index < beginString.length() && (isDigit(beginString.charAt(index))
                || beginString.charAt(index) == '.' || beginString.charAt(index) == 'E')) {
            number.append(beginString.charAt(index));
            if (index != beginString.length() - 1 && beginString.charAt(index) == 'E' &&
                    (beginString.charAt(index + 1) == '-' || beginString.charAt(index + 1) == '+')) {
                number.append(beginString.charAt(index+1));
                ++index;
            }
            ++index;
        }
        polishString.add(number.toString());
        return --index;
    }

}