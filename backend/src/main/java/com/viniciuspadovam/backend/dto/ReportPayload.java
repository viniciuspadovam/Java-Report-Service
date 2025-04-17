package com.viniciuspadovam.backend.dto;

import java.util.ArrayList;
import java.util.Map;

public record ReportPayload(
	String title,
	Map<String, ArrayList<Object>> body
) {}
