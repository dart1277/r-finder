package util.math;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoundingUtil {

    public static double round(double value) {
        return Math.round(value * 1 / MathConstants.EPSILON) * MathConstants.EPSILON;
    }
}
