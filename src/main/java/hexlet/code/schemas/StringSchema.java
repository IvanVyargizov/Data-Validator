package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    public StringSchema() {
        super();
    }

    @Override
    public StringSchema required() {
        Predicate<Object> nonNull = obj -> obj instanceof String && !obj.toString().isEmpty();
        add(nonNull);
        return this;
    }

    @Override
    public boolean isValid(Object obj) {
        if ((getValidations().isEmpty() || getValidations().keySet().stream()
                .noneMatch(str -> str.contains("required")))
                && !Objects.isNull(obj) && obj.equals("")) {
            return true;
        }
        return super.isValid(obj);
    }

    public StringSchema minLength(int length) {
        if (length < 0) {
            throw new RuntimeException("\"minLength\" method parameter cannot be less than 0");
        }
        Predicate<Object> longerThat = obj -> obj instanceof String && obj.toString().length() >= length;
        add(longerThat);
        return this;
    }

    public StringSchema contains(String substring) {
        Predicate<Object> isContains = obj -> obj instanceof String && obj.toString().contains(substring);
        add(isContains);
        return this;
    }

}
