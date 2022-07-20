package com.example.demo.model;

import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

public class ModelTest {
    private static final Validator ACCESSOR_VALIDATOR = ValidatorBuilder.create()
            .with(new GetterTester())
            .with(new SetterTester())
            .build();

    public static void validateAccessors(final Class<?> clazz) {
        ACCESSOR_VALIDATOR.validate(PojoClassFactory.getPojoClass(clazz));
    }

    public static void validateAccessors(final String packageName, PojoClassFilter... filters) {
        ACCESSOR_VALIDATOR.validate(packageName, filters);
    }

    @Test
    public void setterAndGettersTestModel() {
        validateAccessors("com.example.demo.model");
    }
}
