package model;

import java.awt.Color;

public class Food extends CoordinateObject {

	private static final long serialVersionUID = 1L;

	public static final int FOOD_SIZE = 2;

	private Board board;
	private boolean isAlive;
	private boolean isPersistent;

	public Food(Board board) {
		super(
				((float)Math.random() * (board.getBoardWidth()-FOOD_SIZE)) + FOOD_SIZE,
				((float)Math.random() * (board.getBoardHeight()-FOOD_SIZE)) + FOOD_SIZE,
				FOOD_SIZE,
				new Color((int)(Math.random() * 0x1000000)).brighter()
		);
		this.board = board;
		isAlive = true;
		isPersistent = true;
	}
	
	public Food(PlayerCell cell, float directionX, float directionY) {
		super(
				cell.getX() + directionX*cell.getRadius()*1.01f,
				cell.getY() + directionY*cell.getRadius()*1.01f,
				(int)(cell.getSize()*0.05),
				cell.getColor()
		);
		setInertiaX(directionX*4.5f);
		setInertiaY(directionY*4.5f);
		isAlive = true;
		isPersistent = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isPersistent() {
		return isPersistent;
	}

	public void killFood() {
		isAlive = false;
		if(isPersistent) {
			setColor(new Color((int)(Math.random() * 0x1000000)).brighter());
			setX((float)(Math.random() * (board.getBoardWidth()-FOOD_SIZE)) + FOOD_SIZE);
			setY(((float)Math.random() * (board.getBoardHeight()-FOOD_SIZE)) + FOOD_SIZE);
			isAlive = true;
		}
	}

}
