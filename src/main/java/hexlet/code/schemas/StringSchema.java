package hexlet.code.schemas;

import java.util.Objects;

public final class StringSchema extends BaseSchema {

    @Override
    public StringSchema required() {
        add(obj -> obj instanceof String && !Objects.equals(obj, ""));
        return this;
    }

    public StringSchema minLength(int length) {
        if (length < 0) {
            throw new RuntimeException("\"minLength\" method parameter cannot be less than 0");
        }
        add(obj -> obj instanceof String && obj.toString().length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        add(obj -> obj instanceof String && obj.toString().contains(substring));
        return this;
    }

}
