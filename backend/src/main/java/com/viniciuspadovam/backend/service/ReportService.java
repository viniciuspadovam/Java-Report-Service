package com.viniciuspadovam.backend.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.viniciuspadovam.backend.dto.ReportPayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void createMessage(String topic, ReportPayload message) {
		kafkaTemplate.send(topic, message);
	}
	
}
