package com.example.calc.controller;

import com.example.calc.model.Calulation;
import com.example.calc.model.PolishNotation;

import java.util.List;

public final class Controller {

    private PolishNotation polishNotation = new PolishNotation();
    private Calulation calculation = new Calulation();


    public List<String> findPolishString(String originalString) {
        return polishNotation.createPolishNotation(originalString);
    }

    public double calculateAnswer(List<String> polishString, double x) {
       return calculation.computation(polishString, x);
    }

}
