package descala;

import org.junit.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static descala.TestUtils.toASM;
import static org.junit.Assert.assertEquals;


public class MethodsTest implements Opcodes{

    public static void main(String[] args) throws Exception {
        ClassNode node = TestUtils.toNode("/descala/Methods.class");
        System.out.println(toASM(node));
    }

    public MethodNode do1() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "do1", "()V", null, null);
        //mv = cw.visitMethod(ACC_PUBLIC, "do1", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("do1");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "Ldescala/Methods;", null, l0, l2, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do2() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        //mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "do2", "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("do1");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label(); // XXX
        mv.visitLabel(l2); // XXX
        mv.visitMaxs(2, 0);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do3() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "do1", "()Ljava/lang/String;", null, null);
        //mv = cw.visitMethod(ACC_PUBLIC, "do3", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLdcInsn("do1");
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Methods;", null, l0, l1, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do4() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()Ljava/lang/String;",
                null, null);
        //mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "do4", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLdcInsn("do1");
        mv.visitInsn(ARETURN);
        Label l1 = new Label(); // XXX
        mv.visitLabel(l1); // XXX
        mv.visitMaxs(1, 0);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do5() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "do1", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "descala/Methods", "a", "Ljava/lang/String;");
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Methods;", null, l0, l1, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do6() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()Ljava/lang/String;",
                null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "descala/Methods", "a", "Ljava/lang/String;");
        mv.visitInsn(ARETURN);
        Label l1 = new Label(); // XXX
        mv.visitLabel(l1); // XXX
        mv.visitMaxs(1, 0);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do7() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "do1", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitFieldInsn(PUTFIELD, "descala/Methods", "a", "Ljava/lang/String;");
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "Ldescala/Methods;", null, l0, l2, 0);
        mv.visitLocalVariable("b", "Ljava/lang/String;", null, l0, l2, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do8() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do8",
                "(Ljava/lang/String;)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(PUTSTATIC, "descala/Methods", "a", "Ljava/lang/String;");
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("b", "Ljava/lang/String;", null, l0, l2, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do9() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "do1",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "descala/Methods", "b", "Ljava/lang/StringBuilder;");
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Methods;", null, l0, l1, 0);
        mv.visitLocalVariable("content", "Ljava/lang/String;", null, l0, l1, 1);
        mv.visitLocalVariable("salt", "Ljava/lang/String;", null, l0, l1, 2);
        mv.visitMaxs(2, 3);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do10() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "descala/Methods", "b", "Ljava/lang/StringBuilder;");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("content", "Ljava/lang/String;", null, l0, l1, 0);
        mv.visitLocalVariable("salt", "Ljava/lang/String;", null, l0, l1, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        return mv;
    }

    @Test
    public void test12() {
        MethodNode m1 = do1();
        MethodNode m2 = do2();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m1.accept(new MergeObjects.MergeMethodVisitor("descala/Methods", mv));
        assertEquals(toASM(m2), toASM(mv));
    }

    @Test
    public void test34() {
        MethodNode m3 = do3();
        MethodNode m4 = do4();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m3.accept(new MergeObjects.MergeMethodVisitor("descala/Methods", mv));
        assertEquals(toASM(m4), toASM(mv));
    }

    @Test
    public void test56() {
        MethodNode m5 = do5();
        MethodNode m6 = do6();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m5.accept(new MergeObjects.MergeMethodVisitor("descala/Methods", mv));
        assertEquals(toASM(m6), toASM(mv));
    }

    @Test
    public void test78() {
        MethodNode m7 = do7();
        MethodNode m8 = do8();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m7.accept(new MergeObjects.MergeMethodVisitor("descala/Methods", mv));
        assertEquals(toASM(m8), toASM(mv));
    }

    @Test
    public void test910() {
        MethodNode m9 = do9();
        MethodNode m10 = do10();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m9.accept(new MergeObjects.MergeMethodVisitor("descala/Methods", mv));
        assertEquals(toASM(m10), toASM(mv));
    }
}
