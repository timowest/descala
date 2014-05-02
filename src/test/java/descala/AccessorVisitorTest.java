package descala;

import org.junit.Test;
import org.objectweb.asm.tree.ClassNode;

import static descala.TestUtils.toASM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessorVisitorTest {

    @Test
    public void test() throws Exception {
        ClassNode node = TestUtils.toNode("/descala/Accessors.class");
        String asm1 = toASM(node);

        ClassNode node2 = new ClassNode();
        node.accept(new AccessorVisitor(node2));
        String asm2 = toASM(node2);
        String i1 = "mv.visitMethodInsn(INVOKESPECIAL, \"descala/Accessors\", \"prop1\", \"()Ljava/lang/String;\", false);";
        String i2 = "mv.visitMethodInsn(INVOKESTATIC, \"descala/Accessors\", \"prop2\", \"()Ljava/lang/String;\", false);";

        assertTrue(asm1.contains(i1) && asm1.contains(i2));
        assertFalse(asm2.contains(i1) || asm2.contains(i2));
    }
}
