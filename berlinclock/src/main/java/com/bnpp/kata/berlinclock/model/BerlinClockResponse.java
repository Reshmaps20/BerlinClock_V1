package com.bnpp.kata.berlinclock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BerlinClockResponse {

	private String digitalTime;
	private DetailedBerlinTime detailedBerlinTime;
	private String berlinTime;
}
