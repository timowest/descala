package descala;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.*;

/**
 * Adds class level Getter/Setter lombok annotations and removes accessors
 */
public class LombokVisitor extends ClassVisitor implements Opcodes {

    private boolean lombokify;

    private final Set<String> fields = new HashSet<>();

    private final Set<String> annotationDescs;

    public LombokVisitor(ClassVisitor classVisitor, Set<String> annotations) {
        super(ASM5, classVisitor);
        this.annotationDescs = annotations;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (annotationDescs.contains(desc)) {
            if (!lombokify) {
                visitAnnotation("Llombok/Getter;", true).visitEnd();
                visitAnnotation("Llombok/Setter;", true).visitEnd();
                lombokify = true;
            }
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature,
                                   Object value) {
        if (lombokify && (access & ACC_STATIC) == 0) {
            fields.add(name);
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        if (lombokify && (name.startsWith("get") || name.startsWith("set"))
            && name.length() > 3
            && fields.contains(BeanUtils.uncapitalize(name.substring(3)))) {
            return null;
        } else {
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

}
