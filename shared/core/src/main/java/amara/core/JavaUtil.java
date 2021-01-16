package amara.core;

public class JavaUtil {

    public static final int MINIMUM_VERSION = 11;

    public static boolean isVersionSufficient() {
        return (getMajorVersion() >= MINIMUM_VERSION);
    }

    public static int getMajorVersion() {
        return Integer.parseInt(getVersion().split("\\.")[0]);
    }

    public static String getVersion() {
        return System.getProperty("java.version");
    }
}
