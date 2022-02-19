package hexlet.code.schemas;

import java.util.Objects;

public final class StringSchema extends BaseSchema {

    public StringSchema() {
        super();
    }

    @Override
    public boolean isValid(Object obj) {
        if (getValidations().isEmpty()) {
            return Objects.isNull(obj) || Objects.toString(obj).equals("");
        }
        boolean validStatus;
        for (String validation : getValidations().keySet()) {
            switch (validation) {
                case "required" -> {
                    validStatus = obj instanceof String && !obj.toString().isEmpty();
                    if (!validStatus) {
                        return false;
                    }
                }
                case "minLength" -> {
                    validStatus = obj instanceof String && obj.toString().length()
                            >= (Integer) getValidations().get("minLength");
                    if (!validStatus) {
                        return false;
                    }
                }
                case "contains" -> {
                    validStatus = obj instanceof String
                            && obj.toString().contains(Objects.toString(getValidations().get("contains")));
                    if (!validStatus) {
                        return false;
                    }
                }
                default -> { }
            }
        }
        return true;
    }

    public StringSchema minLength(int length) throws RuntimeException {
        if (length < 0) {
            throw new RuntimeException("\"minLength\" method parameter cannot be less than 0");
        }
        getValidations().put("minLength", length);
        return this;
    }

    public StringSchema contains(String substring) {
        getValidations().put("contains", substring);
        return this;
    }

}
