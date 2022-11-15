package card_types;

import java.util.ArrayList;

public class Hero extends Card {
  private int health = 30;

  public Hero(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  public void useAbility(ArrayList<Minion> row) {}

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }
}
