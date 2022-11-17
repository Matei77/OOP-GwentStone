package card_types;

import fileio.ActionsInput;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

public class Environment extends Card {
  public Environment(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void placeCard(ActionsInput action, Player player) {
    ErrorHandler.ThrowError(action, "Cannot place environment card on table.");
  }

  public void castEffect(ArrayList<Minion>[] Board, int rowIndex) {}
}