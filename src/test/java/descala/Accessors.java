package descala;

/**
 * Created by tiwe on 8.4.2014.
 */
public class Accessors {

    private String prop1;

    private static String prop2;

    private String prop1() {
        return prop1;
    }

    private static String prop2() {
        return prop2;
    }

    public void do1() {
        System.out.println(prop1() != null);
    }

    public static void do2() {
        System.out.println(prop2() != null);
    }
}
