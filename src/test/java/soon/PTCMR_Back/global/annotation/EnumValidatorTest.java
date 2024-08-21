package soon.PTCMR_Back.global.annotation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Payload;
import java.lang.annotation.Annotation;
import org.junit.jupiter.api.Test;
import soon.PTCMR_Back.domain.product.entity.StorageType;

class EnumValidatorTest {

    @Test
    void testIgnoreCaseTrue() {
        EnumValidator validator = new EnumValidator();
        validator.initialize(new ValidEnumImpl(StorageType.class, true));

        assertTrue(validator.isValid("frozen", null));
        assertTrue(validator.isValid("FROZEN", null));
        assertTrue(validator.isValid("Frozen", null));
    }

    private static class ValidEnumImpl implements ValidEnum {
        private final Class<? extends Enum<?>> verifyClass;
        private final boolean ignoreCase;

        ValidEnumImpl(Class<? extends Enum<?>> verifyClass, boolean ignoreCase) {
            this.verifyClass = verifyClass;
            this.ignoreCase = ignoreCase;
        }

        @Override
        public Class<? extends Enum<?>> verifyClass() {
            return verifyClass;
        }

        @Override
        public boolean ignoreCase() {
            return ignoreCase;
        }

        @Override
        public String message() {
            return "";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }
}

