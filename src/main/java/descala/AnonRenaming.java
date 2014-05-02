package descala;

import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Renames anonymous classes to a style that javac uses
 */
public class AnonRenaming {

    private static final Pattern ANONFUN = Pattern.compile("\\$\\$anonfun(\\$[a-zA-Z]+)?");

    private static final Pattern ANON = Pattern.compile("\\$\\$anon");

    public static Set<ClassNode> visit(Set<ClassNode> classes) {
        Set<String> sortedNames = new TreeSet<>();
        for (ClassNode node : classes) sortedNames.add(node.name);

        final Map<String, String> newNames = getNewNames(sortedNames);

        Remapper remapper = new AbstractRemapper() {
            @Override
            protected String normalizeType(String type) {
                if (type != null && type.contains("$$")) {
                    for (Map.Entry<String, String> entry : newNames.entrySet()) {
                        type = type.replace(entry.getKey(), entry.getValue());
                    }
                }
                return type;
            }
        };
        Set<ClassNode> result = new HashSet<>(classes.size());
        for (ClassNode value : classes) {
            ClassNode node = new ClassNode();
            value.accept(new RemappingClassAdapter(node, remapper));
            result.add(node);
        }
        return result;
    }

    // TODO simplify
    public static Map<String, String> getNewNames(Set<String> sortedNames) {
        TreeMap<String, String> newNames = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String className : sortedNames) {
            if (className.contains("$$")) {
                String name = ANONFUN.matcher(className).replaceAll("");
                name = ANON.matcher(name).replaceAll("");
                if (newNames.containsValue(name)) {
                    // replace suffix
                    String prefix = name.substring(0, name.lastIndexOf('$'));
                    int suffix = Integer.parseInt(name.substring(name.lastIndexOf('$')+1));
                    while (newNames.containsValue(prefix + "$" + suffix)) {
                        suffix++;
                    }
                    name = prefix + "$" + suffix;
                } else {
                    // find longest prefix and replace
                    Map.Entry<String, String> lowerEntry = newNames.lowerEntry(className);
                    if (lowerEntry != null) {
                        name = className.replace(lowerEntry.getKey(), newNames.get(lowerEntry.getValue()));
                        name = ANONFUN.matcher(name).replaceAll("");
                        name = ANON.matcher(name).replaceAll("");
                    }
                }
                newNames.put(className, name);
            }
        }
        return newNames;
    }
}
