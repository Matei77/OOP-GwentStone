package card_types.environment_cards;

import card_types.Card;
import card_types.Environment;
import card_types.Minion;
import game_engine.GameEngine;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

public class Firestorm extends Environment {

  public Firestorm(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void castEffect(int affectedRow) {
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      minion.setHealth(minion.getHealth() - 1);
    }
    Utils.discardDeadMinions();
    Player player = GameEngine.getCurrentPlayer();
    player.getCardsInHand().remove(this);
    player.setMana(player.getMana() - this.getManaCost());
  }

  @Override
  public Card makeCopy () {
    return new Firestorm(this.getManaCost(), this.getDescription(), this.getColors(), this.getName());
  }
}
