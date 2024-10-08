package com.bnpp.kata.berlinclock.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.bnpp.kata.berlinclock.constants.Constants;
import com.bnpp.kata.berlinclock.model.BerlinClockResponse;
import com.bnpp.kata.berlinclock.model.DetailedBerlinTime;
import com.bnpp.kata.berlinclock.model.TimeInput;
import com.bnpp.kata.berlinclock.store.Lamp;
import com.bnpp.kata.berlinclock.store.LampRow;
import com.bnpp.kata.berlinclock.validation.TimeValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BerlinClockService {

	private final TimeValidator timeValidator;

	public BerlinClockResponse convertToBerlinTime(TimeInput time) {

		timeValidator.validateTimeValues(time);

		DetailedBerlinTime detailedBerlinTime = new DetailedBerlinTime();
		String berlinTime = calculateBerlinTime(time, detailedBerlinTime);

		return new BerlinClockResponse(convertToDigitalTime(time), detailedBerlinTime, berlinTime);
	}

	private String calculateBerlinTime(TimeInput time, DetailedBerlinTime detailedBerlinTime) {

		String secondLamp = getSecondsLamp(Integer.parseInt(time.getSeconds()));
		List<String> hourLamps = getHoursLamp(Integer.parseInt(time.getHours()));
		List<String> minuteLamps = getMinuteLamp(Integer.parseInt(time.getMinutes()));

		setBerlinTimeDetails(detailedBerlinTime, secondLamp, hourLamps, minuteLamps);

		return Stream.of(secondLamp, hourLamps.get(0), hourLamps.get(1), minuteLamps.get(0), minuteLamps.get(1))
				.collect(Collectors.joining(" "));
	}

	private void setBerlinTimeDetails(DetailedBerlinTime detailedBerlinTime, String secondLamp, List<String> hourLamps,List<String> minuteLamps) {

		detailedBerlinTime.setSecondsLamp(secondLamp);
		detailedBerlinTime.setTopFiveHourLamps(hourLamps.get(0));
		detailedBerlinTime.setBottomOneHourLamps(hourLamps.get(1));
		detailedBerlinTime.setTopFiveMinuteLamps(minuteLamps.get(0));
		detailedBerlinTime.setBottomOneMinuteLamps(minuteLamps.get(1));
	}

	private String getSecondsLamp(int seconds) {
		return (seconds % Constants.SECONDS_DIVIDER == Constants.ZERO) ? Lamp.YELLOW.getValue() : Lamp.OFF.getValue();
	}

	private List<String> getHoursLamp(int hours) {
		return Arrays.asList(
				getHourLampRow(LampRow.TOP_HOUR_LAMP.getLength(), hours / Constants.HOUR_DIVIDER),
				getHourLampRow(LampRow.BOTTOM_HOUR_LAMP.getLength(), hours % Constants.HOUR_DIVIDER));
	}

	private String getHourLampRow(int rowLength, int hourValue) {
		return IntStream.range(Constants.ZERO, rowLength)
				.mapToObj(i -> (i < hourValue) ? Lamp.RED.getValue() : Lamp.OFF.getValue())
				.collect(Collectors.joining());
	}

	private List<String> getMinuteLamp(int minutes) {
		return Arrays.asList(
				getMinuteLampRow(LampRow.TOP_MINUTE_LAMP.getLength(), minutes / Constants.MINUTES_DIVIDER, true),
				getMinuteLampRow(LampRow.BOTTOM_MINUTE_LAMP.getLength(), minutes % Constants.MINUTES_DIVIDER,false));
	}

	private String getMinuteLampRow(int rowLength, int minuteValue, boolean isTopRow) {
		String mintLamps = IntStream.range(Constants.ZERO, rowLength)
				.mapToObj(i -> (i < minuteValue) ? Lamp.YELLOW.getValue() : Lamp.OFF.getValue())
				.collect(Collectors.joining());

		return isTopRow ? mintLamps.replace(Constants.REPLACE_YYY, Constants.REPLACE_TO_YYR) : mintLamps;
	}

	public String convertToDigitalTime(TimeInput time) {
		return Arrays.stream(new int[] { Integer.parseInt(time.getHours()), Integer.parseInt(time.getMinutes()),
						Integer.parseInt(time.getSeconds()) })
				.mapToObj(i -> String.format(Constants.TIME_FORMAT, i))
				.collect(Collectors.joining(Constants.TIME_SEPARATOR));
	}

}
