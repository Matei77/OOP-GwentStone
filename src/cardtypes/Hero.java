package cardtypes;

import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

import static utils.Constants.ACTION_AVAILABLE;
import static utils.Constants.HERO_STARTING_HEALTH;

/**
 * Represents a hero card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public class Hero extends Card {
  private int health = HERO_STARTING_HEALTH;
  private boolean actionAvailable = ACTION_AVAILABLE;

  public Hero(final int manaCost, final String description, final ArrayList<String> colors,
              final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Use this hero's special ability.
   */
  public void useHeroAbility() {
    Player player = GameEngine.getCurrentPlayer();

    // check if the player has enough mana to use the hero's special ability
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.throwError("Not enough mana to use hero's ability.");
      return;
    }

    // check if the hero has already used his ability this turn
    if (!this.isActionAvailable()) {
      ErrorHandler.throwError("Hero has already attacked this turn.");
      return;
    }

    // cast the hero's special ability effect
    castEffect();
  }

  /**
   * cast the hero's special ability effect
   */
  public void castEffect() { }

  /**
   * @return the health of this hero
   */
  public int getHealth() {
    return health;
  }

  /**
   * Set the health of this hero.
   * @param health the new health of this card
   */
  public void setHealth(final int health) {
    this.health = health;
  }

  /**
   * @return true if the hero can use his ability or false otherwise
   */
  public boolean isActionAvailable() {
    return actionAvailable;
  }

  /**
   * Set whether the hero can use his ability or not.
   * @param actionAvailable the action available status
   */
  public void setActionAvailable(final boolean actionAvailable) {
    this.actionAvailable = actionAvailable;
  }
}
