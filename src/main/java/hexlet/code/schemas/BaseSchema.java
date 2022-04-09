package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema {

    private final List<Predicate<Object>> validations = new ArrayList<>();

    public abstract BaseSchema required();

    /**
     * Adds a predicate to the chain of validators.
     *
     * @param predicate the Predicate to add to the chain of validators
     */
    public final void add(Predicate<Object> predicate) {
        validations.add(predicate);
    }

    /**
     * Returns a boolean value after checking the passed parameter by the chain of validators.
     *
     * @param   obj the Object to validate along the chain of validators
     * @return      the boolean value
     */
    public final boolean isValid(Object obj) {
        for (Predicate<Object> predicate : validations) {
            if (!predicate.test(obj)) {
                return false;
            }
        }
        return true;
    }

}
