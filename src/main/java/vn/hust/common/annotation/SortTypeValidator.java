package vn.hust.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hust.common.model.SortEnum;

public class SortTypeValidator implements ConstraintValidator<SortType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) return true;
		for (SortEnum sortEnum : SortEnum.values()) {
			if (sortEnum.name().equalsIgnoreCase(value)) return true;
		}
		return false;
	}
}
