package hexlet.code;

//import hexlet.code.schemas.BaseSchema;
//import hexlet.code.schemas.MapSchema;
//import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import java.util.HashMap;
//import java.util.Map;

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
//        assertThat(schema.minLength(1).isValid(null)).isTrue();
//        assertThat(schema.minLength(1).isValid("")).isTrue();
//        assertThat(schema.isValid(1)).isFalse();
//        assertThat(schema.isValid(fraze)).isTrue();
//        assertThat(schema.isValid(1)).isFalse();
//
//        assertThat(schema.required()).isInstanceOf(StringSchema.class);
//
//        assertThat(schema.isValid(null)).isFalse();
//        assertThat(schema.isValid("")).isFalse();
//
//        assertThat(schema.contains("till").isValid(fraze)).isTrue();
//        assertThat(schema.contains("MAKE").isValid(fraze)).isFalse();
//
//        assertThat(schema.isValid(fraze)).isFalse();
//
//        schema.contains("till").minLength(0);
//        assertThat(schema.isValid(fraze)).isTrue();
//        assertThat(schema.minLength(rightLengthFraze).isValid(fraze)).isTrue();
//        assertThat(schema.minLength(wrongLengthFraze).isValid(fraze)).isFalse();
//
//        Assertions.assertThrows(RuntimeException.class, () -> schema.minLength(-1));

    }

//    @Test
//    void testNumberSchema() {
//        final int testNegativeNumber11 = -11;
//        final int testNegativeNumber10 = -10;
//        final int testNegativeNumber7 = -7;
//        final int testNegativeNumber5 = -5;
//        final int testNegativeNumber4 = -4;
//        final int testPositiveNumber11 = 11;
//        final int testPositiveNumber10 = 10;
//        final int testPositiveNumber7 = 7;
//        final int testPositiveNumber5 = 5;
//        final int testPositiveNumber4 = 4;
//
//        Validator v = new Validator();
//        NumberSchema schema = v.number();
//
//        assertThat(schema.isValid(null)).isTrue();
//        assertThat(schema.isValid("10")).isFalse();
//        assertThat(schema.isValid("")).isFalse();
//        assertThat(schema.positive().isValid(null)).isTrue();
//        assertThat(schema.isValid(testPositiveNumber10)).isTrue();
//        assertThat(schema.isValid(testNegativeNumber10)).isFalse();
//        assertThat(schema.isValid(null)).isTrue();
//
//        assertThat(schema.required()).isInstanceOf(NumberSchema.class);
//
//        assertThat(schema.isValid(null)).isFalse();
//        assertThat(schema.isValid(testPositiveNumber5)).isTrue();
//
//        assertThat(schema.positive().isValid(testPositiveNumber10)).isTrue();
//        assertThat(schema.isValid(0)).isFalse();
//        assertThat(schema.isValid(testNegativeNumber10)).isFalse();
//
//        schema.range(testPositiveNumber5, testPositiveNumber10);
//        assertThat(schema.isValid(testPositiveNumber5)).isTrue();
//        assertThat(schema.isValid(testPositiveNumber10)).isTrue();
//        assertThat(schema.isValid(testPositiveNumber7)).isTrue();
//        assertThat(schema.isValid(testPositiveNumber4)).isFalse();
//        assertThat(schema.isValid(testPositiveNumber11)).isFalse();
//
//        schema.positive().range(testNegativeNumber10, testNegativeNumber5);
//        assertThat(schema.isValid(testNegativeNumber7)).isFalse();
//        assertThat(schema.isValid(testNegativeNumber11)).isFalse();
//        assertThat(schema.isValid(testNegativeNumber4)).isFalse();
//
//        Assertions.assertThrows(RuntimeException.class, () -> schema.range(testPositiveNumber7, testPositiveNumber5));
//    }
//
//    @Test
//    void testMapSchema() {
//        Validator v = new Validator();
//        MapSchema schema = v.map();
//
//        assertThat(schema.isValid(null)).isTrue();
//        assertThat(schema.isValid("10")).isFalse();
//        assertThat(schema.isValid("")).isFalse();
//        assertThat(schema.isValid(new HashMap<>())).isTrue();
//
//        assertThat(schema.required()).isInstanceOf(MapSchema.class);
//
//        assertThat(schema.isValid(null)).isFalse();
//        assertThat(schema.isValid("10")).isFalse();
//
//        Map<String, String> data = new HashMap<>();
//        data.put("key1", "value1");
//
//        schema.sizeof(1);
//        assertThat(schema.isValid(data)).isTrue();
//        data.put("key2", "value2");
//        assertThat(schema.isValid(data)).isFalse();
//        assertThat(schema.sizeof(2).isValid(data)).isTrue();
//
//        Assertions.assertThrows(RuntimeException.class, () -> schema.sizeof(-1));
//    }
//
//    @Test
//    void testMapSchemaMethodShape() {
//        final int testNegativeNumber5 = -5;
//        final int testPositiveNumber11 = 11;
//        final int testPositiveNumber10 = 10;
//        final int testPositiveNumber7 = 7;
//        final int testPositiveNumber5 = 5;
//
//        Validator v = new Validator();
//        MapSchema schema1 = v.map();
//
//        Map<String, BaseSchema> schemas1 = new HashMap<>();
//        schemas1.put("name", v.string().required());
//        schemas1.put("age", v.number().positive());
//        schema1.shape(schemas1);
//
//        assertThat(schema1.shape(schemas1)).isInstanceOf(MapSchema.class);
//
//        Map<String, Object> human1 = new HashMap<>();
//        human1.put("name", "Kolya");
//        human1.put("age", testPositiveNumber5);
//        assertThat(schema1.isValid(human1)).isTrue();
//
//        Map<String, Object> human2 = new HashMap<>();
//        human2.put("name", "Maya");
//        human2.put("age", null);
//        assertThat(schema1.isValid(human2)).isTrue();
//
//        Map<String, Object> human3 = new HashMap<>();
//        human3.put("name", null);
//        human3.put("age", testPositiveNumber10);
//        assertThat(schema1.isValid(human3)).isFalse();
//
//        Map<String, Object> human4 = new HashMap<>();
//        human4.put("name", "Valya");
//        human4.put("age", testNegativeNumber5);
//        assertThat(schema1.isValid(human4)).isFalse();
//
//        MapSchema schema2 = v.map();
//
//        Map<String, BaseSchema> schemas2 = new HashMap<>();
//        schemas2.put("name", v.string().minLength(testPositiveNumber7));
//        schemas2.put("age", v.number().positive().range(testPositiveNumber5, testPositiveNumber10));
//        schema2.shape(schemas2);
//
//        Map<String, Object> human5 = new HashMap<>();
//        human5.put("name", "Ivan");
//        human5.put("age", testPositiveNumber7);
//        assertThat(schema2.isValid(human5)).isFalse();
//
//        Map<String, Object> human6 = new HashMap<>();
//        human6.put("name", "Vladislav");
//        human6.put("age", testPositiveNumber11);
//        assertThat(schema2.isValid(human6)).isFalse();
//
//        Map<String, Object> human7 = new HashMap<>();
//        human7.put("name", "Vladislav");
//        human7.put("age", testPositiveNumber7);
//        assertThat(schema2.isValid(human7)).isTrue();
//
//    }

}
