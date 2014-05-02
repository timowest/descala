package descala;

import java.beans.Introspector;

public final class BeanUtils {

    public static String capitalize(String name) {
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1))) {
            return name;
        } else if (name.length() > 1) {
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        } else {
            return name.toUpperCase();
        }
    }

    public static String uncapitalize(String name) {
        return Introspector.decapitalize(name);
    }

    private BeanUtils() {}
}