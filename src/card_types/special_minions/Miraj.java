package card_types.special_minions;

import java.util.ArrayList;
import card_types.Minion;

public class Miraj extends Minion {
  public Miraj(int manaCost, int attackDamage, int health, String description,
               ArrayList<String> colors, String name) {
    super(manaCost, attackDamage, health, description, colors, name);
  }

  // only on enemy minion
  @Override
  public void useAbility(Minion minion) {
    int aux = super.getHealth();
    super.setHealth(minion.getHealth());
    minion.setHealth(aux);
  }
}
