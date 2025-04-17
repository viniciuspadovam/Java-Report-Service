package com.viniciuspadovam.reportprocessor.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.viniciuspadovam.reportprocessor.dto.ReportPayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessorService {
	
	@KafkaListener(topics = "test-topic", groupId = "group_id")
	public void listen(ReportPayload message) {
		System.out.println(message);
	}
	
}
