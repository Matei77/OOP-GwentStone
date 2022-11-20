/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.heroes;

import cardtypes.Hero;
import cardtypes.Minion;
import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

import static utils.Constants.ACTION_NOT_AVAILABLE;

/**
 * Represents the KingMudface type hero card.
 */
public final class KingMudface extends Hero {

  /**
   * Constructor for new KingMudface hero card.
   *
   * @param manaCost the mana cost for the hero card to use its ability
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   */
  public KingMudface(final int manaCost, final String description, final ArrayList<String> colors,
                     final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Give +1 health to all the minions on the affected row. The affected row is taken from the
   * current action.
   */
  @Override
  public void castEffect() {
    // get the affected row from the current action
    int affectedRow = GameActions.getCurrentAction().getAffectedRow();

    // check if the affected row is valid
    if (affectedRow != GameEngine.getCurrentPlayer().getFrontRowBoardIndex()
        && affectedRow != GameEngine.getCurrentPlayer().getBackRowBoardIndex()) {
      ErrorHandler.throwError("Selected row does not belong to the current player.");
      return;
    }

    // give +1 health to all minions on the affected row
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      minion.setHealth(minion.getHealth() + 1);
    }

    // update player's mana
    Player player = GameEngine.getCurrentPlayer();
    player.setMana(player.getMana() - this.getManaCost());

    // update hero action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }
}
