package card_types.heroes;

import card_types.Hero;
import card_types.Minion;
import game_engine.GameActions;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

import static utils.Constants.ACTION_NOT_AVAILABLE;

public class KingMudface extends Hero {
  public KingMudface(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }


  @Override
  public void castEffect() {
    int affectedRow = GameActions.getCurrentAction().getAffectedRow();

    if (affectedRow != GameEngine.getCurrentPlayer().getFrontRowBoardIndex() && affectedRow != GameEngine.getCurrentPlayer().getBackRowBoardIndex()) {
      ErrorHandler.ThrowError("Selected row does not belong to the current player.");
      return;
    }

    ArrayList<ArrayList<Minion>> board = GameEngine.getEngine().getBoard();

    for (Minion minion : board.get(affectedRow)) {
      minion.setHealth(minion.getHealth() + 1);
    }

    Player player = GameEngine.getCurrentPlayer();
    player.setMana(player.getMana() - this.getManaCost());
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }
}
