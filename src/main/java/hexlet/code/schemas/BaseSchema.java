package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema {

    private boolean hasRequired = false;

    public final boolean getStatusRequired() {
        return this.hasRequired;
    }

    private final List<Predicate<Object>> validations = new ArrayList<>();

    public abstract BaseSchema required();

    public final void setRequiredFor(Class<?> expectedType) {
        Predicate<Object> nonNull = expectedType::isInstance;
        this.validations.add(nonNull);
        this.hasRequired = true;
    }

    public final void add(Predicate<Object> predicate) {
        this.validations.add(predicate);
    }

    /**
     * Returns a boolean value after checking the passed parameter by the chain of validators.
     *
     * @param   obj the Object to validate along the chain of validators
     * @return      the boolean value
     */
    public boolean isValid(Object obj) {
        if (!hasRequired && Objects.isNull(obj)) {
            return true;
        } else if (!hasRequired) {
            required();
        }
        for (Predicate<Object> predicate : this.validations) {
            if (!predicate.test(obj)) {
                return false;
            }
        }
        return true;
    }

}
