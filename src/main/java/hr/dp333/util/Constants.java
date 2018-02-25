package hr.dp333.util;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Cannot instantiate class.");
	}

	public static final String JSON_SOURCE_FILE_NAME = "data.json";
	public static final String JSON_DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";
	public static final List<String> HEADER_COLUMNS = Collections.unmodifiableList(Arrays.asList(
			"Democrat - Active",
			"Libertarian - Active",
			"No Party - Active",
			"Other - Active",
			"Republican - Active",
			"Democrat - Inactive",
			"Libertarian - Inactive",
			"No Party - Inactive",
			"Other - Inactive",
			"Republican - Inactive"));
	public static final String HUMAN_READABLE_DATE_FORMAT_PATTERN = "dd. MM. yyyy";
	public static final DateTimeFormatter HUMAN_READABLE_DATE_FORMATTER = DateTimeFormatter.ofPattern(HUMAN_READABLE_DATE_FORMAT_PATTERN);

}
