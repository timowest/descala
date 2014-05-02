package descala;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

public final class Descala {

    public static void process(String inJar, String outJar) throws Exception {
        Set<ClassNode> classes = ZipUtils.getClasses(inJar);
        classes = AnonRenaming.visit(classes);
        classes = MergeObjects.visit(classes);
        ScalaRemapper remapper = new ScalaRemapper();

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outJar));
        try {
            for (ClassNode node : classes) {
                if (node.name.endsWith("$class")) {
                    continue;
                }
                ZipEntry ze = new ZipEntry(node.name + ".class");
                out.putNextEntry(ze);

                ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
                ClassVisitor v;
                v = new CheckClassAdapter(cw, false);
                v = new LombokVisitor(v, Collections.singleton("Ljavax/persistence/Entity;"));
                v = new ScalaSignatureVisitor(v);
                v = new AnonVisitor(v);
                v = new FunctionVisitor(v);
                v = new RemappingClassAdapter(v, remapper);
                v = new AutowiredVisitor(v);
                v = new TraitVisitor(v);
                v = new AccessorVisitor(v);
                v = new ScalaObjectVisitor(v); // FIXME
                v = new CaseClassVisitor(v);
                v = new BoxingVisitor(v);
                v = new CleanupVisitor(v);
                node.accept(v);
                out.write(cw.toByteArray());
            }
        } finally {
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {
        process(args[0], args[1]);
    }

    private Descala() {}

}
