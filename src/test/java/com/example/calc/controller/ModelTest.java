package com.example.calc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Controller controller;
    @BeforeEach
    void beforeEachMethod() {
        controller = new Controller();
    }
    @Test
    void findPolishStringTest1() {
        assertEquals(Arrays.asList("3", "2", "+"), controller.findPolishString("3+2"));
    }

    @Test
    void findPolishStringTest2() {
        assertEquals(Arrays.asList("20", "5", "8", "-", "*" ), controller.findPolishString("20*(5-8)"));
    }

    @Test
    void findPolishStringTest3() {
        assertEquals(Arrays.asList("2", "10", "/", "sin"), controller.findPolishString("sin(2/10)"));
    }

    @Test
    void findPolishStringTest4() {
        assertEquals(Arrays.asList("2", "8", "^", "5", "3", "*", "/", "10", "+"), controller.findPolishString("2^8/(5*3)+10"));
    }

    @Test
    void calculateAnswerTest1() {
        assertEquals(5.0, controller.calculateAnswer(Arrays.asList("3", "2", "+"), 0));
    }

    @Test
    void calculateAnswerTest2() {
        assertEquals(0.479425538604203, controller.calculateAnswer(Arrays.asList("0.5", "sin" ), 0));
    }

    @Test
    void calculateAnswerTest3() {
        assertEquals(2.0, controller.calculateAnswer(Arrays.asList("20", "3", "mod"), 0));
    }
}