package com.bnpp.kata.berlinclock.model;

import lombok.Data;

@Data
public class DetailedBerlinTime {
	
	private String secondsLamp;
	private String topFiveHourLamps;
	private String bottomOneHourLamps;
	private String topFiveMinuteLamps;
	private String bottomOneMinuteLamps;
}
