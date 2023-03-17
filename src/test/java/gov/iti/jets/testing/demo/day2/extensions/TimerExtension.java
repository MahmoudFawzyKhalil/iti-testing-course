package gov.iti.jets.testing.demo.day2.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static java.lang.System.currentTimeMillis;
import static org.junit.platform.commons.support.AnnotationSupport.isAnnotated;

public class TimerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create("gov", "iti", "jets", "TimerExtension");

    @Override
    public void beforeTestExecution(ExtensionContext context) {
//        if (!shouldBeBenchmarked(context))
//            return;

        storeNowAsLaunchTime(context);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
//        if (!shouldBeBenchmarked(context))
//            return;

        long launchTime = loadLaunchTime(context);
        long elapsedTime = currentTimeMillis() - launchTime;
        report(context, elapsedTime);
    }

    private static boolean shouldBeBenchmarked(ExtensionContext context) {
        return context.getElement()
                      .map(el -> isAnnotated(el, Timed.class))
                      .orElse(false);
    }

    private static void storeNowAsLaunchTime(ExtensionContext context) {
        context.getStore(NAMESPACE).put(StartTimeKey.TEST, currentTimeMillis());
    }

    private static long loadLaunchTime(ExtensionContext context) {
        return context.getStore(NAMESPACE).get(StartTimeKey.TEST, long.class);
    }

    private static void report(ExtensionContext context, long elapsedTime) {
        String message = String.format("Test '%s' took %d ms.", context.getDisplayName(), elapsedTime);
        context.publishReportEntry("timing", message);
    }

    private enum StartTimeKey {
        TEST
    }
}