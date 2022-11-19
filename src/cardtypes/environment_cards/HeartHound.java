package cardtypes.environment_cards;

import cardtypes.Card;
import cardtypes.Environment;
import cardtypes.Minion;
import engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.MAX_CARDS_PER_ROW;
import static utils.Constants.MAX_ROW_INDEX;

/**
 * Represents a HeartHound type environment card.
 *
 * @author Ionescu Matei-Stefan
 * @group 323CAb
 * @year 2022-2023
 * @project GwentStone
 */
public class HeartHound extends Environment {

  public HeartHound(final int manaCost, final String description, final ArrayList<String> colors,
                    final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Steal the highest health minion from the opponent's affected row and add it to the current
   * player's mirrored row.
   * @param affectedRow the row on which the effect is cast
   */
  @Override
  public void castEffect(final int affectedRow) {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    // check if there is space for a new card on the player's row
    if (board.get(MAX_ROW_INDEX - affectedRow).size() == MAX_CARDS_PER_ROW) {
      ErrorHandler.throwError("Cannot steal enemy card since the player's row is full.");
      return;
    }

    // move the minion from the opponent's row to the row of the player using the card
    Minion highestHealthMinion = Utils.getHighestHealthMinionOnRow(affectedRow);
    board.get(MAX_ROW_INDEX - affectedRow).add(highestHealthMinion);
    board.get(affectedRow).remove(highestHealthMinion);

    // remove the used card from the player's hand
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().remove(this);

    // update player's mana
    player.setMana(player.getMana() - this.getManaCost());
  }

  /**
   * @return a copy of this HeartHound card
   */
  @Override
  public Card makeCopy() {
    return new HeartHound(this.getManaCost(), this.getDescription(), this.getColors(),
        this.getName());
  }
}
