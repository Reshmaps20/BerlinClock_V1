package com.bnpp.kata.berlinclock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.kata.berlinclock.model.BerlinClockRequest;
import com.bnpp.kata.berlinclock.model.BerlinClockResponse;
import com.bnpp.kata.berlinclock.service.BerlinClockService;

@RestController
@RequestMapping("/api/berlinclock")
public class BerlinClockController {

	private final BerlinClockService berlinClockService;

	public BerlinClockController(BerlinClockService berlinClockService) {
		this.berlinClockService = berlinClockService;
	}

	@PostMapping("/convert")
	public BerlinClockResponse convertTime(@RequestBody BerlinClockRequest request) {
		return berlinClockService.convertToBerlinTime(request.getTime());
	}
}
