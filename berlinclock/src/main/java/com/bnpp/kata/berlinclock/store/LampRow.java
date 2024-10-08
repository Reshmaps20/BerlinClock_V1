package com.bnpp.kata.berlinclock.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LampRow {

	SECONDS_LAMP(1), 
	TOP_HOUR_LAMP(4), 
	BOTTOM_HOUR_LAMP(4), 
	TOP_MINUTE_LAMP(11), 
	BOTTOM_MINUTE_LAMP(4);

	private final int length;
}
