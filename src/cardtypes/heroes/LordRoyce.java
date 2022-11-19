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
import static utils.Constants.FROZEN;

/**
 * Represents the LordRoyce type hero card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public class LordRoyce extends Hero {

  public LordRoyce(final int manaCost, final String description, final ArrayList<String> colors,
                   final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Freeze the highest attack minion on the affected row. The affect row is taken from the
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

    // freeze the highest attack minion on the affected row
    Minion highestAttackMinion = Utils.getHighestAttackMinionOnRow(affectedRow);
    highestAttackMinion.setFrozen(FROZEN);

    // update player's mana
    Player player = GameEngine.getCurrentPlayer();
    player.setMana(player.getMana() - this.getManaCost());

    // update hero action available status
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }
}
