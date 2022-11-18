package card_types;

import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import java.util.ArrayList;

public class Hero extends Card {
  private int health = 30;
  private boolean actionAvailable = true;

  public Hero(int manaCost, String description, ArrayList<String> colors, String name) {
    super(manaCost, description, colors, name);
  }

  public void useHeroAbility() {
    Player player = GameEngine.getCurrentPlayer();
    if (this.getManaCost() > player.getMana()) {
      ErrorHandler.ThrowError("Not enough mana to use hero's ability.");
      return;
    }
    if (!this.actionAvailable) {
      ErrorHandler.ThrowError("Hero has already attacked this turn.");
      return;
    }
    castEffect();
  }

  public void castEffect() {}

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public boolean isActionAvailable() {
    return actionAvailable;
  }

  public void setActionAvailable(boolean actionAvailable) {
    this.actionAvailable = actionAvailable;
  }
}
