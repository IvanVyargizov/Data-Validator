package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema {


    public NumberSchema() {
        super();
    }

    @Override
    public boolean isValid(Object obj) {
        if (getValidations().isEmpty()) {
            return Objects.isNull(obj);
        }
        boolean validStatus;
        for (String validation : getValidations().keySet()) {
            switch (validation) {
                case "required" -> {
                    validStatus = obj instanceof Integer;
                    if (!validStatus) {
                        return false;
                    }
                }
                case "positive" -> {
                    validStatus = obj instanceof Integer && (Integer) obj > 0;
                    if (!validStatus) {
                        return false;
                    }
                }
                case "rangeBegin" -> {
                    validStatus = obj instanceof Integer
                            && (Integer) obj >= (Integer) getValidations().get("rangeBegin")
                            && (Integer) obj <= (Integer) getValidations().get("rangeEnd");
                    if (!validStatus) {
                        return false;
                    }
                }
                default -> { }
            }
        }
        return true;
    }

    public NumberSchema positive() {
        getValidations().put("positive", true);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        if (end < begin) {
            throw new RuntimeException("\"range\" method parameter \"end\" cannot be less than \"begin\"");
        }
        getValidations().put("rangeBegin", begin);
        getValidations().put("rangeEnd", end);
        return this;
    }

}
