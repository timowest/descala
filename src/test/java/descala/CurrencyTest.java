package descala;

import org.junit.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static descala.TestUtils.toASM;
import static org.junit.Assert.assertEquals;

public class CurrencyTest implements Opcodes {

    public static void main(String[] args) throws Exception {
        ClassNode node = TestUtils.toNode("/descala/Currency.class");
        System.out.println(toASM(node));
    }

    private MethodNode do1() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "from", "(Ljava/math/BigDecimal;)" +
                "Ljava/math/BigDecimal;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitTypeInsn(NEW, "java/math/BigDecimal");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigDecimal", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ICONST_2);
        mv.visitFieldInsn(GETSTATIC, "java/math/RoundingMode", "HALF_UP", "Ljava/math/RoundingMode;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigDecimal", "setScale", "(ILjava/math/RoundingMode;)Ljava/math/BigDecimal;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Currency;", null, l0, l1, 0);
        mv.visitLocalVariable("value", "Ljava/math/BigDecimal;", null, l0, l1, 1);
        mv.visitMaxs(3, 2);
        mv.visitEnd();
        return mv;
    }

    private MethodNode do2() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "from",
                "(Ljava/math/BigDecimal;)Ljava/math/BigDecimal;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitTypeInsn(NEW, "java/math/BigDecimal");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigDecimal", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ICONST_2);
        mv.visitFieldInsn(GETSTATIC, "java/math/RoundingMode", "HALF_UP", "Ljava/math/RoundingMode;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/math/BigDecimal", "setScale", "(ILjava/math/RoundingMode;)Ljava/math/BigDecimal;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("value", "Ljava/math/BigDecimal;", null, l0, l1, 0);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
        return mv;
    }

    @Test
    public void test12() {
        MethodNode m1 = do1();
        MethodNode m2 = do2();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m1.accept(new MergeObjects.MergeMethodVisitor("descala/Currency", mv));
        assertEquals(toASM(m2), toASM(mv));
    }
}
