package descala;

public class Methods {

    public String a;

    public static String aStatic;

    public StringBuilder b;

    public static StringBuilder bStatic;

    public void do1() {
        System.out.println("do1");
    }

    public static void do2() {
        System.out.println("do2");
    }

    public String do3() {
        return "d03";
    }

    public static String do4() {
        return "do4";
    }

    public String do5() {
        return a;
    }

    public static String do6() {
        return aStatic;
    }

    public void do7(String b) {
        a = b;
    }

    public static void do8(String b) {
        aStatic = b;
    }

    public String do9(String content, String salt) {
        return this.b.append(content).append(salt).toString();
    }

    public static String do10(String content, String salt) {
        return bStatic.append(content).append(salt).toString();
    }


}
