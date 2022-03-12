package hexlet.code.schemas;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    public MapSchema() {
        super();
    }

    @Override
    public MapSchema required() {
        Predicate<Object> nonNull = obj -> obj instanceof Map<?, ?>;
        add(nonNull);
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
        Predicate<Object> isValid = obj -> {
            if (!(obj instanceof Map<?, ?>)) {
                return false;
            }
            Optional<Boolean> optional = ((Map<?, ?>) obj).keySet().stream()
                    .map(k -> schemas.get(k).isValid(((Map<?, ?>) obj).get(k)))
                    .reduce((b1, b2) -> b1 && b2);
            return optional.orElse(false);
        };
        add(isValid);
        return this;
    }

}
