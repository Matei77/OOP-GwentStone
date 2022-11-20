/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.ACTION_NOT_AVAILABLE;
import static utils.Constants.FRONT_ROW;
import static utils.Constants.MAX_CARDS_PER_ROW;
import static utils.Constants.PLAYER_ONE_TURN;

/**
 * Represents a minion card.
 */
public class Minion extends Card {
  private int health;
  private int attackDamage;
  private final int rowPlacement;
  private final boolean tank;
  private boolean frozen = false;
  private boolean actionAvailable = true;


  /**
   * Constructor for new minion card. Minions with special abilities need to be constructed using
   * their unique classes.
   *
   * @see cardtypes.specialminions.Disciple#Disciple(int, int, int, String, ArrayList, String,
   *   int, boolean)  Disciple
   * @see cardtypes.specialminions.Miraj#Miraj(int, int, int, String, ArrayList, String, int,
   *   boolean)  Miraj
   * @see cardtypes.specialminions.TheCursedOne#TheCursedOne(int, int, int, String, ArrayList,
   *   String, int, boolean)  TheCursedOne
   * @see cardtypes.specialminions.TheRipper#TheRipper(int, int, int, String, ArrayList, String,
   *   int, boolean)  TheRipper
   *
   * @param manaCost the mana cost of placing the minion on the board
   * @param attackDamage the attackDamage of the minion
   * @param health the health of the minion
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   * @param rowPlacement the row on which the card should be played; 1 for FrontRow; 2 for BackRow
   * @param tank whether the minion is a tank or not
   */
  public Minion(final int manaCost, final int attackDamage, final int health,
                final String description, final ArrayList<String> colors, final String name,
                final int rowPlacement, final boolean tank) {
    super(manaCost, description, colors, name);
    this.health = health;
    this.attackDamage = attackDamage;
    this.rowPlacement = rowPlacement;
    this.tank = tank;
  }

  /**
   * Place this minion card from the player's hand on the board.
   */
  @Override
  public void placeCard() {
    // check if the player has enough mana to play the card
    Player player = GameEngine.getCurrentPlayer();
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.throwError("Not enough mana to place card on table.");
      return;
    }

    // find on which row the minion should be placed
    int boardRowIndex;
    if (this.getRowPlacement() == FRONT_ROW)  {
      boardRowIndex = player.getFrontRowBoardIndex();
    } else {
      boardRowIndex = player.getBackRowBoardIndex();
    }

    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    //check if the row is full
    if (board.get(boardRowIndex).size() == MAX_CARDS_PER_ROW) {
      ErrorHandler.throwError("Cannot place card on table since row is full.");
      return;
    }

    // place the card on the board and remove it from the player's hand
    board.get(boardRowIndex).add(this);
    player.getCardsInHand().remove(this);

