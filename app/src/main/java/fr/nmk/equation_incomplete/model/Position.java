package fr.nmk.equation_incomplete.model;

import java.util.Random;

public enum Position {

	left, right, result;

	public static Random random = new Random();

	public static Position getRandomPosition() {
		return values()[random.nextInt(values().length)];
	}
}
