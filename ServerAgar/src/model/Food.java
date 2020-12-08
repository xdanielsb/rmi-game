package model;

import java.awt.Color;

public class Food extends CoordinateObject {

	private static final long serialVersionUID = 1L;

	public static final int FOOD_SIZE = 2;

	private Board board;
	private boolean isAlive;

	public Food(Board board) {
		super(
			((float)Math.random() * (board.getBoardWidth()-FOOD_SIZE)) + FOOD_SIZE,
			((float)Math.random() * (board.getBoardHeight()-FOOD_SIZE)) + FOOD_SIZE,
			FOOD_SIZE,
			new Color((int)(Math.random() * 0x1000000)).brighter()
		);
		this.board = board;
		this.isAlive = true;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void killFood() {
		isAlive = false;
		setColor(new Color((int)(Math.random() * 0x1000000)).brighter());
		setX((float)(Math.random() * (board.getBoardWidth()-FOOD_SIZE)) + FOOD_SIZE);
		setY(((float)Math.random() * (board.getBoardHeight()-FOOD_SIZE)) + FOOD_SIZE);
		isAlive = true;
	}

}
