package card_types.environment_cards;

import card_types.Environment;
import card_types.Minion;
import game_engine.GameEngine;
import player.Player;
import utils.Utils;

import java.util.ArrayList;

public class Winterfell extends Environment {
  public Winterfell(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void castEffect(int affectedRow) {
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      minion.setFrozen(true);
    }
    Player player = Utils.getCurrentPlayer();
    player.getCardsInHand().remove(this);
    player.setMana(player.getMana() - this.getManaCost());
  }
}
