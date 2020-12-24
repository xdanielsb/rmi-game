package server.model;

import interfaces.CoordinateObject;
import java.awt.Color;

/** Implementation of the CoordinatObject Interface */
public abstract class CoordinateObjectImpl implements CoordinateObject {

  private static final long serialVersionUID = 1L;

  private float x;
  private float y;
  private int size;
  private float radius;
  private Color color;
  private float inertiaX;
  private float inertiaY;
  private boolean isAlive;

  /**
   * CoordinateObject main constructor
   *
   * @param x : X coordinate of the CoordinateObject
   * @param y : Y coordinate of the CoordinateObject
   * @param size : size of the CoordinateObject (can be consider like the CoordinateObject area)
   * @param color : Color of the coordinate object
   */
  public CoordinateObjectImpl(float x, float y, int size, Color color) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.radius = (float) Math.sqrt(size / Math.PI);
    this.color = color;
    this.inertiaX = 0;
    this.inertiaY = 0;
    isAlive = true;
  }

  /**
   * Method to of the X coordinate
   *
   * @return the X coordinate
   */
  @Override
  public float getX() {
    return x;
  }

  /**
   * Method to modify the X coordinate
   *
   * @param x : new X coordinate
   */
  @Override
  public void setX(float x) {
    this.x = x;
  }

  /**
   * Method to get the Y coordinate
   *
   * @return Y coordinate
   */
  @Override
  public float getY() {
    return y;
  }

  /**
   * Method to modify the Y coordinate
   *
   * @param y : new Y coordinate
   */
  @Override
  public void setY(float y) {
    this.y = y;
  }

  /**
   * Method to get the object Color
   *
   * @return the cell color
   */
  @Override
  public Color getColor() {
    return color;
  }

  /**
   * Method to modify the cell Color
   *
   * @param color : new Color
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Method to get the Cell Size
   *
   * @return the cell size
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Method to modify the cell size
   *
   * @param size : new cell size
   */
  @Override
  public void setSize(int size) {
    this.size = size;
    radius = (float) Math.sqrt(size / Math.PI);
  }

  /**
   * Method to get the cell radius
   *
   * @return cell raduis ( sqrt(cell.size/PI) )
   */
  @Override
  public float getRadius() {
    return radius;
  }

  /**
   * Method to get the cell inertia (X vector)
   *
   * @return inertia on X coordinate
   */
  @Override
  public float getInertiaX() {
    return inertiaX;
  }

  /**
   * Method to modify the inertia on X coordinate
   *
   * @param inertiaX : new inertia vector on X
   */
  @Override
  public void setInertiaX(float inertiaX) {
    this.inertiaX = inertiaX;
  }

  /**
   * Method to get the inertia vector on Y coordinate
   *
   * @return inertia vector on Y coordinate
   */
  @Override
  public float getInertiaY() {
    return inertiaY;
  }

  /**
   * Method to modify the inertia vector on Y coordinate
   *
   * @param inertiaY : new inertia vector on Y
   */
  @Override
  public void setInertiaY(float inertiaY) {
    this.inertiaY = inertiaY;
  }

  /**
   * Method to get the speed vector on X coordinate
   *
   * @return speed vector on X
   */
  @Override
  public float getSpeedX() {
    return inertiaX;
  }

  /**
   * Method to get the speed vector on Y coordinate
   *
   * @return speed vector on Y
   */
  @Override
  public float getSpeedY() {
    return inertiaY;
  }

  /**
   * Method to know if this cell is still alive
   *
   * @return true if the cell is alive, false if not
   */
  @Override
  public boolean isAlive() {
    return isAlive;
  }

  /**
   * Method to modify the life status
   *
   * @param isAlive
   */
  @Override
  public void setAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  /** Method to apply the movement mechanic on this cell, depending of the speed vector */
  @Override
  public void applyMouvement() {
    x += getSpeedX();
    y += getSpeedY();
    inertiaX *= 0.95;
    if (inertiaX < 0.01 && inertiaX > -0.01) {
      inertiaX = 0;
    }
    inertiaY *= 0.95;
    if (inertiaY < 0.01 && inertiaY > -0.01) {
      inertiaY = 0;
    }
  }

  @Override
  public int compareTo(CoordinateObject coordObj) {
    return Integer.compare(this.getSize(), coordObj.getSize());
  }
}
