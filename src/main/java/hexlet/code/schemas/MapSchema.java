package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class MapSchema extends BaseSchema {

    public MapSchema() {
        super();
    }

    @Override
    public boolean isValid(Object obj) {
        if (getValidations().isEmpty()) {
            return Objects.isNull(obj);
        }
        boolean validStatus;
        for (String validation : getValidations().keySet()) {
            switch (validation) {
                case "required" -> {
                    validStatus = obj instanceof Map<?, ?>;
                    if (!validStatus) {
                        return false;
                    }
                }
                case "size" -> {
                    validStatus = obj instanceof Map<?, ?> && ((Map<?, ?>) obj).size()
                            == (Integer) getValidations().get("size");
                    if (!validStatus) {
                        return false;
                    }
                }
                case "shape" -> {
                    if (!(obj instanceof Map<?, ?>)) {
                        return false;
                    }
                    @SuppressWarnings("unchecked") Map<String, BaseSchema> schemas
                            = (Map<String, BaseSchema>) getValidations().get("shape");
                    Optional<Boolean> optional = ((Map<?, ?>) obj).keySet().stream()
                            .map(k -> schemas.get(k).isValid(((Map<?, ?>) obj).get(k)))
                            .reduce((b1, b2) -> b1 && b2);
                    return optional.orElse(false);
                }
                default -> { }
            }
        }
        return true;
    }

    public MapSchema sizeof(int size) {
        if (size < 0) {
            throw new RuntimeException("\"sizeof\" method parameter cannot be less than 0");
        }
        getValidations().put("size", size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        getValidations().put("shape", schemas);
        return this;
    }

}
