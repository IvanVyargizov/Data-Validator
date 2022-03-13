package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema {

    private final Map<String, Predicate<Object>> validations;

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
        this.validations.clear();
        this.validations.put(id, predicate);
    }

    @SuppressWarnings("checkstyle:designforextension")
    public boolean isValid(Object obj) {
        if (this.validations.isEmpty()) {
            if (Objects.isNull(obj)) {
                return true;
            } else {
                required();
                Predicate<Object> pr = this.validations.values().stream().toList().get(0);
                this.validations.clear();
                return pr.test(obj);
            }
        }
        for (Predicate<Object> predicate : this.validations.values()) {
            if (!predicate.test(obj)) {
                return false;
            }
        }
        return true;
    }

}
