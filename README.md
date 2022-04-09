[![Actions Status](https://github.com/IvanVyargizov/java-project-lvl3/workflows/hexlet-check/badge.svg)](https://github.com/IvanVyargizov/java-project-lvl3/actions) [![build](https://github.com/IvanVyargizov/java-project-lvl3/actions/workflows/build-check.yml/badge.svg)](https://github.com/IvanVyargizov/java-project-lvl3/actions/workflows/build-check.yml) [![Maintainability](https://api.codeclimate.com/v1/badges/83d29b93d33d78b7367a/maintainability)](https://codeclimate.com/github/IvanVyargizov/java-project-lvl3/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/83d29b93d33d78b7367a/test_coverage)](https://codeclimate.com/github/IvanVyargizov/java-project-lvl3/test_coverage)

# Data Validator

Data Validator is a library for check the correctness of any data with easy-readable fluent interface and ability to extensible by adding your own schemas.

## Validator

`Validator` is the class that provides a number of base schemas.

```ts
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;

public class Example {

    public static void main(String[] args) {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        NumberSchema numberSchema = validator.number();
        MapSchema mapSchema = validator.map();
    }

}
```

## BaseSchema

`BaseSchema` is the abstract base class that all schema type inherit from. It provides a number of base methods and properties to all other schema types. `BaseSchema` provides extensible by adding your own schemas.

All schemas inherited from the `BaseScheme` inherit the following methods:
- `required()` - abstract method that assumes the implementation of checking the value to any suitable non-null value
- `add(Predicate<Object>)` - method that adds a predicate to the chain of conditions
- `isValid(Object)` - method that returns a boolean depending on whether the condition value passed in the predicate chain. 

If no predicate is passed, `isValid(Object)` returns `true` for any passed `Object`.

### StringSchema

`StringSchema` implements the ability to check a string by validators.

```ts
Validator validator = new Validator();
StringSchema stringSchema = validator.string();
```

`StringSchema` provides three methods:
- `required()` - checking is a string for a non-null value or non-empty string
- `minLength(int)` - checking is a string for a length equal to or greater than the passed value
- `contains(String)` - checking is a string for the occurrence of a passed substring

```ts
Validator validator = new Validator();
StringSchema stringSchema = validator.string();

stringSchema.isValid(""); // true
stringSchema.isValid(null); // true
stringSchema.isValid("fake it till you make it"); // true

stringSchema.required();

stringSchema.isValid("fake it till you make it"); // true
stringSchema.isValid("hexlet"); // true

stringSchema.isValid(null); // false
stringSchema.isValid(""); // false
// return value is false because method 'required()' is in the chain of checks

stringSchema.contains("fake").isValid("fake it till you make it"); // true
stringSchema.minLength(1).isValid("fake it till you make it"); // true

stringSchema.contains("MAKE").isValid("fake it till you make it"); // false
stringSchema.contains("make").isValid("fake it till you make it"); // false
// return value is false because the chain of checks (predicates) contains 
// the previous check `contains("MAKE")` which is false
```

### NumberSchema

`NumberSchema` implements the ability to check an integer by validators.

```ts
Validator validator = new Validator();
NumberSchema numberSchema = validator.number();
```

`NumberSchema` provides three methods:
- `required()` - checking is an integer for a non-null value
- `positive()` - checking is whether an integer is positive or checking is whether the passed `Object` is `null`
- `range(int, int)` - checking is whether an integer is of a passed range, include range limits

```ts
Validator validator = new Validator();
NumberSchema numberSchema = validator.number();

numberSchema.isValid(null); // true
numberSchema.isValid(5); // true

nnumberSchema.positive();

numberSchema.isValid(null); // true
// return value is true because method 'positive()' returns `true` if the passed `Object` is `null`

numberSchema.required();

numberSchema.isValid(null); // false
// return value is false because method 'required()' is in the chain of checks

numberSchema.isValid(10) // true
numberSchema.isValid("5"); // false
numberSchema.isValid(-10); // false

numberSchema.range(5, 10);

numberSchema.isValid(5); // true
numberSchema.isValid(7); // true
numberSchema.isValid(10); // true
numberSchema.isValid(4); // false
numberSchema.isValid(11); // false

numberSchema.range(-10, -5);

numberSchema.isValid(-5); // false
numberSchema.isValid(-7); // false
numberSchema.isValid(-10); // false
numberSchema.isValid(-4); // false
numberSchema.isValid(-11); // false
// return value is false because the chain of checks (predicates) contains
// the previous check `positive()` which is false
```

### MapSchema

`MapSchema` implements the ability to check a `Map<?, ?>` by validators.

```ts
Validator validator = new Validator();
MapSchema mapSchema = validator.map();
```

`MapSchema` provides three methods:
- `required()` - checking is a value along the `Map<?, ?>` object
- `sizeof(int)` - checking is whether the number of key-value pairs in the `Map<?, ?>` object matches the value passed
- `shape(Map<?, ?>)` - checking is values inside the `Map<?, ?>` object

```ts
Validator validator = new Validator();
MapSchema mapSchema = validator.map();

mapSchema.isValid(null); // true
mapSchema.isValid(new HashMap()); // true

mapSchema.required();

mapSchema.isValid(null) // false
// return value is false because method 'required()' is in the chain of checks

mapSchema.isValid(new HashMap()); // true

Map<String, String> data = new HashMap<>();
data.put("key1", "value1");

mapSchema.isValid(data); // true

mapSchema.sizeof(2);

mapSchema.isValid(data);  // false

data.put("key2", "value2");

mapSchema.isValid(data); // true

MapSchema mapSchema2 = validator.map();

Map<String, BaseSchema> schemas = new HashMap<>();
schemas.put("name", v.string().required());
schemas.put("age", v.number().positive());

mapSchema2.shape(schemas);

Map<String, Object> human1 = new HashMap<>();
human1.put("name", "Kolya");
human1.put("age", 15);

mapSchema.isValid(human1); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("name", "Maya");
human2.put("age", null); 

mapSchema.isValid(human2); // true
// return value of 'v.number().positive()' is true because method 'positive()' returns `true` 
// if the passed `Object` is `null`

Map<String, Object> human3 = new HashMap<>();
human3.put("name", "");
human3.put("age", 10);

mapSchema.isValid(human3); // false

Map<String, Object> human4 = new HashMap<>();
human4.put("name", "Valya");
human4.put("age", -5);

mapSchema.isValid(human4); // false
```