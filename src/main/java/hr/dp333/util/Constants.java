package hr.dp333.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Cannot instantiate class.");
	}

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

}
