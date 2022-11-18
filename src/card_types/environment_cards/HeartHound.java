package card_types.environment_cards;

import card_types.Environment;
import card_types.Minion;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import java.util.ArrayList;

import static utils.Constants.MAX_CARDS_PER_ROW;
import static utils.Constants.MAX_ROW_INDEX;

public class HeartHound extends Environment {

  public HeartHound(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void castEffect(int affectedRow) {
    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    if (board.get(MAX_ROW_INDEX - affectedRow).size() == MAX_CARDS_PER_ROW) {
      ErrorHandler.ThrowError("Cannot steal enemy card since the player's row is full.");
      return;
    }

    Minion highestHealthMinion = getHighestHealthMinionOnRow(affectedRow);
    board.get(MAX_ROW_INDEX - affectedRow).add(highestHealthMinion);
    board.get(affectedRow).remove(highestHealthMinion);

    Player player = Utils.getCurrentPlayer();
    player.getCardsInHand().remove(this);
    player.setMana(player.getMana() - this.getManaCost());
  }

  private Minion getHighestHealthMinionOnRow(int affectedRow) {
    Minion highestHealthMinion = GameEngine.getEngine().getBoard().get(affectedRow).get(0);
    for (Minion minion : GameEngine.getEngine().getBoard().get(affectedRow)) {
      if (minion.getHealth() > highestHealthMinion.getHealth())
        highestHealthMinion = minion;
    }
    return highestHealthMinion;
  }
}
