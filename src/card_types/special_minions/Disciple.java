package card_types.special_minions;

import java.util.ArrayList;
import card_types.Minion;

public class Disciple extends Minion {
  public Disciple(int manaCost, int attackDamage, int health, String description,
                  ArrayList<String> colors, String name) {
    super(manaCost, attackDamage, health, description, colors, name);
  }

  //only on friendly minion
  @Override
  public void useAbility(Minion minion) {
    minion.setHealth(minion.getHealth() + 2);
  }
}
