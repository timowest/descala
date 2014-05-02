package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashSet;
import java.util.Set;

/**
 * ScalaObjectVisitor simplifies Scala object usage code
 */
public class ScalaObjectVisitor extends ClassVisitor implements Opcodes {

    private final Set<String> ownerTypes = new HashSet<>();

    public ScalaObjectVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (name.equals("MODULE$") && !owner.startsWith("scala/")) {
                    ownerTypes.add(owner);
                } else {
                    super.visitFieldInsn(opcode, owner, name, desc);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc,
                                        boolean itf) {
                if (owner.endsWith("$") && ownerTypes.contains(owner)) {
                    owner = owner.substring(0, owner.length() - 1);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, desc, itf);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            }
        };
    }
}
