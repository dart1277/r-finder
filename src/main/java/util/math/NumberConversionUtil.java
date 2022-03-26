package util.math;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberConversionUtil {

    public static Double mapDouble(List<Double> doubleList, int position) {
        if (doubleList.size() <= position || Objects.isNull(doubleList.get(position))) {
            return 0d;
        }
        return doubleList.get(position);
    }

}
