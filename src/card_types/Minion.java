package card_types;

import java.util.ArrayList;

public class Minion extends Card{
  private int health;
  private int attackDamage;
  private int rowPlacement;
  private boolean tank;
  private boolean frozen = false;
  private boolean actionAvailable = true;


  public Minion (int manaCost, int attackDamage, int health, String description,
                 ArrayList<String> colors, String name, int rowPlacement, boolean tank) {
    super(manaCost, description, colors, name);
    this.health = health;
    this.attackDamage = attackDamage;
    this.rowPlacement = rowPlacement;
    this.tank = tank;
  }

  public void useAbility(Minion minion) {}

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getAttackDamage() {
    return attackDamage;
  }

  public void setAttackDamage(int attackDamage) {
    this.attackDamage = attackDamage;
  }

  public boolean isTank() {
    return tank;
  }

  public void setTank(boolean tank) {
    this.tank = tank;
  }

  public boolean isFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public boolean isActionAvailable() {
    return actionAvailable;
  }

  public void setActionAvailable(boolean actionAvailable) {
    this.actionAvailable = actionAvailable;
  }

  public int getRowPlacement() {
    return rowPlacement;
  }

  public void setRowPlacement(int rowPlacement) {
    this.rowPlacement = rowPlacement;
  }
}
