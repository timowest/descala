package descala;

import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionVisitor extends ClassVisitor implements Opcodes {

    private static final String SUFFIX = "Ljava/io/Serializable;";

    public FunctionVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        if (signature != null && signature.contains("Function")) {
            if (signature.endsWith(SUFFIX)) {
                // remove Serializable
                signature = signature.substring(0, signature.length() - SUFFIX.length());
            }
            List<String> ifaces = new ArrayList<>(Arrays.asList(interfaces));
            ifaces.remove("java/io/Serializable");
            interfaces = ifaces.toArray(new String[ifaces.size()]);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature,
                                   Object value) {
        if (name.equals("serialVersionUID")) {
            return null;
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (name.equals("serialVersionUID")) {
                    return;
                }
                super.visitFieldInsn(opcode, owner, name, desc);
            }
        };
    }
}
