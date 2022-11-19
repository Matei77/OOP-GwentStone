package cardtypes.environment_cards;

import cardtypes.Card;
import cardtypes.Environment;
import cardtypes.Minion;
import engine.GameEngine;
import player.Player;

import java.util.ArrayList;

public class Winterfell extends Environment {
  public Winterfell(final int manaCost, final String description, final ArrayList<String> colors,
                    final String name) {
    super(manaCost, description, colors, name);
  }

  /**
   * Freeze all cards on the affectedRow.
   * @param affectedRow the row on which the effect is cast
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
   * @return a copy of this Winterfell card
   */
  @Override
  public Card makeCopy() {
    return new Winterfell(this.getManaCost(), this.getDescription(), this.getColors(),
        this.getName());
  }
}
