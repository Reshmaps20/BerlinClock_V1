package com.bnpp.kata.berlinclock.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.bnpp.kata.berlinclock.constants.BClockConstants;
import com.bnpp.kata.berlinclock.model.BerlinClockResponse;
import com.bnpp.kata.berlinclock.model.TimeInput;
import com.bnpp.kata.berlinclock.store.Lamp;
import com.bnpp.kata.berlinclock.store.LampRow;
import com.bnpp.kata.berlinclock.validation.TimeValidator;

@Service
public class BerlinClockService {

	private final TimeValidator timeValidator;

	public BerlinClockService(TimeValidator timeValidator) {
		this.timeValidator = timeValidator;
	}

	public BerlinClockResponse convertToBerlinTime(TimeInput time) {

		timeValidator.validateTimeValues(time);

		return new BerlinClockResponse(convertToDigitalTime(time), calculateBerlinTime(time));
	}

	private List<String> calculateBerlinTime(TimeInput time) {

		List<String> hourLamps = getHoursLamp(Integer.parseInt(time.getHours()));
		List<String> minuteLamps = getMinuteLamp(Integer.parseInt(time.getMinutes()));

		return Arrays.asList(getSecondsLamp(Integer.parseInt(time.getSeconds())), hourLamps.get(0), hourLamps.get(1), minuteLamps.get(0),
				minuteLamps.get(1));
	}

	private String getSecondsLamp(int seconds) {
		return (seconds % BClockConstants.SECONDS_DIVIDER == BClockConstants.ZERO) ? Lamp.YELLOW.getValue() : Lamp.OFF.getValue();
	}

	private List<String> getHoursLamp(int hours) {
		return Arrays.asList(
				getHourLampRow(LampRow.TOP_HOUR_LAMP.getLength(), hours / BClockConstants.HOUR_DIVIDER),
				getHourLampRow(LampRow.BOTTOM_HOUR_LAMP.getLength(), hours % BClockConstants.HOUR_DIVIDER));
	}

	private String getHourLampRow(int rowLength, int hourValue) {
		return IntStream.range(BClockConstants.ZERO, rowLength)
				.mapToObj(i -> (i < hourValue) ? Lamp.RED.getValue() : Lamp.OFF.getValue())
				.collect(Collectors.joining());
	}

	private List<String> getMinuteLamp(int minutes) {
		return Arrays.asList(
				getMinuteLampRow(LampRow.TOP_MINUTE_LAMP.getLength(), minutes / BClockConstants.MINUTES_DIVIDER, true),
				getMinuteLampRow(LampRow.BOTTOM_MINUTE_LAMP.getLength(), minutes % BClockConstants.MINUTES_DIVIDER,
						false));
	}

	private String getMinuteLampRow(int rowLength, int minuteValue, boolean isTopRow) {
		String mintLamps = IntStream.range(BClockConstants.ZERO, rowLength)
				.mapToObj(i -> (i < minuteValue) ? Lamp.YELLOW.getValue() : Lamp.OFF.getValue())
				.collect(Collectors.joining());

		return isTopRow ? mintLamps.replace(BClockConstants.REPLACE_YYY, BClockConstants.REPLACE_TO_YYR) : mintLamps;
	}

	public String convertToDigitalTime(TimeInput time) {
		return Arrays.stream(new int[] { Integer.parseInt(time.getHours()), Integer.parseInt(time.getMinutes()), Integer.parseInt(time.getSeconds()) })
				.mapToObj(i -> String.format(BClockConstants.TIME_FORMAT, i))
				.collect(Collectors.joining(BClockConstants.TIME_SEPARATOR));
	}

}
