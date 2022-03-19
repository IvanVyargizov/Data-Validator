package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema {

    private final Map<String, Predicate<Object>> validations;

    private String idRequired;

    public final Map<String, Predicate<Object>> getValidations() {
        return new HashMap<>(this.validations);
    }

    public BaseSchema() {
        this.validations = new HashMap<>();
    }

    public abstract BaseSchema required();

    public final void add(Predicate<Object> predicate) {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        StackWalker.StackFrame methodName = stackWalker.walk(
                stream1 -> stream1
                        .skip(1)
                        .findFirst()
                        .orElse(null));
        String id = this.getClass().getName() + methodName.getMethodName();
        if (id.equals(this.getClass().getPackageName() + "." + this.getClass().getSimpleName() + "required")) {
            this.idRequired = id;
        }
        this.validations.put(id, predicate);
    }

    /**
     * Returns a boolean value after checking the passed parameter by the chain of validators.
     *
     * @param   obj the Object to validate along the chain of validators
     * @return      the boolean value
     */
    public boolean isValid(Object obj) {
        int checker = 0;
        if (this.validations.isEmpty() || !this.validations.containsKey(this.idRequired)) {
            if (Objects.isNull(obj)) {
                return true;
            } else {
                required();
                checker = 1;
            }
        }
        boolean isValidate = true;
        for (Predicate<Object> predicate : this.validations.values()) {
            isValidate = predicate.test(obj);
            if (!isValidate) {
                break;
            }
        }
        if (checker == 1) {
            this.validations.remove(this.idRequired);
        }
        return isValidate;
    }

}
