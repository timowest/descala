package descala;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class TestUtils {

    public static ClassNode toNode(String resource) throws IOException {
        return toNode(TestUtils.class.getResourceAsStream(resource));
    }

    private static ClassNode toNode(InputStream in) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode node = new ClassNode();
        cr.accept(node, ClassReader.EXPAND_FRAMES);
        return node;
    }

    private static String toString(ASMifier asMifier) {
        StringBuilder builder = new StringBuilder();
        for (Object line : asMifier.getText()) {
            if (line instanceof List) {
                for (Object l : ((List)line)) {
                    builder.append(l);
                }
            } else {
                builder.append(line);
            }
        }
        String rv = builder.toString().trim();
        // visitMaxs is recomputed in ClassWriter
        return rv.replaceAll("visitMaxs\\(\\d+, \\d+\\)", "visitMaxs(X, X)");
    }

    public static String toASM(ClassNode n) {
        ASMifier asMifier = new ASMifier();
        ClassVisitor v = new TraceClassVisitor(null, asMifier, null);
        n.accept(v);
        return toString(asMifier);
    }

    public static String toASM(MethodNode n) {
        ASMifier asMifier = new ASMifier();
        MethodVisitor v = new TraceMethodVisitor(null, asMifier);
        n.accept(v);
        return toString(asMifier);
    }

    private TestUtils() {}
}
