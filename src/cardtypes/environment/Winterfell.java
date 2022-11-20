/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.environment;

import cardtypes.Card;
import cardtypes.Environment;
import cardtypes.Minion;
import engine.GameEngine;
import player.Player;

import java.util.ArrayList;

/**
 * Represents a Winterfell type environment card.
 */
public final class Winterfell extends Environment {

  /**
   * Constructor for new Winterfell card.
   *
   * @param manaCost the mana cost for the card to be played
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   */
  public Winterfell(final int manaCost, final String description, final ArrayList<String> colors,
                    final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Freeze all cards on the affectedRow.
   *
   * @param affectedRow the row on which the effect is used
   */
  @Override
  public void castEffect(final int affectedRow) {
    // freeze the cards on the affected row
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      minion.setFrozen(true);
    }

    // remove the used card from the player's hand
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().remove(this);

    // update player's mana
    player.setMana(player.getMana() - this.getManaCost());
  }

  /**
   * Make a copy of this Winterfell card.
   */
  @Override
  public Card makeCopy() {
    return new Winterfell(this.getManaCost(), this.getDescription(), this.getColors(),
        this.getName());
  }
}
