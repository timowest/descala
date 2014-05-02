package descala;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

public class MergeObjects implements Opcodes {

    static class MergeMethodVisitor extends MethodVisitor {

        private final String owner, target;

        public MergeMethodVisitor(String owner, MethodVisitor visitor) {
            this(owner, owner, visitor);
        }

        public MergeMethodVisitor(String owner, String target, MethodVisitor visitor) {
            super(ASM5, visitor);
            this.owner = owner;
            this.target = target;
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (opcode == PUTSTATIC && name.equals("MODULE$")) {
                return;
            }
            if (owner.equals(this.owner)) {
                owner = this.target;
                switch (opcode) {
                    case GETFIELD: opcode = GETSTATIC; break;
                    case PUTFIELD: opcode = PUTSTATIC; break;
                }
            }
            super.visitFieldInsn(opcode, owner, name, desc);
        }

        @Override
        public void visitLocalVariable(String name, String desc, String signature,
                                       Label start, Label end, int index) {
            if (!name.equals("this")) {
                super.visitLocalVariable(name, desc, signature, start, end, index - 1);
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            // TODO improve <init> check
            if (name.equals("<init>") && (owner.equals(this.owner) || owner.equals
                    ("java/lang/Object"))) {
                return;
            }
            if (owner.equals(this.owner)) {
                owner = this.target;
                opcode = INVOKESTATIC;
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode != NEW || !type.equals(owner)) {
                super.visitTypeInsn(opcode, type);
            }
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            if (var > 0) {
                super.visitVarInsn(opcode, var - 1);
            }
        }

    }

    public static Set<ClassNode>  visit(Set<ClassNode> classes) {
        Map<String, ClassNode> mapped = new HashMap<>();
        for (ClassNode node : classes) {
            mapped.put(node.name, node);
        }
        for (ClassNode node : classes) {
            if (node.name.endsWith("$")) {
                ClassNode parent = mapped.get(node.name.substring(0, node.name.length() - 1));
                if (parent != null) {
                    for (FieldNode field : ((List<FieldNode>)node.fields)) {
                        if (!field.name.equals("MODULE$")) {
                            FieldNode copy = new FieldNode(field.access + ACC_STATIC,
                                    field.name, field.desc, field.signature, field.value);
                            copy.visibleAnnotations = field.visibleAnnotations;
                            parent.fields.add(copy);
                        }
                    }

                    for (MethodNode method : ((List<MethodNode>)node.methods)) {
                        boolean c = method.name.equals("<init>") || method.name.equals("<clinit>");
                        MethodNode copy = new MethodNode(
                                c ? ACC_STATIC : (method.access + ACC_STATIC),
                                c ? "<clinit>" : method.name,
                                method.desc, method.signature,
                                ((List<String>)method.exceptions).toArray(new String[0]));
                        boolean found = false;
                        for (MethodNode parentMethod : ((List<MethodNode>)parent.methods)) {
                            if (parentMethod.name.equals(method.name)
                                && !c
                                && parentMethod.desc.equals(method.desc)
                                && Objects.equals(parentMethod.signature, method.signature)) {
                                found = true;
                                method.accept(new MergeMethodVisitor(node.name, parent.name,
                                        copy));
                                parentMethod.instructions = copy.instructions;
                            }
                        }
                        if (!found) {
                            method.accept(new MergeMethodVisitor(node.name, parent.name,
                                    copy));
                            if (copy.instructions.size() > 2) {
                                parent.methods.add(copy);
                            }
                        }
                    }
                }
            }
        }
        return classes;
    }

}
