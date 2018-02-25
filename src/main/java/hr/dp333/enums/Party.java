package hr.dp333.enums;

public enum Party {

	DEMOCRAT("Democrat"), REPUBLICAN("Republican"), LIBERTARIAN("Libertarian"), NO_PARTY("No Party"), OTHER("Other");

	private final String value;

	private Party(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public static boolean isValidParty(String party) {
		if (null == party) {
			return false;
		}
		for (Party p : Party.values()) {
			if (p.toString().equals(party)) {
				return true;
			}
		}
		return false;
	}

}
