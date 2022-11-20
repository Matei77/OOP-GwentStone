/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.environment;

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
 */
public final class HeartHound extends Environment {

  /**
   * Constructor for new HeartHound card.
   *
   * @param manaCost the mana cost for the card to be played
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   */
  public HeartHound(final int manaCost, final String description, final ArrayList<String> colors,
                    final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Steal the highest health minion from the opponent's affected row and add it to the current
   * player's mirrored row.
   *
   * @param affectedRow the row on which the effect is used
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
   * Make a copy of this HeartHound card.
   */
  @Override
  public Card makeCopy() {
    return new HeartHound(this.getManaCost(), this.getDescription(), this.getColors(),
        this.getName());
  }
}
