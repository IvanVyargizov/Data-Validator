package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    @Override
    public MapSchema required() {
        setRequiredFor(Map.class);
        return this;
    }

    public MapSchema sizeof(int size) {
        if (size < 0) {
            throw new RuntimeException("\"sizeof\" method parameter cannot be less than 0");
        }
        Predicate<Object> isEqualSize = obj -> obj instanceof Map<?, ?> && ((Map<?, ?>) obj).size() == size;
        add(isEqualSize);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        Predicate<Object> isValid = obj -> obj instanceof Map<?, ?> && schemas.entrySet().stream()
                .allMatch(check -> {
                    final Object key = check.getKey();
                    final Object value = ((Map<?, ?>) obj).get(key);
                    final BaseSchema validator = check.getValue();
                    return validator.isValid(value);
                });
        add(isValid);
        return this;
    }

}
