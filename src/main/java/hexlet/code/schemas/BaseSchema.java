package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema {

    private final Map<String, Predicate<Object>> validations = new HashMap<>();

    public final Map<String, Predicate<Object>> getValidations() {
        return new HashMap<>(this.validations);
    }

    public abstract BaseSchema required();

    // По замечанию: немного упростил свою первую реализацию, но в целом хочу попробовать отстоять свое решение:
    // По моему решению, использующему реализованную библиотеку, не придется придумывать самому имя для
    // валидатора (validatorName), достаточно просто передать один параметр (предикат) в метод add
    // и не вдаваться в то, как реализован метод add внутри.
    // Также в isValid я проверяю наличие абстрактного метода required в мапе validations по ключу String "required"
    // и если добавлять параметр validatorName, то я должен быть уверен, что при реализации этого метода,
    // в add будет передан именно validatorName = String "required",
    // то есть шанс неправильного использования матода add кратно возрастет
    public final void add(Predicate<Object> predicate) {
        StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        StackWalker.StackFrame methodName = stackWalker.walk(
                stream1 -> stream1
                        .skip(1)
                        .findFirst()
                        .orElse(null));
        String key = methodName.getMethodName();
        this.validations.put(key, predicate);
    }

    /**
     * Returns a boolean value after checking the passed parameter by the chain of validators.
     *
     * @param   obj the Object to validate along the chain of validators
     * @return      the boolean value
     */
    public boolean isValid(Object obj) {
        boolean hasRequired = this.validations.containsKey("required");
        if (Objects.isNull(obj) && !hasRequired) {
            return true;
        }
        required();
        boolean isValidate = true;
        for (Predicate<Object> predicate : this.validations.values()) {
            if (!predicate.test(obj)) {
                isValidate = false;
                break;
            }
        }
        if (!hasRequired) {
            this.validations.remove("required");
        }
        return isValidate;
    }

}
