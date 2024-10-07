package com.bnpp.kata.berlinclock.service;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.bnpp.kata.berlinclock.constants.BClockConstants;
import com.bnpp.kata.berlinclock.exception.TimeFormatException;
import com.bnpp.kata.berlinclock.model.BerlinClockResponse;

@Service
public class BerlinClockService {

	public BerlinClockResponse convertToBerlinTime(String time) {

		if (time == null || time.isEmpty())
			throw new TimeFormatException(BClockConstants.TIME_IS_EMPTY_ERROR);

		int[] splitTime = Arrays.asList(time.split(BClockConstants.TIME_SEPARATOR))
				.stream().mapToInt(Integer::parseInt).toArray();

		validateTime(splitTime);
		
		String berlinTime = String.join(" ", getSecondsLamp(splitTime[BClockConstants.SECONDS_INDEX]),
				getHoursLamp(splitTime[BClockConstants.HOURS_INDEX]),
				getMinuteLamp(splitTime[BClockConstants.MINUTES_INDEX]));

		return new BerlinClockResponse(time, berlinTime);
	}

	private void validateTime(int[] splitTime) {

		if (splitTime.length != BClockConstants.TOTAL_TIME_LENGTH)
			throw new TimeFormatException(BClockConstants.INVALID_TIME_FORMAT);

		if (splitTime[BClockConstants.HOURS_INDEX] < BClockConstants.ZERO
				|| splitTime[BClockConstants.HOURS_INDEX] > BClockConstants.MAX_HOURS)
			throw new TimeFormatException(BClockConstants.INVALID_HOUR_ERROR);

		if (splitTime[BClockConstants.MINUTES_INDEX] < BClockConstants.ZERO
				|| splitTime[BClockConstants.MINUTES_INDEX] > BClockConstants.MAX_MINUTES)
			throw new TimeFormatException(BClockConstants.INVALID_MINUTE_ERROR);

		if (splitTime[BClockConstants.SECONDS_INDEX] < BClockConstants.ZERO
				|| splitTime[BClockConstants.SECONDS_INDEX] > BClockConstants.MAX_SECONDS)
			throw new TimeFormatException(BClockConstants.INVALID_SECOND_ERROR);
	}

	private String getSecondsLamp(int seconds) {
		return (seconds % BClockConstants.SECONDS_DIVIDER == BClockConstants.ZERO) ? BClockConstants.YELLOW : BClockConstants.OFF;
	}

	private String getHoursLamp(int hours) {
		return String.join(" ", getTopHourRow(hours / BClockConstants.HOUR_DIVIDER),getBottomHourRow(hours % BClockConstants.HOUR_DIVIDER));
	}

	private String getBottomHourRow(int bottomHourValue) {
		return IntStream.range(BClockConstants.ZERO, BClockConstants.HOUR_LAMPS_IN_BOTTOM_ROW)
				.mapToObj(i -> (i < bottomHourValue) ? BClockConstants.RED : BClockConstants.OFF)
				.collect(Collectors.joining());
	}

	private String getTopHourRow(int topHourValue) {
		return IntStream.range(BClockConstants.ZERO, BClockConstants.HOUR_LAMPS_IN_TOP_ROW)
				.mapToObj(i -> (i < topHourValue) ? BClockConstants.RED : BClockConstants.OFF)
				.collect(Collectors.joining());
	}

	private String getMinuteLamp(int minutes) {
		return String.join(" ", getTopMinuteRow(minutes / BClockConstants.MINUTES_DIVIDER),getBottomMinuteRow(minutes % BClockConstants.MINUTES_DIVIDER));
	}

	private String getTopMinuteRow(int topMinuteValue) {
		return IntStream.range(BClockConstants.ZERO, BClockConstants.MINUTES_LAMPS_IN_TOP_ROW)
				.mapToObj(i -> (i < topMinuteValue) ? BClockConstants.YELLOW : BClockConstants.OFF)
				.collect(Collectors.joining()).replace(BClockConstants.REPLACE_YYY, BClockConstants.REPLACE_TO_YYR);
	}

	private String getBottomMinuteRow(int bottomMinuteValue) {
		return IntStream.range(BClockConstants.ZERO, BClockConstants.MINUTES_LAMPS_IN_BOTTOM_ROW)
				.mapToObj(i -> (i < bottomMinuteValue) ? BClockConstants.YELLOW : BClockConstants.OFF)
				.collect(Collectors.joining());
	}

}
