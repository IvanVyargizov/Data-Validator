package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    // По замечанию, что переопределние метода isValid избыточно:
    // isValid в классе BaseSchema при переданном obj = null и отсутствии переданных валидаторов
    // или не переданном валидаторе required возвращает true,
    // и соответственно все классы наследники унаследуют эту логику.
    // По заданию в шагах проекта для класса наследника StringSchema для переданного obj = ""
    // должна выполнятся та же логика, что и для obj = null.
    // если прописать этот вариант для obj = "" в классе родителе BaseSchema,
    // то эту логику унаследуют и все классы наследники, но такая логика мне нужна только в StringSchema,
    // поэтому в StringSchema я переопределяю класс IsValid добавляя проверку для obj = ""
    @Override
    public boolean isValid(Object obj) {
        return Objects.equals(obj, "") && !getValidations().containsKey("required") || super.isValid(obj);
    }

    @Override
    public StringSchema required() {
        Predicate<Object> nonNull = obj -> obj instanceof String && !Objects.equals(obj, "");
        add(nonNull);
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
