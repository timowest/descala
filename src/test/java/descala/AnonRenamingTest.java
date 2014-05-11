package descala;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnonRenamingTest {

    @Test @Ignore
    public void test() {
        Map<String, String> names = new LinkedHashMap<>();
        names.put("ServiceClass$$anonfun$1", "ServiceClass$1");
        names.put("ServiceClass$$anonfun$2", "ServiceClass$2");
        names.put("ServiceClass$$anonfun$3", "ServiceClass$3");
        names.put("ServiceClass$$anonfun$4", "ServiceClass$4");
        names.put("ServiceClass$$anonfun$method$1", "ServiceClass$5");
        names.put("ServiceClass$$anonfun$method$1$$anonfun$apply$1", "ServiceClass$5$1");
        names.put("ServiceClass$$anonfun$methodX$1", "ServiceClass$6");

        names.put("ServiceClass2$$anonfun$method$1", "ServiceClass2$1");
        names.put("ServiceClass2$$anonfun$method$2", "ServiceClass2$2");
        names.put("ServiceClass2$$anonfun$method$3", "ServiceClass2$3");

        Map<String, String> newNames = AnonRenaming.getNewNames(names.keySet());
        assertEquals(names, newNames);
    }
}
