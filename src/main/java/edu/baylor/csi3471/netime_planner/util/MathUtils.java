package edu.baylor.csi3471.netime_planner.util;

public class MathUtils {
    public static int LCM(int a, int b) {
        if (a <= 0 || b <= 0)
            throw new IllegalArgumentException("a and b must be positive");
        return a * b / GCD(a, b);
    }

    // Uses Euclid's algorithm: https://en.wikipedia.org/wiki/Euclidean_algorithm
    public static int GCD(int a, int b) {
        if (b == 0)
            return a;
        else
            return GCD(b, a % b);
    }
}
