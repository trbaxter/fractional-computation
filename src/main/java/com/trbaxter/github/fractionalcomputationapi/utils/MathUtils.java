package com.trbaxter.github.fractionalcomputationapi.utils;

import org.apache.commons.math3.special.Gamma;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    private MathUtils() {
        throw new UnsupportedOperationException("Utility class for math operations");
    }

    public static BigDecimal gamma(BigDecimal z) {
        try {
            double value = z.doubleValue();
            return BigDecimal.valueOf(Gamma.gamma(value)).setScale(15, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for gamma function: " + z, e);
        }
    }
}