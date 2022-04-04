package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    @Override
    public boolean isValid(Object obj) {
        return Objects.equals(obj, "") && !getStatusRequired() || super.isValid(obj);
    }

    @Override
    public StringSchema required() {
        setRequiredFor(String.class);
        Predicate<Object> nonEmpty = obj -> !Objects.equals(obj, "");
        add(nonEmpty);
        return this;
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
