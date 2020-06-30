package fr.nmk.equation_incomplete.model;

public enum Level {

	easy(10), medium(50), hard(100);

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
