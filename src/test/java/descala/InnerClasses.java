package descala;

public class InnerClasses {

    private String test;

    public void doSomething(final String arg, final String arg2, final int args) {
        new Runnable() {
            @Override
            public void run() {
                System.out.println(arg + arg2 + args + test);
            }
        }.run();;
    }

}
