package card_types.environment_cards;

import card_types.Environment;
import card_types.Minion;

import java.util.ArrayList;

import static utils.Constants.ROWS_MAX_INDEX;

public class HeartHound extends Environment {

  public HeartHound(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void castEffect(ArrayList<Minion>[] board, int rowIndex) {
    Minion highestHealthMinion = board[rowIndex].get(0);
    for (Minion minion : board[rowIndex]) {
      if (minion.getHealth() > highestHealthMinion.getHealth())
        highestHealthMinion = minion;
    }
      board[ROWS_MAX_INDEX - rowIndex].add(highestHealthMinion);
      board[rowIndex].remove(highestHealthMinion);
  }
}
