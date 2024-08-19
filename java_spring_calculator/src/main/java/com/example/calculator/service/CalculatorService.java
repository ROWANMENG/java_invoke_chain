package com.example.calculator.service;

import org.springframework.stereotype.Service;

import java.lang.instrument.*;

@Service
public class CalculatorService {
    public double add(double a, double b) {
        return (a + b);
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    public double complexComp(double a, double b) {
        double c = divide(a, b);
        double res = multiply(c, a);

        return res;
    }
}
