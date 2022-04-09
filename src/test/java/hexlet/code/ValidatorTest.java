package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    @Test
    void testStringSchema() {
        final String fraze = "fake it till you make it";
        final int rightLengthFraze = 24;
        final int wrongLengthFraze = 25;
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("")).isTrue();

        schema.required();

        assertThat(schema).isInstanceOf(StringSchema.class);

        assertThat(schema.isValid(fraze)).isTrue();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(1)).isFalse();

        assertThat(schema.minLength(rightLengthFraze).isValid(fraze)).isTrue();
        assertThat(schema.contains("till").isValid(fraze)).isTrue();
        assertThat(schema.minLength(wrongLengthFraze).isValid(fraze)).isFalse();
        assertThat(schema.contains("MAKE").isValid(fraze)).isFalse();

        Assertions.assertThrows(RuntimeException.class, () -> schema.minLength(-1));

    }

    @Test
    void testNumberSchema() {
        final int testNumber11 = 11;
        final int testNumber10 = 10;
        final int testNumber7 = 7;
        final int testNumber5 = 5;
        final int testNumber4 = 4;

        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema).isInstanceOf(NumberSchema.class);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("10")).isFalse();
        assertThat(schema.isValid(testNumber10)).isTrue();
        assertThat(schema.isValid(-testNumber10)).isTrue();

        schema.positive();
        assertThat(schema.isValid(testNumber10)).isTrue();
        assertThat(schema.isValid(-testNumber10)).isFalse();

        schema.range(testNumber5, testNumber10);
        assertThat(schema.isValid(testNumber5)).isTrue();
        assertThat(schema.isValid(testNumber10)).isTrue();
        assertThat(schema.isValid(testNumber7)).isTrue();
        assertThat(schema.isValid(testNumber4)).isFalse();
        assertThat(schema.isValid(testNumber11)).isFalse();

        schema.range((-testNumber10), (-testNumber5));
        assertThat(schema.isValid(-testNumber7)).isFalse();
        assertThat(schema.isValid(-testNumber11)).isFalse();
        assertThat(schema.isValid(-testNumber4)).isFalse();

        Assertions.assertThrows(RuntimeException.class, () -> schema.range(testNumber7, testNumber5));
    }

    @Test
    void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema).isInstanceOf(MapSchema.class);

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("10")).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");

        schema.sizeof(2);
        assertThat(schema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();

        Assertions.assertThrows(RuntimeException.class, () -> schema.sizeof(-1));
    }

    @Test
    void testMapSchemaMethodShape() {
        final int testNumber5 = 5;
        final int testNumber11 = 11;
        final int testNumber10 = 10;
        final int testNumber7 = 7;

        Validator v = new Validator();
        MapSchema schema1 = v.map();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", v.string().required());
        schemas1.put("age", v.number().positive());
        schema1.shape(schemas1);

        assertThat(schema1).isInstanceOf(MapSchema.class);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", testNumber5);
        assertThat(schema1.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema1.isValid(human2)).isTrue();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(schema1.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", (-testNumber5));
        assertThat(schema1.isValid(human4)).isFalse();

        MapSchema schema2 = v.map();

        Map<String, BaseSchema> schemas2 = new HashMap<>();
        schemas2.put("name", v.string().minLength(testNumber7));
        schemas2.put("age", v.number().positive().range(testNumber5, testNumber10));
        schema2.shape(schemas2);

        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Ivan");
        human5.put("age", testNumber7);
        assertThat(schema2.isValid(human5)).isFalse();

        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Vladislav");
        human6.put("age", testNumber11);
        assertThat(schema2.isValid(human6)).isFalse();

        Map<String, Object> human7 = new HashMap<>();
        human7.put("name", "Vladislav");
        human7.put("age", testNumber7);
        assertThat(schema2.isValid(human7)).isTrue();

    }

}
