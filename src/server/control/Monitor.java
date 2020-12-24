package server.control;

import interfaces.Player;
import interfaces.Team;
import java.util.ArrayList;
import java.util.List;

/** This class will handle all synchronization for variables update */
public class Monitor {

  private List<Player> waitingAddPlayers;
  private Object addPlayersBell;

  private List<Player> waitingRemovePlayers;
  private Object removePlayersBell;

  /** Monitor main constructor */
  public Monitor() {
    waitingAddPlayers = new ArrayList<>();
    addPlayersBell = new Object();

    waitingRemovePlayers = new ArrayList<>();
    removePlayersBell = new Object();
  }

  /**
   * Method to synchronized all future players add on the board
   *
   * @param player : player to add
   */
  public void addPlayer(Player player) {
    if (player == null) {
      return;
    }
    synchronized (addPlayersBell) {
      if (waitingAddPlayers.contains(player)) {
        return;
      }
      waitingAddPlayers.add(player);
    }
  }

  /**
   * Method to know there is player to add on the board
   *
   * @return true if there is player to add on the board
   */
  public boolean hasAddPlayerWaiting() {
    return waitingAddPlayers.size() > 0;
  }

  /**
   * Method to get all the players to add on the board (will remove all the waiting players from the
   * monitor)
   *
   * @return the list of player to add
   */
  public List<Player> clearWaitingAddPlayers() {
    synchronized (addPlayersBell) {
      List<Player> tmp = new ArrayList<>();
      tmp.addAll(waitingAddPlayers);
      waitingAddPlayers.clear();
      return tmp;
    }
  }

  /**
   * Method to synchronized all future players remove on the board
   *
   * @param player : player to add
   */
  public void removePlayer(Player player) {
    if (player == null) {
      return;
    }
    synchronized (removePlayersBell) {
      if (waitingRemovePlayers.contains(player)) {
        return;
      }
      waitingRemovePlayers.add(player);
    }
  }

  /**
   * Method to know there is player to remove on the board
   *
   * @return true if there is player to remove on the board
   */
  public boolean hasRemovePlayerWaiting() {
    return waitingRemovePlayers.size() > 0;
  }

  /**
   * Method to get all the players to remove on the board (will remove all the waiting players from
   * the monitor)
   *
   * @return the list of player to add
   */
  public List<Player> clearWaitingRemovePlayers() {
    synchronized (removePlayersBell) {
      List<Player> tmp = new ArrayList<>();
      tmp.addAll(waitingRemovePlayers);
      waitingRemovePlayers.clear();
      return tmp;
    }
  }

  /**
   * Method to update a team score
   *
   * @param team : Team who will have it score updated
   * @param amount : value to add to the score (positive or negative)
   */
  public void addScore(Team team, int amount) {
    if (team == null) {
      return;
    }
    synchronized (team.getBell()) {
      team.addToScore(amount);
    }
  }
}
;
