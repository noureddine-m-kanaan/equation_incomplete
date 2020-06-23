package fr.nmk.equation_incomplete.model;

public class Equation {

	private Operation operation;
	private Level level;

	private int a; // � gauche
	private int b; // � droite
	private int c; // r�sultat

	private Position positionToHide;

	public Equation(Level level) {
		this.level = level;
		this.operation = Operation.getRandomOperation();

		// init a and b
		init_a_and_b();

		// compute the value of the result
		compute_result();

		// random position to hide (a, b or c)
		this.positionToHide = Position.getRandomPosition();

	}

	private void compute_result() {
		switch (operation) {
		case plus: {
			c = a + b;
			break;
		}
		case minus: {
			// Switch values of a and b
			if (a < b) {
				int tmp = b;
				b = a;
				a = tmp;
			}
			c = a - b;
			break;
		}
		case multiplication: {
			c = a * b;
			break;
		}
		case divide: {
			// a/b = c
			// a = b*c
			if (b == 0) {
				b = (int) (Math.random() * this.level.getFactor()) + 1;
			}
			c = a;
			a = b * c;
			break;
		}
		default:
			operation = Operation.plus;
			c = a + b;
			break;
		}
	}

	private void init_a_and_b() {
		a = (int) (Math.random() * this.level.getFactor());
		b = (int) (Math.random() * this.level.getFactor());
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		if (positionToHide.equals(Position.left)) {
			sb.append("... ");
		} else {
			sb.append(a);
			sb.append(" ");
		}

		sb.append(operation.getLabel());
		sb.append(" ");

		if (positionToHide.equals(Position.right)) {
			sb.append("... ");
		} else {
			sb.append(b);
			sb.append(" ");
		}

		sb.append("= ");

		if (positionToHide.equals(Position.result)) {
			sb.append("...");
		} else {
			sb.append(c);
		}

		return sb.toString();
	}

	public boolean verifyValue(int value) {
		switch (positionToHide) {
		case left: {
			if (operation.equals(Operation.multiplication) && b == 0) {
				return true;
			}
			return a == value;
		}
		case right: {
			if (operation.equals(Operation.multiplication) && a == 0) {
				return true;
			}
			return b == value;
		}
		case result: {
			return c == value;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + positionToHide);
		}
	}

	public String getAStr() {
		return positionToHide.equals(Position.left) ? "...": String.valueOf(a);
	}

	public String getBStr() {
		return positionToHide.equals(Position.right) ? "...": String.valueOf(b);
	}

	public String getCStr() {
		return positionToHide.equals(Position.result) ? "...": String.valueOf(c);
	}

	public String getOperationStr() {
		return ""+ operation.getLabel();
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getC() {
		return c;
	}

	public Operation getOperation() {
		return operation;
	}

	public Level getLevel() {
		return level;
	}

	public Position getPositionToHide() {
		return positionToHide;
	}
}
