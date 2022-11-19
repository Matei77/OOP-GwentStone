package card_types.special_minions;

import java.util.ArrayList;

import card_types.Card;
import card_types.Minion;
import game_engine.GameActions;
import game_engine.GameEngine;
import player.Player;
import utils.ErrorHandler;

import static utils.Constants.ACTION_NOT_AVAILABLE;

public class Disciple extends Minion {
  public Disciple(int manaCost, int attackDamage, int health, String description,
                  ArrayList<String> colors, String name, int rowPlacement, boolean tank) {
    super(manaCost, attackDamage, health, description, colors, name, rowPlacement, tank);
  }

  //only on friendly minion
  @Override
  public void castAbility() {
    int cardAttackedX = GameActions.getCurrentAction().getCardAttacked().getX();
    int cardAttackedY = GameActions.getCurrentAction().getCardAttacked().getY();
    Player player = GameEngine.getCurrentPlayer();

    if (cardAttackedX != player.getFrontRowBoardIndex() && cardAttackedX != player.getBackRowBoardIndex()) {
      ErrorHandler.ThrowError("Attacked card does not belong to the current player.");
      return;
    }

    Minion friendlyMinion = GameEngine.getEngine().getBoard().get(cardAttackedX).get(cardAttackedY);

    friendlyMinion.setHealth(friendlyMinion.getHealth() + 2);
    this.setActionAvailable(ACTION_NOT_AVAILABLE);
  }

  @Override
  public Card makeCopy() {
    return new Disciple(this.getManaCost(), this.getAttackDamage(), this.getHealth(),
        this.getDescription(), this.getColors(), this.getName(), this.getRowPlacement(), this.isTank());
  }
}