    // update player's mana
    player.setMana(player.getMana() - this.getManaCost());
  }

  /**
   * Throw an error, as the player tries to use a minion card instead of an environment one.
   */
  @Override
  public void useEnvironment() {
    ErrorHandler.throwError("Chosen card is not of type environment.");
  }

  /**
   * Make a copy of this Minion card.
   */
  @Override
  public Card makeCopy() {
    return new Minion(this.getManaCost(), this.getAttackDamage(), this.getHealth(),
        this.getDescription(), this.getColors(), this.getName(), this.getRowPlacement(),
        this.isTank());
  }

  /**
   * Attack the enemy hero.
   */
  public void attackHero() {
    // check if this minion is frozen and cannot attack
    if (this.isFrozen()) {
      ErrorHandler.throwError("Attacker card is frozen.");
      return;
    }

    // check if this minion has already attacked/used ability
    if (this.hasActionAvailable()) {
      ErrorHandler.throwError("Attacker card has already attacked this turn.");
      return;
    }

    // check if the enemy has a tank minion on the board
    if (Utils.enemyHasTank()) {
      ErrorHandler.throwError("Attacked card is not of type 'Tank'.");
      return;
    }

    // get the attacked hero
    Hero attackedPlayerHero;
    if (GameEngine.getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
      attackedPlayerHero = GameEngine.getEngine().getPlayerTwo().getHero();
    } else {
      attackedPlayerHero = GameEngine.getEngine().getPlayerOne().getHero();
    }

    // damage the hero
    attackedPlayerHero.setHealth(attackedPlayerHero.getHealth() - this.getAttackDamage());

    // check if the hero has been killed
    if (attackedPlayerHero.getHealth() <= 0) {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode outputObjectNode = mapper.createObjectNode();

      // check which player won
      if (GameEngine.getEngine().getPlayerTurn() == PLAYER_ONE_TURN) {
        outputObjectNode.put("gameEnded", "Player one killed the enemy hero.");
        //update player one number of wins
        GameEngine.getEngine().setPlayerOneWins(GameEngine.getEngine().getPlayerOneWins() + 1);

      } else {
        outputObjectNode.put("gameEnded", "Player two killed the enemy hero.");
        //update player two number of wins
        GameEngine.getEngine().setPlayerTwoWins(GameEngine.getEngine().getPlayerTwoWins() + 1);
      }

      // output which player won
      ArrayNode output = GameEngine.getEngine().getOutput();
      output.addAll(List.of(outputObjectNode));
    }

    // update minion's action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }

  /**
   * Attack an enemy minion on the board
   */
  public void useAttack() {
    int cardAttackedX = GameActions.getCurrentAction().getCardAttacked().getX();
    int cardAttackedY = GameActions.getCurrentAction().getCardAttacked().getY();
    Player player = GameEngine.getCurrentPlayer();

    // check if attacked card belongs to the enemy
    if (cardAttackedX == player.getFrontRowBoardIndex()
        || cardAttackedX == player.getBackRowBoardIndex()) {
      ErrorHandler.throwError("Attacked card does not belong to the enemy.");
      return;
    }

    // check if this minion has already attacked/used ability this turn
    if (this.hasActionAvailable()) {
      ErrorHandler.throwError("Attacker card has already attacked this turn.");
      return;
    }

    // check if this minion is frozen and cannot attack
    if (this.frozen) {
      ErrorHandler.throwError("Attacker card is frozen.");
      return;
    }

    Minion enemyMinion = GameEngine.getEngine().getBoard().get(cardAttackedX).get(cardAttackedY);

    // check if the enemy has a tank minion on the board and if it is attacked
    if (Utils.enemyHasTank() && !enemyMinion.isTank()) {
      ErrorHandler.throwError("Attacked card is not of type 'Tank'.");
      return;
    }

    // damage the attacked minion
    enemyMinion.setHealth(enemyMinion.getHealth() - this.getAttackDamage());

    // update this minion's action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }


  /**
   * Use this minion's special ability
   */
  public void useMinionAbility() {
    // check if the minion is frozen
    if (this.frozen) {
      ErrorHandler.throwError("Attacker card is frozen.");
      return;
    }

    // check if the minion has already attacked/used ability this turn
    if (this.hasActionAvailable()) {
      ErrorHandler.throwError("Attacker card has already attacked this turn.");
      return;
    }

    // use special ability
    this.useAbility();
  }

  /**
   * Use the special ability of this minion. The effect will be different based on the type of the
   * minion using the ability.
   *
   * @see cardtypes.specialminions.Disciple#useAbility() Disciple
   * @see cardtypes.specialminions.Miraj#useAbility() Miraj
   * @see cardtypes.specialminions.TheCursedOne#useAbility() TheCursedOne
   * @see cardtypes.specialminions.TheRipper#useAbility() TheRipper
   */
  public void useAbility() { }

  /**
   * @return the health of this minion
   */
  public int getHealth() {
    return health;
  }

  /**
   * Set the health of this minion.
   *
   * @param health the new health of this card
   */
  public void setHealth(final int health) {
    this.health = health;
  }

  /**
   * @return the attack of this minion
   */
  public int getAttackDamage() {
    return attackDamage;
  }

  /**
   * Set the attack of this minion.
   *
   * @param attackDamage the new attack of this card
   */
  public void setAttackDamage(final int attackDamage) {
    this.attackDamage = attackDamage;
  }


  /**
   * @return whether this minion is a tank or not
   */
  public boolean isTank() {
    return tank;
  }

  /**
   * @return whether this minion is frozen or not
   */
  public boolean isFrozen() {
    return frozen;
  }

  /**
   * Set this minion's frozen status.
   *
   * @param frozen new frozen status
   */
  public void setFrozen(final boolean frozen) {
    this.frozen = frozen;
  }

  /**
   * @return whether this minion has an action available
   */
  public boolean hasActionAvailable() {
    return !actionAvailable;
  }

  /**
   * Set this minion's action available status.
   *
   * @param actionAvailable new action available status
   */
  public void setActionAvailable(final boolean actionAvailable) {
    this.actionAvailable = actionAvailable;
  }

  /**
   * @return the row on which this minion must be placed;
   * 1 = front row;
   * 2 = back row;
   */
  public int getRowPlacement() {
    return rowPlacement;
  }
}
