package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Various cleanup operations
 */
public class CleanupVisitor extends ClassVisitor implements Opcodes {

    public CleanupVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        if (name.endsWith("AsScala") || name.contains("$_setter_$")) {
            return null;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

}
