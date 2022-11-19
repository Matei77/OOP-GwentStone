package cardtypes.special_minions;

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
 * Represents a Miraj type minion card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public class Miraj extends Minion {
  public Miraj(final int manaCost, final int attackDamage, final int health,
               final String description, final ArrayList<String> colors, final String name,
               final int rowPlacement, final boolean tank) {
    super(manaCost, attackDamage, health, description, colors, name, rowPlacement, tank);
  }

  /**
   * Swap this minion's health with another enemy minion.
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

    // swap the health of this minion and with the selected enemy minion's health
    int aux = this.getHealth();
    this.setHealth(enemyMinion.getHealth());
    enemyMinion.setHealth(aux);

    // update the minion's action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }

  /**
   * @return a copy of this Miraj card.
   */
  @Override
  public Card makeCopy() {
    return new Miraj(this.getManaCost(), this.getAttackDamage(), this.getHealth(),
        this.getDescription(), this.getColors(), this.getName(), this.getRowPlacement(),
        this.isTank());
  }
}
