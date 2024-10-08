package com.bnpp.kata.berlinclock.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BerlinClockResponse {

	private String digitalTime;
	private List<String> berlinTime;
}
