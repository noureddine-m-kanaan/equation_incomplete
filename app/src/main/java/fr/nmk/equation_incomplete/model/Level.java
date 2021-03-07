package fr.nmk.equation_incomplete.model;

public enum Level {

	easy(10), medium(20), hard(50);

	private int factor;

	Level(int factor) {
		this.factor = factor;
	}

	public int getFactor() {
		return factor;
	}

	public static Level getLevel(String level) {
		switch (level) {
			case "easy": return easy;
			case "medium": return medium;
			case "hard": return hard;
			default: return easy;
		}
	}
}
