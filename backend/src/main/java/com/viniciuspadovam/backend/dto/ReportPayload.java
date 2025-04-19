package com.viniciuspadovam.backend.dto;

import java.util.ArrayList;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportPayload(
	@NotBlank
	@Size(max = 55)
	String title,
	@NotNull
	Map<String, ArrayList<String>> body
) {}
