package card_types.heroes;

import card_types.Hero;
import card_types.Minion;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
  public GeneralKocioraw(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  @Override
  public void useAbility(ArrayList<Minion> row) {
    for (Minion minion : row) {
      minion.setAttackDamage(minion.getAttackDamage() + 1);
    }
  }
}
