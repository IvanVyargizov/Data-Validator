package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    @Override
    public NumberSchema required() {
        add(obj -> obj instanceof Integer);
        return this;
    }

    public NumberSchema positive() {
        add(obj -> obj == null || (obj instanceof Integer && (Integer) obj > 0));
        return this;
    }

    public NumberSchema range(int begin, int end) {
        if (end < begin) {
            throw new RuntimeException("\"range\" method parameter \"end\" cannot be less than \"begin\"");
        }
        add(
                obj -> obj instanceof Integer
                && (Integer) obj >= begin
                && (Integer) obj <= end
        );
        return this;
    }

}
