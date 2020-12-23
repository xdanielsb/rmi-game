package server.model;

import java.awt.Color;

import interfaces.Board;
import interfaces.Food;
import interfaces.PlayerCell;

/**
 * Implementation of the Food Interface
 */
public class FoodImpl extends CoordinateObjectImpl implements Food {

	private static final long serialVersionUID = 1L;

	private Board board;
	private boolean isPersistent;

	/**
	 * Food constructor use for the foods add to the board add the beginning of the
	 * game
	 * 
	 * @param board Board of the game
	 */
	public FoodImpl(Board board) {
		super(((float) Math.random() * (board.getBoardWidth() - FOOD_SIZE)) + FOOD_SIZE,
				((float) Math.random() * (board.getBoardHeight() - FOOD_SIZE)) + FOOD_SIZE, FOOD_SIZE,
				new Color((int) (Math.random() * 0x1000000)).brighter());
		this.board = board;
		isPersistent = true;
	}

	/**
	 * Food constructor use when a player create and throw some food
	 * 
	 * @param cell       : the PlayerCell who will throw this food
	 * @param directionX : the direction vector to throw on X coordinate
	 * @param directionY : the direction vector to throw on Y coordinate
	 */
	public FoodImpl(PlayerCell cell, float directionX, float directionY) {
		super(cell.getX() + directionX * cell.getRadius() * 1.01f, cell.getY() + directionY * cell.getRadius() * 1.01f,
				(int) (cell.getSize() * 0.05), cell.getColor());
		setInertiaX(directionX * 4.5f);
		setInertiaY(directionY * 4.5f);
		isPersistent = false;
	}

	/**
	 * Method to know if this food is persistent ( if, when this food is killed, it
	 * will reappeared on the board)
	 * 
	 * @return true if this food will reappeared on the board, false if not
	 */
	@Override
	public boolean isPersistent() {
		return isPersistent;
	}

	/**
	 * Method to kill a food
	 */
	@Override
	public void killFood() {
		setAlive(false);
		if (isPersistent) {
			setColor(new Color((int) (Math.random() * 0x1000000)).brighter());
			setX((float) (Math.random() * (board.getBoardWidth() - FOOD_SIZE)) + FOOD_SIZE);
			setY(((float) Math.random() * (board.getBoardHeight() - FOOD_SIZE)) + FOOD_SIZE);
			setAlive(true);
		}
	}

}
