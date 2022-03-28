package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {

    @Override
    public NumberSchema required() {
        Predicate<Object> nonNull = obj -> obj instanceof Integer;
        add(nonNull);
        return this;
    }

    public NumberSchema positive() {
        Predicate<Object> isPositive = obj -> obj instanceof Integer && (Integer) obj > 0;
        add(isPositive);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        if (end < begin) {
            throw new RuntimeException("\"range\" method parameter \"end\" cannot be less than \"begin\"");
        }
        Predicate<Object> isEnterRange = obj -> obj instanceof Integer
                && (Integer) obj >= begin
                && (Integer) obj <= end;
        add(isEnterRange);
        return this;
    }

}
