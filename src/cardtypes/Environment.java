/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes;

import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

/**
 * Represents an environment card.
 */
public abstract class Environment extends Card {


  /**
   * Helper constructor for new environment card. Cannot be used to instantiate a new environment
   * card. Derived classes should be used.
   *
   * @see cardtypes.environment.Firestorm#Firestorm(int, String, ArrayList, String)  Firestorm
   * @see cardtypes.environment.HeartHound#HeartHound(int, String, ArrayList, String)  HeartHound
   * @see cardtypes.environment.Winterfell#Winterfell(int, String, ArrayList, String)  Winterfell
   */
  public Environment(final int manaCost, final String description, final ArrayList<String> colors,
                     final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Throw an error, as the player tries to put an environment card on the board.
   */
  @Override
  public void placeCard() {
    ErrorHandler.throwError("Cannot place environment card on table.");
  }


  /**
   * Use this environment card. This will get the affected row from the current game action and
   * will cast an effect on it.
   */
  @Override
  public void useEnvironment() {
    Player player = GameEngine.getCurrentPlayer();

    // check if player has enough mana to use the environment card
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.throwError("Not enough mana to use environment card.");
      return;
    }

    int affectedRow = GameActions.getCurrentAction().getAffectedRow();

    // check if the affected row is valid
    if (affectedRow == player.getFrontRowBoardIndex()
        || affectedRow == player.getBackRowBoardIndex()) {
      ErrorHandler.throwError("Chosen row does not belong to the enemy.");
      return;
    }

    // cast the effect
    this.castEffect(affectedRow);
  }

  /**
   * Cast an effect on the affected row. The effect will be different based on what type the
   * environment card is.
   *
   * @param affectedRow the row on which the effect is used
   * @see cardtypes.environment.Firestorm#castEffect(int) Firestorm
   * @see cardtypes.environment.HeartHound#castEffect(int) HeartHound
   * @see cardtypes.environment.Winterfell#castEffect(int) Winterfell
   */
  public void castEffect(final int affectedRow) { }
}
