package vn.hust.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.hust.common.errorcode.ErrorCode;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortType {

	String message() default ErrorCode.InvalidValue;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
