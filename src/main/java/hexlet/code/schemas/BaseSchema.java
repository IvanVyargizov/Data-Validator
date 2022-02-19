package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseSchema {

    private final Map<String, Object> validations;

    public final Map<String, Object> getValidations() {
        return validations;
    }

    public BaseSchema() {
        this.validations = new HashMap<>();
    }

    public abstract boolean isValid(Object obj);

}
