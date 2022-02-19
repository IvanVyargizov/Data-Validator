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
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid(null)).isEqualTo(true);
        assertThat(schema.isValid("")).isEqualTo(true);
        assertThat(schema.isValid("fake it till you make it")).isEqualTo(false);

        schema.required();
        assertThat(schema.required()).isInstanceOf(StringSchema.class);
        assertThat(schema.isValid(null)).isEqualTo(false);
        assertThat(schema.isValid("")).isEqualTo(false);
        assertThat(schema.isValid("fake it till you make it")).isEqualTo(true);

        assertThat(schema.contains("till").isValid("fake it till you make it")).isEqualTo(true);
        assertThat(schema.contains("MAKE").isValid("fake it till you make it")).isEqualTo(false);

        assertThat(schema.isValid("fake it till you make it")).isEqualTo(false);

        schema.contains("till").minLength(0);
        assertThat(schema.isValid("fake it till you make it")).isEqualTo(true);
        assertThat(schema.minLength(24).isValid("fake it till you make it")).isEqualTo(true);
        assertThat(schema.minLength(25).isValid("fake it till you make it")).isEqualTo(false);

        Assertions.assertThrows(RuntimeException.class, () -> schema.minLength(-1));

    }

    @Test
    void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isEqualTo(true);
        assertThat(schema.isValid(10)).isEqualTo(false);

        schema.required();
        assertThat(schema.required()).isInstanceOf(NumberSchema.class);
        assertThat(schema.isValid(null)).isEqualTo(false);
        assertThat(schema.isValid("10")).isEqualTo(false);
        assertThat(schema.isValid(5)).isEqualTo(true);

        assertThat(schema.positive().isValid(10)).isEqualTo(true);
        assertThat(schema.isValid(0)).isEqualTo(false);
        assertThat(schema.isValid(-10)).isEqualTo(false);

        schema.range(5, 10);
        assertThat(schema.isValid(5)).isEqualTo(true);
        assertThat(schema.isValid(10)).isEqualTo(true);
        assertThat(schema.isValid(7)).isEqualTo(true);
        assertThat(schema.isValid(4)).isEqualTo(false);
        assertThat(schema.isValid(11)).isEqualTo(false);

        schema.positive().range(-10, -5);
        assertThat(schema.isValid(-7)).isEqualTo(false);
        assertThat(schema.isValid(-11)).isEqualTo(false);
        assertThat(schema.isValid(-4)).isEqualTo(false);

        Assertions.assertThrows(RuntimeException.class, () -> schema.range(10, 5));
    }

    @Test
    void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isEqualTo(true);
        assertThat(schema.isValid(new HashMap<>())).isEqualTo(false);
        assertThat(schema.isValid("10")).isEqualTo(false);

        schema.required();
        assertThat(schema.required()).isInstanceOf(MapSchema.class);
        assertThat(schema.isValid(null)).isEqualTo(false);
        assertThat(schema.isValid("10")).isEqualTo(false);
        assertThat(schema.isValid(new HashMap<>())).isEqualTo(true);

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        schema.sizeof(2);
        assertThat(schema.isValid(data)).isEqualTo(true);
        data.put("key3", "value3");
        assertThat(schema.isValid(data)).isEqualTo(false);

        Assertions.assertThrows(RuntimeException.class, () -> schema.sizeof(-1));
    }

    @Test
    void testMapSchemaMethodShape() {
        Validator v = new Validator();
        MapSchema schema1 = v.map();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", v.string().required());
        schemas1.put("age", v.number().positive());
        schema1.shape(schemas1);
        assertThat(schema1.shape(schemas1)).isInstanceOf(MapSchema.class);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 51);
        assertThat(schema1.isValid(human1)).isEqualTo(true);

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema1.isValid(human2)).isEqualTo(false);

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", null);
        human3.put("age", 17);
        assertThat(schema1.isValid(human3)).isEqualTo(false);

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertThat(schema1.isValid(human4)).isEqualTo(false);

        MapSchema schema2 = v.map();

        Map<String, BaseSchema> schemas2 = new HashMap<>();
        schemas2.put("name", v.string().minLength(7).required());
        schemas2.put("age", v.number().positive().range(5, 10));
        schema2.shape(schemas2);

        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Ivan");
        human5.put("age", 7);
        assertThat(schema2.isValid(human5)).isEqualTo(false);

        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Vladislav");
        human6.put("age", 11);
        assertThat(schema2.isValid(human6)).isEqualTo(false);

        Map<String, Object> human7 = new HashMap<>();
        human7.put("name", "Vladislav");
        human7.put("age", 7);
        assertThat(schema2.isValid(human7)).isEqualTo(true);

    }

}
