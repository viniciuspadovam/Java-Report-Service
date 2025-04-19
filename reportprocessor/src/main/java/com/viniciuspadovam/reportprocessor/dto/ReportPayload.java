package com.viniciuspadovam.reportprocessor.dto;

import java.util.ArrayList;
import java.util.Map;

public record ReportPayload(
	String title,
	Map<String, ArrayList<String>> body
) {}
