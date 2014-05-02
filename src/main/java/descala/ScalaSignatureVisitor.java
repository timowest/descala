package descala;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ScalaSignatureVisitor removes the ScalaSignature annotation
 */
public class ScalaSignatureVisitor extends ClassVisitor implements Opcodes {

    public ScalaSignatureVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals("Lscala/reflect/ScalaSignature;")) {
            return null;
        }
        return super.visitAnnotation(desc, visible);
    }
}
