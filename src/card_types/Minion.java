package card_types;

import fileio.ActionsInput;
import game_engine.GameActions;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.*;

public class Minion extends Card{
  private int health;
  private int attackDamage;
  private int rowPlacement;
  private boolean tank;
  private boolean frozen = false;
  private boolean actionAvailable = true;


  public Minion (int manaCost, int attackDamage, int health, String description,
                 ArrayList<String> colors, String name, int rowPlacement, boolean tank) {
    super(manaCost, description, colors, name);
    this.health = health;
    this.attackDamage = attackDamage;
    this.rowPlacement = rowPlacement;
    this.tank = tank;
  }

  @Override
  public void placeCard() {
    Player player = Utils.getCurrentPlayer();
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.ThrowError("Not enough mana to place card on table.");
      return;
    }

    int boardRowIndex = 0;
    if (this.getRowPlacement() == FRONT_ROW)
      boardRowIndex = player.getFrontRowBoardIndex();
    else if (this.getRowPlacement() == BACK_ROW)
      boardRowIndex = player.getBackRowBoardIndex();

    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    if (board.get(boardRowIndex).size() == MAX_CARDS_PER_ROW) {
      ErrorHandler.ThrowError("Cannot place card on table since row is full.");
      return;
    }

    board.get(boardRowIndex).add(this);
    player.getCardsInHand().remove(this);
    player.setMana(player.getMana() - this.getManaCost());
  }

  @Override
  public void useEnvironment() {
    ErrorHandler.ThrowError("Chosen card is not of type environment.");
  }

  public void useAbility(Minion minion) {}

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getAttackDamage() {
    return attackDamage;
  }

  public void setAttackDamage(int attackDamage) {
    this.attackDamage = attackDamage;
  }

  public boolean isTank() {
    return tank;
  }

  public void setTank(boolean tank) {
    this.tank = tank;
  }

  public boolean isFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public boolean isActionAvailable() {
    return actionAvailable;
  }

  public void setActionAvailable(boolean actionAvailable) {
    this.actionAvailable = actionAvailable;
  }

  public int getRowPlacement() {
    return rowPlacement;
  }

  public void setRowPlacement(int rowPlacement) {
    this.rowPlacement = rowPlacement;
  }
}
