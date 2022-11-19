package cardtypes.special_minions;

import java.util.ArrayList;

import cardtypes.Card;
import cardtypes.Minion;
import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import static utils.Constants.ACTION_NOT_AVAILABLE;

/**
 * Represents a Disciple type minion card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public class Disciple extends Minion {
  public Disciple(final int manaCost, final int attackDamage, final int health,
                  final String description, final ArrayList<String> colors, final String name,
                  final int rowPlacement, final boolean tank) {
    super(manaCost, attackDamage, health, description, colors, name, rowPlacement, tank);
  }

  /**
   * Give a friendly minion +2 health.
   */
  @Override
  public void useAbility() {
    int cardAttackedX = GameActions.getCurrentAction().getCardAttacked().getX();
    int cardAttackedY = GameActions.getCurrentAction().getCardAttacked().getY();
    Player player = GameEngine.getCurrentPlayer();

    // check if the selected minion belongs to the current player
    if (cardAttackedX != player.getFrontRowBoardIndex()
        && cardAttackedX != player.getBackRowBoardIndex()) {
      ErrorHandler.throwError("Attacked card does not belong to the current player.");
      return;
    }

    // increase the health of the selected minion
    Minion friendlyMinion = GameEngine.getEngine().getBoard().get(cardAttackedX).get(cardAttackedY);
    friendlyMinion.setHealth(friendlyMinion.getHealth() + 2);

    // update minion's action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }

  /**
   * @return a copy of this Disciple card.
   */
  @Override
  public Card makeCopy() {
    return new Disciple(this.getManaCost(), this.getAttackDamage(), this.getHealth(),
        this.getDescription(), this.getColors(), this.getName(), this.getRowPlacement(),
        this.isTank());
  }
}
