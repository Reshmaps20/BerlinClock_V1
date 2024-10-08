package com.bnpp.kata.berlinclock.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Lamp {

	YELLOW("Y"), 
	RED("R"), 
	OFF("O");

	private final String value;
}
