package card_types;

import java.util.ArrayList;

public class Environment extends Card {
  public Environment(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  public void castEffect(ArrayList<Minion>[] Board, int rowIndex) {}
}