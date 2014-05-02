package descala;

import java.util.UUID;
import java.util.regex.Pattern;

public class Id {

    private Pattern regex = Pattern.compile("\\-");

    private Pattern regex() {
        return regex;
    }

    private static Pattern regexStatic = Pattern.compile("\\-");

    private static Pattern regexStatic() {
        return regexStatic;
    }

    public String get() {
        return this.regex.matcher(UUID.randomUUID().toString()).replaceAll("");
    }

    public String get2() {
        return this.regex().matcher(UUID.randomUUID().toString()).replaceAll("");
    }

    public static String getStatic() {
        return regexStatic.matcher(UUID.randomUUID().toString()).replaceAll("");
    }

    public static String getStatic2() {
        return regexStatic().matcher(UUID.randomUUID().toString()).replaceAll("");
    }

}
