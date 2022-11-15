package card_types.environment_cards;

import card_types.Environment;
import card_types.Minion;

import java.util.ArrayList;

public class Winterfell extends Environment {
  public Winterfell(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void castEffect(ArrayList<Minion>[] board, int rowIndex) {
    for (Minion minion : board[rowIndex]) {
      minion.setFrozen(true);
    }
  }
}
