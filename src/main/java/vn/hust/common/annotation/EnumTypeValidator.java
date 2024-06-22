package vn.hust.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import java.lang.reflect.Field;

public class EnumTypeValidator implements ConstraintValidator<Enums, String> {

	@Override
	@SuppressWarnings("unchecked")
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Class<? extends Enum<?>>[] constants = (Class<? extends Enum<?>>[])
				((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAttributes().get("constants");
		for (Class<? extends Enum<?>> constant : constants) {
			for (Field field : constant.getFields()) {
				if (field.getName().equalsIgnoreCase(value)) {
					return true;
				}
			}
		}
		return false;
	}
}
