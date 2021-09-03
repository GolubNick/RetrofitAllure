package assertions;

import org.assertj.core.api.SoftAssertions;

public class SoftAssertProvider {

    private static final ThreadLocal<SoftAssertions> threadLocalSoftAssertions = new ThreadLocal<>();

    public static SoftAssertions getSoftAssert() {
        if (threadLocalSoftAssertions.get() == null) {
            threadLocalSoftAssertions.set(new SoftAssertions());
        }
        return threadLocalSoftAssertions.get();
    }

    public static void unsetSoftAssert() {
        threadLocalSoftAssertions.remove();
    }
}