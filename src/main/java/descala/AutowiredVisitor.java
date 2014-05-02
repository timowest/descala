package descala;

import org.objectweb.asm.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Remove accessors for Spring injected fields
 */
public class AutowiredVisitor extends ClassVisitor implements Opcodes {

    private final Set<String> fields = new HashSet<>();

    public AutowiredVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public FieldVisitor visitField(int access, final String name, String desc, String signature,
                                   Object value) {
        return new FieldVisitor(ASM5, super.visitField(access, name, desc, signature,
                value)) {
            @Override
            public AnnotationVisitor visitAnnotation(String desc,
                                                     boolean visible) {
                if (desc.equals("Lorg/springframework/beans/factory/annotation/Autowired;")) {
                    fields.add(name);
                }
                return super.visitAnnotation(desc, visible);
            }
        };
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {

        if (fields.contains(name)
            || (name.length() > 3 && fields.contains(BeanUtils.uncapitalize(name.substring(3))))) {
            return null;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

}
