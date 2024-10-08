package com.bnpp.kata.berlinclock.constants;

public final class BClockConstants {

	public static final String TIME_SEPARATOR = ":";
	public static final String TIME_FORMAT = "%02d";
	public static final int TOTAL_TIME_LENGTH = 3;
	public static final int HOURS_INDEX = 0;
	public static final int MINUTES_INDEX = 1;
	public static final int SECONDS_INDEX = 2;
	public static final int ZERO = 0;
	public static final int MAX_HOURS = 23;
	public static final int MAX_MINUTES = 59;
	public static final int MAX_SECONDS = 59;
    public static final int HOUR_DIVIDER = 5;
    public static final int MINUTES_DIVIDER = 5;
    public static final int SECONDS_DIVIDER = 2;
	public static final String REPLACE_YYY = "YYY";
	public static final String REPLACE_TO_YYR = "YYR";
	public static final String INVALID_TIME_FORMAT = "Invalid Time.Time must be in the format HH:mm:ss";
	public static final String INVALID_HOUR_ERROR = "Hours must be between 0 and 23.";
	public static final String INVALID_MINUTE_ERROR = "Minutes must be between 0 and 59.";
	public static final String INVALID_SECOND_ERROR = "Seconds must be between 0 and 59.";
	public static final String TIME_IS_EMPTY_ERROR = "Invalid Time.Time cannot be null or empty.";
}
