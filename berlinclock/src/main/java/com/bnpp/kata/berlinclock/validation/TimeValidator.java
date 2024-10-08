package com.bnpp.kata.berlinclock.validation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.bnpp.kata.berlinclock.constants.Constants;
import com.bnpp.kata.berlinclock.exception.TimeFormatException;
import com.bnpp.kata.berlinclock.model.TimeInput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class TimeValidator {

	private Predicate<Integer> validationRule;
	private String errorMessage;

	public void validate(int value) {
		if (!validationRule.test(value)) {
			throw new TimeFormatException(errorMessage);
		}
	}

	public void validateTimeValues(TimeInput time) {

		if (isAnyTimeFieldInvalid(time)) {
			throw new TimeFormatException(Constants.TIME_IS_EMPTY_ERROR);
		}

		List<TimeValidator> validators = Arrays.asList(
				new TimeValidator(val -> val >= Constants.ZERO && val <= Constants.MAX_HOURS,Constants.INVALID_HOUR_ERROR),
				new TimeValidator(val -> val >= Constants.ZERO && val <= Constants.MAX_MINUTES,Constants.INVALID_MINUTE_ERROR),
				new TimeValidator(val -> val >= Constants.ZERO && val <= Constants.MAX_SECONDS,Constants.INVALID_SECOND_ERROR));

		int[] valuesToValidate = new int[] { Integer.parseInt(time.getHours()), Integer.parseInt(time.getMinutes()),
				Integer.parseInt(time.getSeconds()) };

		IntStream.range(0, validators.size()).forEach(i -> validators.get(i).validate(valuesToValidate[i]));
	}

	private boolean isAnyTimeFieldInvalid(TimeInput time) {
		return StringUtils.isEmpty(time.getHours()) || StringUtils.isEmpty(time.getMinutes())|| StringUtils.isEmpty(time.getSeconds());
	}

}
