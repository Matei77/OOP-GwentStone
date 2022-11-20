/* Copyright Ionescu Matei-Stefan - 323CAb - 2022-2023 */

package cardtypes.environment;

import cardtypes.Card;
import cardtypes.Environment;
import cardtypes.Minion;
import engine.GameEngine;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

/**
 * Represents a Firestorm type environment card.
 */
public final class Firestorm extends Environment {

  /**
   * Constructor for new Firestorm card.
   *
   * @param manaCost the mana cost for the card to be played
   * @param description  the description of the card
   * @param colors the colors on the card
   * @param name the name of the card
   */
  public Firestorm(final int manaCost, final String description, final ArrayList<String> colors,
                   final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Deal 1 damage to each minion on the affectedRow.
   *
   * @param affectedRow the row on which the effect is used
   */
  @Override
  public void castEffect(final int affectedRow) {
    // deal 1 damage to each minion on the affectedRow
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      minion.setHealth(minion.getHealth() - 1);
    }

    // remove dead minions from the board
    Utils.discardDeadMinions();

    // remove the used card from the player's hand
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().remove(this);

    // update player's mana
    player.setMana(player.getMana() - this.getManaCost());
  }

  /**
   * Make a copy of this Firestorm card.
   */
  @Override
  public Card makeCopy() {
    return new Firestorm(this.getManaCost(), this.getDescription(), this.getColors(),
        this.getName());
  }
}
