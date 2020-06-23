package fr.nmk.equation_incomplete.model;

import java.util.Random;

public enum Operation {

	plus('+'), minus('-'), divide('/'), multiplication('x');

	private char label;

	Operation(char label) {
		this.label = label;
	}

	public static Random random = new Random();

	public static Operation getRandomOperation() {
		return values()[random.nextInt(values().length)];
	}

	public char getLabel() {
		return label;
	}

}
