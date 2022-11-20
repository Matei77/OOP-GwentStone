/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.specialminions;

import java.util.ArrayList;

import cardtypes.Card;
import cardtypes.Minion;
import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import static utils.Constants.ACTION_NOT_AVAILABLE;

/**
 * Represents a TheRipper type minion card.
 */
public final class TheRipper extends Minion {

  /**
   * Constructor for new TheRipper type minion card.
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
  public TheRipper(final int manaCost, final int attackDamage, final int health,
                   final String description, final ArrayList<String> colors, final String name,
                   final int rowPlacement, final boolean tank) {
    super(manaCost, attackDamage, health, description, colors, name, rowPlacement, tank);
  }

  /**
   * Give -2 attack damage for the selected enemy minion.
   */
  @Override
  public void useAbility() {
    int cardAttackedX = GameActions.getCurrentAction().getCardAttacked().getX();
    int cardAttackedY = GameActions.getCurrentAction().getCardAttacked().getY();
    Player player = GameEngine.getCurrentPlayer();

    // check if the selected minion belongs to the enemy
    if (cardAttackedX == player.getFrontRowBoardIndex()
        || cardAttackedX == player.getBackRowBoardIndex()) {
      ErrorHandler.throwError("Attacked card does not belong to the enemy.");
      return;
    }

    Minion enemyMinion = GameEngine.getEngine().getBoard().get(cardAttackedX).get(cardAttackedY);

    // check if the enemy has a tank minion and if it is targeted
    if (Utils.enemyHasTank() && !enemyMinion.isTank()) {
      ErrorHandler.throwError("Attacked card is not of type 'Tank'.");
      return;
    }

    // decrease the enemy minion's attack
    enemyMinion.setAttackDamage(Math.max(enemyMinion.getAttackDamage() - 2, 0));

    // update the minion's action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }

  /**
   * Make a copy of this TheRipper card.
   */
  public Card makeCopy() {
    return new TheRipper(this.getManaCost(), this.getAttackDamage(), this.getHealth(),
        this.getDescription(), this.getColors(), this.getName(), this.getRowPlacement(),
        this.isTank());
  }
}
