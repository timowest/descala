package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * AccessorVisitor replaces Scala accessors with Java Bean accessors
 */
public class AccessorVisitor extends ClassVisitor implements Opcodes {

    private final Set<String> fields = new HashSet<>();

    private final Set<String> getters = new HashSet<>();

    private final Set<String> setters = new HashSet<>();

    private final Set<String> ownTypes = new HashSet<>();

    public AccessorVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        ownTypes.add(name);
        ownTypes.addAll(Arrays.asList(interfaces));
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature,
                                   Object value) {
        fields.add(name);
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        boolean accessor = true;
        // scala getter
        if (fields.contains(name)) {
            if (!getters.add(name)) return null;
            name = "get" + BeanUtils.capitalize(name);
        // scala setter
        } else if (name.endsWith("_$eq") && fields.contains(name.substring(0, name.length() - 4))) {
            String propertyName = name.substring(0, name.length() - 4);
            if (!setters.add(propertyName)) return null;
            name = "set" + BeanUtils.capitalize(propertyName);
        // java getter
        } else if (name.startsWith("get") && name.length() > 3 && fields.contains(BeanUtils
                .uncapitalize(name.substring(3)))) {
            String propertyName = BeanUtils.uncapitalize(name.substring(3));
            if (!getters.add(propertyName)) return null;
        // java setter
        } else if (name.startsWith("set") && name.length() > 3 && fields.contains(BeanUtils
                .uncapitalize(name.substring(3)))) {
            String propertyName = BeanUtils.uncapitalize(name.substring(3));
            if (!setters.add(propertyName)) return null;
        } else {
            accessor = false;
        }
        // private accessors are skipped
        if (accessor && (access & ACC_PRIVATE) > 0) {
            return null;
        }
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                // replace scala getter
                if (ownTypes.contains(owner) && fields.contains(name)) {
                    int op = (opcode == INVOKESTATIC) ? GETSTATIC : GETFIELD;
                    super.visitFieldInsn(op, owner, name, desc.substring(2));
                // replace scala setter
                } else if (ownTypes.contains(owner) && name.endsWith("_eq$")
                    && fields.contains(name.substring(0, name.length() - 4))) {
                    int op = (opcode == INVOKESTATIC) ? PUTSTATIC : PUTFIELD;
                    super.visitFieldInsn(op, owner, name, desc.substring(2));
                // replace java getter
                } else if (ownTypes.contains(owner) && name.startsWith("get") && name.length() > 3
                    && fields.contains(BeanUtils.uncapitalize(name.substring(3)))) {
                    int op = (opcode == INVOKESTATIC) ? GETSTATIC : GETFIELD;
                    String fieldName = BeanUtils.uncapitalize(name.substring(3));
                    super.visitFieldInsn(op, owner, fieldName, desc.substring(2));
                // replace java setter
                } else if (ownTypes.contains(owner) && name.startsWith("set") && name.length() > 3
                    && fields.contains(BeanUtils.uncapitalize(name.substring(3)))) {
                    int op = (opcode == INVOKESTATIC) ? PUTSTATIC : PUTFIELD;
                    String fieldName = BeanUtils.uncapitalize(name.substring(3));
                    super.visitFieldInsn(op, owner, fieldName, desc.substring(2));
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            }
        };
    }

}
