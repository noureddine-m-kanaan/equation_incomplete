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
}
