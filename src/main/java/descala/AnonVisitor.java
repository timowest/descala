package descala;

import org.objectweb.asm.*;

public class AnonVisitor extends ClassVisitor implements Opcodes {

    private boolean isAnon;

    public AnonVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        if (name.charAt(name.length()-2) == '$' && Character.isDigit(name.charAt(name.length()-1))) {
            isAnon = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature,
                                   Object value) {
        if (isAnon && name.contains("$")) {
            // add synthetic
            if ((access & ACC_SYNTHETIC) == 0) {
                access += ACC_SYNTHETIC;
            }
            // remove private
            if ((access & ACC_PRIVATE) > 0) {
                access -= ACC_PRIVATE;
            }
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        if (isAnon && name.equals("<init>")) {
            access = 0;
        }
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitLocalVariable(String name, String desc, String signature,
                                           Label start, Label end, int index) {
                if (name.endsWith("$1")) {
                    name = name.substring(0, name.length() - 2);
                }
                super.visitLocalVariable(name, desc, signature, start, end, index);
            }
        };
    }

}
