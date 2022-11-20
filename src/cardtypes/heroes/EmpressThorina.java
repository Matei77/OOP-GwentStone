/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.heroes;

import cardtypes.Hero;
import cardtypes.Minion;
import engine.GameActions;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.ACTION_NOT_AVAILABLE;

/**
 * Represents the EmpressThorina type hero card.
 */
public final class EmpressThorina extends Hero {

  /**
   * Constructor for new EmpressThorina hero card.
   *
   * @param manaCost the mana cost for the hero card to use its ability
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   */
  public EmpressThorina(final int manaCost, final String description,
                        final ArrayList<String> colors, final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Destroy the highest health minion on the affected row. The affected row is taken from the
   * current action.
   */
  @Override
  public void castEffect() {
    // get the affected row from the current action
    int affectedRow = GameActions.getCurrentAction().getAffectedRow();

    // check if the affected row is valid
    if (affectedRow == GameEngine.getCurrentPlayer().getFrontRowBoardIndex()
        || affectedRow == GameEngine.getCurrentPlayer().getBackRowBoardIndex()) {
      ErrorHandler.throwError("Selected row does not belong to the enemy.");
      return;
    }

    // get the highest health minion on the affected row
    Minion highestHealthMinionOnRow = Utils.getHighestHealthMinionOnRow(affectedRow);

    // remove the highest health minion from the affected row
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();
    board.get(affectedRow).remove(highestHealthMinionOnRow);

    // update player's mana
    Player player = GameEngine.getCurrentPlayer();
    player.setMana(player.getMana() - this.getManaCost());

    // update hero action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }
}
