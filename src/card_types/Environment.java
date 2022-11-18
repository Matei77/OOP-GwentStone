package card_types;

import game_engine.GameActions;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

public class Environment extends Card {
  public Environment(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void placeCard() {
    ErrorHandler.ThrowError("Cannot place environment card on table.");
  }

  @Override
  public void useEnvironment() {
    Player player = GameEngine.getCurrentPlayer();
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.ThrowError("Not enough mana to use environment card.");
      return;
    }
    int affectedRow = GameActions.getCurrentAction().getAffectedRow();
    if (affectedRow == player.getFrontRowBoardIndex() || affectedRow == player.getBackRowBoardIndex()) {
      ErrorHandler.ThrowError("Chosen row does not belong to the enemy.");
      return;
    }
    this.castEffect(affectedRow);
  }

  public void castEffect(int affectedRow) {}
}