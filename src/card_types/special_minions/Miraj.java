package card_types.special_minions;

import java.util.ArrayList;
import card_types.Minion;
import game_engine.GameActions;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;
import utils.Utils;

import static utils.Constants.ACTION_NOT_AVAILABLE;

public class Miraj extends Minion {
  public Miraj(int manaCost, int attackDamage, int health, String description,
               ArrayList<String> colors, String name, int rowPlacement, boolean tank) {
    super(manaCost, attackDamage, health, description, colors, name, rowPlacement, tank);
  }

  // only on enemy minion
  @Override
  public void castAbility() {
    int cardAttackedX = GameActions.getCurrentAction().getCardAttacked().getX();
    int cardAttackedY = GameActions.getCurrentAction().getCardAttacked().getY();
    Player player = GameEngine.getCurrentPlayer();

    if (cardAttackedX == player.getFrontRowBoardIndex() || cardAttackedX == player.getBackRowBoardIndex()) {
      ErrorHandler.ThrowError("Attacked card does not belong to the enemy.");
      return;
    }

    Minion enemyMinion = GameEngine.getEngine().getBoard().get(cardAttackedX).get(cardAttackedY);

    if (Utils.enemyHasTank() && !enemyMinion.isTank()) {
      ErrorHandler.ThrowError("Attacked card is not of type 'Tank'.");
      return;
    }

    int aux = this.getHealth();
    this.setHealth(enemyMinion.getHealth());
    enemyMinion.setHealth(aux);
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }
}
