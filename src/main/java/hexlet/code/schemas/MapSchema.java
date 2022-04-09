package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {

    @Override
    public MapSchema required() {
        add(obj -> obj instanceof Map);
        return this;
    }

    public MapSchema sizeof(int size) {
        if (size < 0) {
            throw new RuntimeException("\"sizeof\" method parameter cannot be less than 0");
        }
        add(obj -> obj instanceof Map && ((Map<?, ?>) obj).size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        add(
                obj -> obj instanceof Map<?, ?> && schemas.entrySet().stream()
                        .allMatch(check -> {
                            final Object key = check.getKey();
                            final Object value = ((Map<?, ?>) obj).get(key);
                            final BaseSchema validator = check.getValue();
                            return validator.isValid(value);
                        })
        );
        return this;
    }

}
