package descala;

import org.objectweb.asm.commons.Remapper;

public abstract class AbstractRemapper extends Remapper {

    protected abstract String normalizeType(String type);

    @Override
    public String mapType(String type) {
        return super.mapType(normalizeType(type));
    }

    @Override
    public String[] mapTypes(String[] types) {
        for (int i = 0; i < types.length; i++) {
            types[i] = normalizeType(types[i]);
        }
        return super.mapTypes(types);
    }

    @Override
    public String mapDesc(String desc) {
        return super.mapDesc(normalizeType(desc));
    }

    @Override
    public String mapSignature(String signature, boolean typeSignature) {
        return super.mapSignature(normalizeType(signature), typeSignature);
    }
}
