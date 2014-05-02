package descala;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Converts Scala boxing code
 */
public class BoxingVisitor extends ClassVisitor implements Opcodes {

    public BoxingVisitor(ClassVisitor classVisitor) {
        super(ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature,
                exceptions)) {
            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (owner.equals("scala/runtime/BoxesRunTime")) {
                    switch (name) {
                        case "boxToBoolean":
                            owner = "java/lang/Boolean";
                            name = "valueOf";
                            break;
                        case "boxToCharacter":
                            owner = "java/lang/Character";
                            name = "valueOf";
                            break;
                        case "boxToByte":
                            owner = "java/lang/Byte";
                            name = "valueOf";
                            break;
                        case "boxToShort":
                            owner = "java/lang/Short";
                            name = "valueOf";
                            break;
                        case "boxToInteger":
                            owner = "java/lang/Integer";
                            name = "valueOf";
                            break;
                        case "boxToLong":
                            owner = "java/lang/Long";
                            name = "valueOf";
                            break;
                        case "boxToFloat":
                            owner = "java/lang/Float";
                            name = "valueOf";
                            break;
                        case "boxToDouble":
                            owner = "java/lang/Double";
                            name = "valueOf";
                            break;

                        case "unboxToBoolean":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Boolean";
                            name = "booleanValue";
                            desc = "()I";
                            break;
                        case "unboxToChar":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Character";
                            name = "charValue";
                            desc = "()I";
                            break;
                        case "unboxToByte":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Byte";
                            name = "byteValue";
                            desc = "()I";
                            break;
                        case "unboxToShort":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Short";
                            name = "shortValue";
                            desc = "()I";
                            break;
                        case "unboxToInt":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Integer";
                            name = "intValue";
                            desc = "()I";
                            break;
                        case "unboxToLong":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Long";
                            name = "longValue";
                            desc = "()I";
                            break;
                        case "unboxToDouble":
                            opcode = INVOKEVIRTUAL;
                            owner = "java/lang/Double";
                            name = "doubleValue";
                            desc = "()I";
                            break;
                    }
                }
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        };
    }

}
