package descala;

import org.junit.Test;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static descala.TestUtils.toASM;
import static org.junit.Assert.assertEquals;

public class IdTest implements Opcodes {

    public static void main(String[] args) throws Exception {
        ClassNode node = TestUtils.toNode("/descala/Id.class");
        System.out.println(toASM(node));
    }

    public MethodNode do1() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "get", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "descala/Id", "regex", "Ljava/util/regex/Pattern;");
        mv.visitMethodInsn(INVOKESTATIC, "java/util/UUID", "randomUUID", "()Ljava/util/UUID;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/UUID", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", false);
        mv.visitLdcInsn("");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "replaceAll", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Id;", null, l0, l1, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do2() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "get", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitFieldInsn(GETSTATIC, "descala/Id", "regex", "Ljava/util/regex/Pattern;");
        mv.visitMethodInsn(INVOKESTATIC, "java/util/UUID", "randomUUID", "()Ljava/util/UUID;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/UUID", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", false);
        mv.visitLdcInsn("");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "replaceAll", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label(); // XXX
        mv.visitLabel(l1); // XXX
        mv.visitMaxs(2, 0);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do3() {
        MethodNode mv = new MethodNode(ACC_PUBLIC, "get2", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "descala/Id", "regex", "()Ljava/util/regex/Pattern;", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/UUID", "randomUUID", "()Ljava/util/UUID;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/UUID", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", false);
        mv.visitLdcInsn("");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "replaceAll", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "Ldescala/Id;", null, l0, l1, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
        return mv;
    }

    public MethodNode do4() {
        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "get2",
                "()Ljava/lang/String;", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitMethodInsn(INVOKESTATIC, "descala/Id", "regex", "()Ljava/util/regex/Pattern;", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/util/UUID", "randomUUID", "()Ljava/util/UUID;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/UUID", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Pattern", "matcher", "(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;", false);
        mv.visitLdcInsn("");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/regex/Matcher", "replaceAll", "(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        Label l1 = new Label(); // XXX
        mv.visitLabel(l1); // XXX
        mv.visitMaxs(2, 0);
        mv.visitEnd();
        return mv;
    }

    @Test
    public void test12() {
        MethodNode m1 = do1();
        MethodNode m2 = do2();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m1.accept(new MergeObjects.MergeMethodVisitor("descala/Id", mv));
        assertEquals(toASM(m2), toASM(mv));
    }

    @Test
    public void test34() {
        MethodNode m3 = do3();
        MethodNode m4 = do4();

        MethodNode mv = new MethodNode(ACC_PUBLIC + ACC_STATIC, "do1", "()V", null, null);
        m3.accept(new MergeObjects.MergeMethodVisitor("descala/Id", mv));
        assertEquals(toASM(m4), toASM(mv));
    }
}
