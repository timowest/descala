package descala;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ZipUtils {

    public static Set<ClassNode> getClasses(String jarPath) throws IOException {
        Set<ClassNode> classes = new HashSet<>();
        ZipFile zip = new ZipFile(jarPath);
        try {
            for (Enumeration<? extends ZipEntry> list = zip.entries(); list.hasMoreElements(); ) {
                ZipEntry entry = list.nextElement();
                if (entry.getName().endsWith(".class")) {
                    ClassReader cr = new ClassReader(zip.getInputStream(entry));
                    ClassNode cn = new ClassNode();
                    cr.accept(cn, ClassReader.EXPAND_FRAMES);
                    classes.add(cn);
                }
            }
        } finally {
            zip.close();
        }
        return classes;
    }

    private ZipUtils() {}
}
