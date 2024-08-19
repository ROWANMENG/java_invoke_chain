package com.example.calculator.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testDivide() {
        assertEquals(2, calculatorService.divide(6, 3));
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.divide(6, 0);
        });
    }


    @Test
    public void testMultiplyPositiveNumbers() {
        assertEquals(6, calculatorService.multiply(2, 3));
    }

    //insert AI code after this line



}
