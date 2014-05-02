package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.*;

/**
 * CaseClassVisitor simplifies cases classes
 */
public class CaseClassVisitor extends ClassVisitor implements Opcodes {

    private static final Set<String> skipped = new HashSet<>(Arrays.asList("canEqual",
            "copy",
            "curried",
            "productArity",
            "productElement",
            "productIterator",
            "productPrefix",
            "tupled",
            "equals",
            "hashCode",
            "toString"));

    private boolean caseClass;

    public CaseClassVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        List<String> ifaces = new ArrayList<>();
        for (String iface : interfaces) {
            if (iface.equals("scala/Product")) {
                caseClass = true;
            } else {
                ifaces.add(iface);
            }
        }
        super.visit(version, access, name, signature, superName,
                ifaces.toArray(new String[ifaces.size()]));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        if (caseClass && (name.contains("$default$") || skipped.contains(name))) {
            return null;
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
