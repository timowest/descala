package descala;

import org.junit.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static descala.TestUtils.toASM;
import static org.junit.Assert.assertEquals;

public class InitsTest implements Opcodes {

    public static void main(String[] args) throws Exception {
        ClassNode node = TestUtils.toNode("/descala/Inits.class");
        System.out.println(toASM(node));
    }

    public MethodNode do1() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitFieldInsn(PUTFIELD, "descala/Inits", "i", "J");
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "Ldescala/Inits;", null, l0, l2, 0);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
        return mv;
    }

    private MethodNode do2() {
        MethodNode mv = new MethodNode(ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        Label l1 = new Label(); // XXX
        mv.visitLabel(l1); // XXX
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitFieldInsn(PUTSTATIC, "descala/Inits", "i", "J");
        mv.visitInsn(RETURN);
        Label l2 = new Label(); // XXX
        mv.visitLabel(l2); // XXX
        mv.visitMaxs(2, 0);
        mv.visitEnd();
        return mv;
    }

    @Test
    public void test12() {
        MethodNode m1 = do1();
        MethodNode m2 = do2();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "<clinit>", "()V", null, null);
        m1.accept(new MergeObjects.MergeMethodVisitor("descala/Inits", mv));
        assertEquals(toASM(m2), toASM(mv));
    }

}
