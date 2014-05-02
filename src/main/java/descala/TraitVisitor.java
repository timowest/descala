package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * TraitVisitor removes trait initialization calls from classes
 */
public class TraitVisitor extends ClassVisitor implements Opcodes {

    public TraitVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
           String[] interfaces) {
        List<String> ifaces = new ArrayList<>();
        for (String iface : interfaces) {
            if (!iface.endsWith("Support")) {
                ifaces.add(iface);
            }
        }
        super.visit(version, access, name, signature, superName,
                ifaces.toArray(new String[ifaces.size()]));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (!name.equals("$init$")) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            }
        };
    }
}
