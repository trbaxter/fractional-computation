package com.trbaxter.github.fractionalcomputationapi.service;

import com.trbaxter.github.fractionalcomputationapi.utils.MathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RiemannLiouvilleDerivativeServiceTest {

    private RiemannLiouvilleDerivativeService riemannLiouvilleDerivativeService;

    @BeforeEach
    public void setUp() {
        riemannLiouvilleDerivativeService = new RiemannLiouvilleDerivativeService();
    }

    @Test
    public void testDerivative_SimplePolynomial() {
        double[] coefficients = {3.0, 2.0, 1.0};
        double alpha = 0.5;

        try (MockedStatic<MathUtils> utilities = Mockito.mockStatic(MathUtils.class)) {
            utilities.when(() -> MathUtils.gamma(3)).thenReturn(2.0); // Gamma(3) = 2!
            utilities.when(() -> MathUtils.gamma(2.5)).thenReturn(1.329);
            utilities.when(() -> MathUtils.gamma(2)).thenReturn(1.0); // Gamma(2) = 1!
            utilities.when(() -> MathUtils.gamma(1.5)).thenReturn(0.886);
            utilities.when(() -> MathUtils.gamma(1)).thenReturn(1.0);
            utilities.when(() -> MathUtils.gamma(0.5)).thenReturn(1.772);

            String result = riemannLiouvilleDerivativeService.computeDerivative(coefficients, alpha);
            String expected = "4.515x^1.500 + 2.257x^0.500 + 0.564x^-0.500";

            assertEquals(expected, result);
        }
    }

    @Test
    public void testComputeDerivative_NegativeCoefficients() {
        double[] coefficients = {-3.0, -2.0, -1.0};
        double alpha = 0.5;

        try (MockedStatic<MathUtils> utilities = Mockito.mockStatic(MathUtils.class)) {
            utilities.when(() -> MathUtils.gamma(3)).thenReturn(2.0);
            utilities.when(() -> MathUtils.gamma(2.5)).thenReturn(1.329);
            utilities.when(() -> MathUtils.gamma(2)).thenReturn(1.0);
            utilities.when(() -> MathUtils.gamma(1.5)).thenReturn(0.886);

            String result = riemannLiouvilleDerivativeService.computeDerivative(coefficients, alpha);
            String expected = "- 4.515x^1.500 - 2.257x^0.500";

            assertEquals(expected, result);
        }
    }

    @Test
    public void testComputeDerivative_ConstantTerm() {
        double[] coefficients = {3.0};
        double alpha = 0.5;

        String result = riemannLiouvilleDerivativeService.computeDerivative(coefficients, alpha);
        String expected = "1.693x^-0.500";

        assertEquals(expected, result);
    }

    @Test
    public void testComputeDerivative_ZeroPolynomial() {
        double[] coefficients = {0.0, 0.0, 0.0};
        double alpha = 0.5;

        String result = riemannLiouvilleDerivativeService.computeDerivative(coefficients, alpha);
        String expected = "";

        assertEquals(expected, result);
    }

    @Test
    public void testComputeDerivative_DifferentAlpha() {
        double[] coefficients = {3.0, 0.0, 1.0};
        double alpha = 1.5;

        try (MockedStatic<MathUtils> utilities = Mockito.mockStatic(MathUtils.class)) {
            utilities.when(() -> MathUtils.gamma(3)).thenReturn(2.0); // Gamma(3) = 2!
            utilities.when(() -> MathUtils.gamma(1.5)).thenReturn(0.886);

            String result = riemannLiouvilleDerivativeService.computeDerivative(coefficients, alpha);
            String expected = "6.772x^0.500";

            assertEquals(expected, result);
        }
    }
}