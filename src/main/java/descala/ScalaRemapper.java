package descala;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ScalaRemapper replaces Scala types with Java types
 */
public class ScalaRemapper extends AbstractRemapper {

    private final Map<String, String> signatures = new LinkedHashMap<>();

    public ScalaRemapper() {
        Map<String, String> types = new LinkedHashMap<>();
        types.put("scala.collection.SeqLike", "java.util.Collection"); // ?
        types.put("scala.collection.Seq", "java.util.Collection"); // ?
        types.put("scala.collection.TraversableLike", "java.util.Collection"); // ?
        types.put("scala.collection.LinearSeqOptimized", "java.util.Collection"); // ?

        types.put("scala.collection.Iterable", "java.lang.Iterable");
        types.put("scala.collection.Iterator", "java.util.Iterator");
        types.put("scala.collection.Map", "java.util.Map");
        types.put("scala.collection.Set", "java.util.Set");
        types.put("scala.collection.SortedMap", "java.util.SortedMap");
    
        types.put("scala.collection.immutable.HashMap", "java.util.HashMap");
        types.put("scala.collection.immutable.List", "java.util.List");
        types.put("scala.collection.immutable.Map", "java.util.Map");
        types.put("scala.collection.immutable.Set", "java.util.Set");
        types.put("scala.collection.immutable.TreeMap", "java.util.TreeMap");
    
        types.put("scala.collection.mutable.HashMap", "java.util.HashMap");
        types.put("scala.collection.mutable.LinkedHashSet", "java.util.LinkedHashSet");
        types.put("scala.collection.mutable.LinkedList", "java.util.LinkedList");
        types.put("scala.collection.mutable.List", "java.util.List");
        types.put("scala.collection.mutable.Map", "java.util.Map");
        types.put("scala.collection.mutable.Set", "java.util.Set");
    
        types.put("scala.collection.mutable.StringBuilder", "java.lang.StringBuilder");

        types.put("scala.runtime.Null$", "java.lang.Void");
        types.put("scala.Serializable", "java.io.Serializable");

        types.put("scala.Function0", "java.util.function.Supplier");
        types.put("scala.Function1", "java.util.function.Function");
        types.put("scala.Function2", "java.util.function.BiFunction");
        types.put("scala.Option", "java.util.Optional");

        for (Map.Entry<String, String> entry : types.entrySet()) {
            signatures.put(entry.getKey().replace('.', '/'),
                    entry.getValue().replace('.', '/'));
        }
    }

    protected String normalizeType(String type) {
        if (type != null) {
            for (Map.Entry<String, String> entry : signatures.entrySet()) {
                type = type.replace(entry.getKey(), entry.getValue());
            }
        }
        return type;
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        if (name.equals("$outer")) { // for anon classes
            return "this$0";
        } else  if (name.endsWith("$1")) { // for anon classes
            return "val$" + name.substring(0, name.length() - 2);
        } else if (name.contains("$$")) {
            return name.substring(name.lastIndexOf("$$") + 2);
        } else {
            return super.mapFieldName(owner, name, desc);
        }
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        if (name.endsWith("_$eq")) {
            return "set" + BeanUtils.capitalize(name.substring(0, name.length() - 4));
        } else if (name.contains("$$")) {
            return name.substring(name.lastIndexOf("$$") + 2);
        } else {
            return super.mapMethodName(owner, name, desc);
        }
    }

    @Override
    public String mapInvokeDynamicMethodName(String name, String desc) {
        if (name.endsWith("_$eq")) {
            return "set" + BeanUtils.capitalize(name.substring(0, name.length() - 4));
        } else if (name.contains("$$")) {
            return name.substring(name.lastIndexOf("$$") + 2);
        } else {
            return super.mapInvokeDynamicMethodName(name, desc);
        }
    }

}
