package tutorials;

import java.time.ZonedDateTime;

public class Log {

    public static void log(Class<?> cls, String level, String message, Object... args) {
        System.out.printf("%s;%s;%s;%s%n", ZonedDateTime.now(), cls.getCanonicalName(), level, message.formatted(args));
    }

    public static void info(Class<?> cls, String message, Object... args) {
        log(cls, "INFO", message, args);
    }

    public static void debug(Class<?> cls, int debugLevel, String message, Object... args) {
        if (App.isDebugGreaterThan(debugLevel)) {
            log(cls, "DEBUG", message, args);
        }
    }

    public static void error(Class<?> cls, String message, Object... args) {
        log(cls, "ERROR", message, args);
    }
}
