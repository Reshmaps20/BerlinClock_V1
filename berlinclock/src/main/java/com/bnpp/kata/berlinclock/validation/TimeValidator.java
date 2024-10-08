package com.bnpp.kata.berlinclock.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.bnpp.kata.berlinclock.constants.BClockConstants;
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

		Objects.requireNonNull(time, BClockConstants.TIME_IS_EMPTY_ERROR);

		List<TimeValidator> validators = Arrays.asList(
				new TimeValidator(val -> val >= BClockConstants.ZERO && val <= BClockConstants.MAX_HOURS,
						BClockConstants.INVALID_HOUR_ERROR),
				new TimeValidator(val -> val >= BClockConstants.ZERO && val <= BClockConstants.MAX_MINUTES,
						BClockConstants.INVALID_MINUTE_ERROR),
				new TimeValidator(val -> val >= BClockConstants.ZERO && val <= BClockConstants.MAX_SECONDS,
						BClockConstants.INVALID_SECOND_ERROR));

		int[] valuesToValidate = new int[] { 
				Integer.parseInt(time.getHours()), Integer.parseInt(time.getMinutes()), Integer.parseInt(time.getSeconds()) 
				};
		
		IntStream.range(0, validators.size()).forEach(i -> validators.get(i).validate(valuesToValidate[i]));
	}

}
