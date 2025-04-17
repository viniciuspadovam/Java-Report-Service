package com.viniciuspadovam.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciuspadovam.backend.dto.ReportPayload;
import com.viniciuspadovam.backend.service.ReportService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController {

	private final ReportService reportService;
	
	@PostMapping
	public Mono<Void> publishMessage(@RequestBody ReportPayload message) {
		return Mono.fromRunnable(() -> reportService.createMessage("test-topic", message));
	}
	
}
