package model;

public interface Food extends CoordinateObject {

	static final long serialVersionUID = 1L;

	public boolean isAlive();

	public boolean isPersistent();

}
