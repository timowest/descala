package descala;

import org.objectweb.asm.tree.ClassNode;

import static descala.TestUtils.toASM;

public class PrimitivesTest {

    public int fromBoxed(Integer i) {
        return i.intValue();
    }

    public Integer toBoxed(int i) {
        return Integer.valueOf(i);
    }

    public static void main(String[] args) throws Exception {
        ClassNode node = TestUtils.toNode("/descala/PrimitivesTest.class");
        System.out.println(toASM(node));
    }
}
